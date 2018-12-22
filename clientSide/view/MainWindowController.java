package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import viewModel.PipeGameViewModel;


public class MainWindowController implements Initializable{

	PipeGameViewModel vm;
	ListProperty<char[]> board;
	BooleanProperty isGoal;
	IntegerProperty numberOfSteps;

	@FXML
	private TextField Steps;

	@FXML
	private TextField Time;

	@FXML
	PipeGameDisplayer pipeGameDisplayer;


	public MainWindowController(PipeGameViewModel vm) {
		this.vm = vm;
		this.board = new SimpleListProperty<>();
		this.board.bind(vm.board);
		this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bind(vm.isGoal);
		this.numberOfSteps = new SimpleIntegerProperty();
		this.numberOfSteps.bind(vm.numberOfSteps);
	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open Pipe Game file");
		fc.setInitialDirectory(new File("./resources/Levels"));
		File chosen = fc.showOpenDialog(null);
		if(chosen != null)
		{
			vm.loadLevel(chosen);
		}
	}
	
	public void openSavedFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open Pipe Game file");
		fc.setInitialDirectory(new File("./resources/Saves"));
		File chosen = fc.showOpenDialog(null);
		if(chosen != null)
		{
			vm.loadSavedLevel(chosen);
		}
	}

	public void Save() throws FileNotFoundException {
		vm.save();
	}

	public void solve() throws Exception {
		List<String> solution = vm.solve();
		for(int i=0; i<solution.size();i++) {
			int y,x,numberOfRotations;
			String[] line = solution.get(i).split(",");
			y = Integer.parseInt(line[0]);
			x = Integer.parseInt(line[1]);
			numberOfRotations = Integer.parseInt(line[2]);
			for(int j=0;j<numberOfRotations;j++) {
				vm.rotate(y, x);
				pipeGameDisplayer.setPipeGameBoard(vm.board);
			}
		}
	}

	public void addClickOnPipeBoardHandler() {
		if(pipeGameDisplayer==null)
			return;

		pipeGameDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{pipeGameDisplayer.requestFocus();});

		pipeGameDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				double w = pipeGameDisplayer.getW();
				double h = pipeGameDisplayer.getH();
				int x = (int) (event.getX()/w);
				int y = (int) (event.getY()/h);
				vm.rotate(y, x);
				pipeGameDisplayer.setPipeGameBoard(vm.board); //set calls for redraw automatically
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		pipeGameDisplayer.setPipeGameBoard(vm.board);
		addClickOnPipeBoardHandler();
	}
}

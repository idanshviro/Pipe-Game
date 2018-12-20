package view;

import java.io.File;
import java.net.URL;
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




	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		pipeGameDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{pipeGameDisplayer.requestFocus();});
		vm.loadLevel(new File("./resources/Levels/1.txt"));

		pipeGameDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				double w = pipeGameDisplayer.getW();
				double h = pipeGameDisplayer.getH();
				int x = (int) (event.getX()/w);
				int y = (int) (event.getY()/h);
				vm.rotate(y, x);
			}
		});
	}
}

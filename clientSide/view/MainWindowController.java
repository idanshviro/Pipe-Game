package view;

import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.sun.javafx.collections.IntegerArraySyncer;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import searchable.SearchablePipeGame;
import state.Board;
import state.State;


public class MainWindowController implements Initializable{
	private Board b;
	private State<Board> boardState;
	private SearchablePipeGame searchable;
	private int steps;

    @FXML
    private TextField Steps;
	
	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open Pipe Game file");
		fc.setInitialDirectory(new File("./resources/Levels"));
		File chosen = fc.showOpenDialog(null);
		if(chosen != null)
		{
			loadLevel(chosen);
		}
	}
	
	public String getSteps() {
	return String.valueOf(steps);
	}

	public void loadLevel(File f){
		steps=0;
		Scanner scanner = null;
		List<char[]> level = new ArrayList<char[]>();
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNext()){
			char[] line=null;
			String string = scanner.nextLine();	
			line = string.toCharArray();
			level.add(line);
		}
		scanner.close();
		pipeGameDisplayer.setPipeGameBoard(level);
		b = new Board(pipeGameDisplayer.getPipeGameBoard());
		boardState = new State<Board> (b);
		searchable = new SearchablePipeGame(boardState);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		pipeGameDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{pipeGameDisplayer.requestFocus();});
		loadLevel(new File("./resources/Levels/1.txt"));

		pipeGameDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				double w = pipeGameDisplayer.getW();
				double h = pipeGameDisplayer.getH();
				int x = (int) (event.getX()/w);
				int y = (int) (event.getY()/h);
				char c = pipeGameDisplayer.pipeGameBoard.get(y)[x];
				if(c == 'L' || c == 'l' ||c == 'F' || c == 'f' ||c == '7' || c == 'j'||c == 'J' || c == '-'||c == '|') {
					steps++;
					Steps.setText(getSteps());
					searchable.rotateOnBoard(y,x);
					pipeGameDisplayer.redraw();		
				}

			}
		});
	}
}

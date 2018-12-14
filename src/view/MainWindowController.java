package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import searchable.Searchable;
import searchable.SearchablePipeGame;
import state.Board;
import state.State;


public class MainWindowController implements Initializable{
	private Board b;
	private State<Board> boardState;
	private SearchablePipeGame searchable;

	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open Pipe Game file");
		fc.setInitialDirectory(new File("./resources/Levels"));
		File chosen = fc.showOpenDialog(null);
		if(chosen != null)
		{
			pipeGameDisplayer.setPipeGameBoard(loadLevel(chosen));
		}
	}

	public List<char[]> loadLevel(File f){
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
		return level;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	



		pipeGameDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{pipeGameDisplayer.requestFocus();});

		List<char[]> gameBoard = new ArrayList<char[]>();
		gameBoard.add("s-|--7".toCharArray());
		gameBoard.add("7-J--7".toCharArray());
		gameBoard.add("J-L--7".toCharArray());
		gameBoard.add("|----7".toCharArray());
		gameBoard.add("|-F--7".toCharArray());
		gameBoard.add("L-J--g".toCharArray());

		pipeGameDisplayer.setPipeGameBoard(gameBoard);

		b = new Board(pipeGameDisplayer.getPipeGameBoard());
		boardState = new State<Board> (b);
		searchable = new SearchablePipeGame(boardState);

		pipeGameDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				double w = pipeGameDisplayer.getW();
				double h = pipeGameDisplayer.getH();
				int x = (int) (event.getX()/w);
				int y = (int) (event.getY()/h);
				searchable.rotateOnBoard(y,x);
				pipeGameDisplayer.redraw();				
			}

		});
	}
}

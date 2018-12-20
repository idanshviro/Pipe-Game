package viewModel;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import model.PipeGameModel;

public class PipeGameViewModel {
	public ListProperty<char[]> board;
	public BooleanProperty isGoal;
	public IntegerProperty numberOfSteps;
	public PipeGameModel model;
	
	public PipeGameViewModel(PipeGameModel m) {
	this.model = m;
	this.board = new SimpleListProperty<>();
	this.board.bind(m.board);
	this.isGoal = new SimpleBooleanProperty();
	this.isGoal.bind(m.isGoal);
	this.numberOfSteps = new SimpleIntegerProperty();
	this.numberOfSteps.bind(m.numberOfSteps);
	}
	
	public void rotate(int i, int j) {
		this.model.rotate(i, j);
	}
	
	public void loadLevel(File f){
		this.model.loadLevel(f);
	}
	
}

package viewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

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
	public PipeGameModel model;

	public PipeGameViewModel(PipeGameModel m) {
		this.model = m;
		this.board = new SimpleListProperty<>();
		this.board.bind(m.board);
		this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bind(m.isGoal);
	}

	public void rotate(int i, int j) {
		this.model.rotate(i, j);
	}

	public void loadLevel(File f){
		this.model.loadLevel(f);
	}

	public boolean save() throws FileNotFoundException {
		return this.model.save();
	}

	public void loadSavedLevel(File f) {
		this.model.loadLevel(f);
	}

	public List<String> solve() throws Exception{
		return this.model.solve();
	}

	public void setPort(int port) {
		this.model.setPort(port);
	}

	public void setHost(String host) {
		this.model.setHost(host);
	}
}

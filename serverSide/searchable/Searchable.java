package searchable;
import java.util.List;
import state.State;

public interface Searchable<T> {
	State<T> getInitialState();
	boolean isGoalState(State<T> state);
	List<State<T>> getAllPossibleStates(State<T> s);
	public double getGrade(State<T> state);
}

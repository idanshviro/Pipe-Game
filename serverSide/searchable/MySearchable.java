package searchable;
import state.State;

public abstract class MySearchable<T> implements Searchable<T>{
	private State<T> initialState;
	protected State<T> goalState;
	
	public MySearchable(State<T> t) {
		this.initialState = t;
	}
	
	@Override
	public State<T> getInitialState() {
		return this.initialState;
	}

	public void setInitialState(State<T> initialState) {
		this.initialState = initialState;
	}
	
	@Override
	public boolean isGoalState(State<T> state) {
		return state.equals(goalState);
	}
}

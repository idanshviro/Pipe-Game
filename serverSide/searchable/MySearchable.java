package searchable;
import state.State;


public abstract class  MySearchable<T> implements Searchable<T>{
	
	private State<T> initialState;
	private State<T> goalState;
	
	public MySearchable(State<T> t) {
		// TODO Auto-generated constructor stub
		this.initialState = t;
	}
	
	@Override
	public State<T> getInitialState() {
		return initialState;
	}
	@Override
	public boolean isGoalState(State<T> state) {
		return state.equals(goalState);
	}	
	public void setInitialState(State<T> initialState) {
		this.initialState = initialState;
	}
}
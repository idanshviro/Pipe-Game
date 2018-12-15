package solver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import state.State;

public class Solution{

	protected State<?> initialState;
	protected State<?> goalState;
	protected List<Action> actionList = new LinkedList<Action>();

	public Solution(State<?> initialstate, State<?> goalstate) {
		this.initialState = initialstate;
		this.goalState = goalstate;

		State<?> currentState = this.goalState;

		while(currentState.getCameFrom() != null) {
			actionList.add(currentState.getAction());
			currentState = currentState.getCameFrom(); 
		}
		Collections.reverse(actionList);
	}
	public List<Action> getActionList(){
		return actionList;
	}

	public State<?> getInitialState() {
		return initialState;
	}
	
	public State<?> getGoalState(){
		return this.goalState;
	}
}

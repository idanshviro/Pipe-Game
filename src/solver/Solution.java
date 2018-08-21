package solver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import state.State;

public class Solution{
	protected List<Action> actionList = new LinkedList<Action>();
	private State<?> initialState;
	private State<?> goalState;  
 
	public State<?> getInitialState() {
		return initialState;
	}
	public Solution(State<?> initialState, State<?> goalState){
		this.initialState = initialState; 
		this.goalState = goalState;
		State<?> currentState = goalState;
		 
		while(currentState.getCameFrom()!= null){
			actionList.add(currentState.getAction());
			currentState = currentState.getCameFrom();		
		}
		Collections.reverse(actionList);
	} 

	public List<Action> getActionList(){
		return actionList; 
	}


	public State<?> getGoalState(){
		return goalState;
	}
	

}

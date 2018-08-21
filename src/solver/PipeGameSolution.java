package solver;

import java.util.HashMap;



public class PipeGameSolution extends Solution {

	public PipeGameSolution(Solution sol) {
		super(sol.getInitialState(),sol.getGoalState());

		HashMap<Action,Integer> actionDict = new HashMap<Action,Integer>();
		for (Action act : this.actionList) {
			if (actionDict.containsKey(act)) {
				actionDict.put(act,(actionDict.get(act)+1)%4);
			}
			else {
				actionDict.put(act, 1);
			}
		}
		this.actionList.clear();

		for(Action a : actionDict.keySet()) {
			Action act = new Action(a.getX(),a.getY(), actionDict.get(a));
			if(act.getNumOfActions()==0)
			{
				//nothing
			}
			else {
				this.actionList.add(act);
			}
		}

	} 
}
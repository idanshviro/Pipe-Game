package searcher;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import searchable.Searchable;
import solver.Solution;
import state.State;

public class HillClimbing extends CommonSearcher{
	//	 currentNode = startNode;
	//	   loop do
	//	      L = NEIGHBORS(currentNode);
	//	      nextEval = -INF;
	//	      nextNode = NULL;
	//	      for all x in L 
	//	         if (EVAL(x) > nextEval)
	//	              nextNode = x;
	//	              nextEval = EVAL(x);
	//	      if nextEval <= EVAL(currentNode)
	//	         //Return current node since no better neighbors exist
	//	         return currentNode;
	//	      currentNode = nextNode;

	public HillClimbing() {
		super(new PriorityQueue<State<?>>());
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Solution algorithm(Searchable<T> s) {
		//	currentNode = startNode;
		Set<State<T>> visited = new HashSet<State<T>>(); //visited
		State<T> next = null;
		State<T> state = s.getInitialState();
		state.setCost(s.getGrade(state));
		openList.add(state);
		double grade = Double.MAX_VALUE;

		//	loop do
		while(!openList.isEmpty()) { 
			State<T> currentState = (State<T>) openList.poll();
			visited.add(currentState);
			incEvaluatedNodes();
			next = null;
			grade = s.getGrade(currentState); 
			if(s.isGoalState(currentState)) { 
				return new Solution(s.getInitialState(),currentState);
			}

			//	L = NEIGHBORS(currentNode);
			List<State<T>> neighbors = s.getAllPossibleStates(currentState);
			for (State<T> neighbor : neighbors) { 
				if(!visited.contains(neighbor)&&!openList.contains(neighbor)) {
					double neighborcost = s.getGrade(neighbor);
					neighbor.setCost(neighborcost);
					openList.add(neighbor);
					if(grade>neighborcost) {
						grade = neighborcost;
						next = neighbor;
						neighbor.setCost(neighborcost);
					}
				}
			}
			if(next == null) {
				while(true) {
					Random random = new Random();
					if(!neighbors.isEmpty()) {
						int randomIndex = random.nextInt(neighbors.size());
						next = neighbors.get(randomIndex);
					}
					else {
						if(openList.isEmpty()) {
							return null;
						}
					}
					if(!visited.contains(next)) {
						if (!openList.contains(next)) {
							openList.add(next);
							break;
						}
						else {
							break; //no need to add
						}
					}
					
				}
			}
		}
		return null;
	} 
}
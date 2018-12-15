package searcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import searchable.Searchable;
import solver.Solution;
import state.State;



public class DFS extends CommonSearcher {

	
	
	public DFS() {
		// we need a fast queue
		super(null);
	}
	
	@Override
	public <T> Solution algorithm(Searchable<T> s) {
		Stack<State<T>> stack = new Stack<State<T>>();
		
		stack.add(s.getInitialState());
		Set<State<T>> closedSet = new HashSet<State<T>>();

		while (!stack.isEmpty()) {
			State<T> n = stack.pop(); 
			incEvaluatedNodes();
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				return new Solution(s.getInitialState(), n);
			}

			List<State<T>> neighbors = s.getAllPossibleStates(n);

			for (State<T> neighbor : neighbors) {
				if (!closedSet.contains(neighbor) && !stack.contains(neighbor)) {
					neighbor.setCameFrom(n);
					neighbor.setCost(n.getCost() + 1);
					stack.add(neighbor);
				}
			}
		}
		return null;
	}
}

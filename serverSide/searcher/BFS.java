package searcher;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import searchable.Searchable;
import solver.Solution;
import state.State;

public class BFS extends CommonSearcher {
	public BFS() {
		super(new ArrayDeque<State<?>>());
	}

	@Override
	public <T> Solution algorithm(Searchable<T> s) {
		addToOpenList(s.getInitialState());
		Set<State<T>> closedSet = new HashSet<State<T>>();

		while (!openList.isEmpty()) {
			State<T> n = popOpenList(); 
			closedSet.add(n);
			
			if (s.isGoalState(n)) {
				return new Solution(s.getInitialState(), n);
			}

			List<State<T>> neighbors = s.getAllPossibleStates(n);

			for (State<T> neighbor : neighbors) {
				if (!closedSet.contains(neighbor) && !(openList.contains(neighbor))) {
					neighbor.setCameFrom(n);
					neighbor.setCost(n.getCost() + 1); // update cost
					addToOpenList(neighbor);
				} else {
					// all edges are 1, no need to update the cost
				}
			}
		}
		return null;
	}


}

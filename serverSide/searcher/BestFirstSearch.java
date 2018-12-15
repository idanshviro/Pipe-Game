package searcher;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import searchable.Searchable;
import solver.Solution;
import state.State;

public class BestFirstSearch extends CommonSearcher {
	//Best-First-Search(Grah g, Node start)
	//1) Create an empty PriorityQueue
	//   PriorityQueue pq;
	//2) Insert "start" in pq.
	//   pq.insert(start)
	//3) Until PriorityQueue is empty
//	      u = PriorityQueue.DeleteMin
//	      If u is the goal
//	         Exit
//	      Else
//	         Foreach neighbor v of u
//	            If v "Unvisited"
//	                Mark v "Visited"                    
//	                pq.insert(v)
//	         		Mark v "Examined"                    
	//End procedure
	
	public BestFirstSearch() {
		super(new PriorityQueue<State<?>>());
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
				if (!closedSet.contains(neighbor)) {
					neighbor.setCameFrom(n);
					neighbor.setCost(n.getCost() + 1); 
					if (!openList.contains(neighbor)) {
						neighbor.setCost(s.getGrade(neighbor));
						addToOpenList(neighbor);
					}
				}
			}
		}
		return null;
	}
}

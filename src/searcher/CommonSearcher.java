package searcher;

import java.util.Queue;

import searchable.Searchable;
import solver.Solution;
import state.State;

public abstract class CommonSearcher implements Searcher {
	protected int evaluatedNodes;
	protected Queue<State<?>> openList;

	public CommonSearcher(Queue<State<?>> queue) {
		this.openList = queue;
		evaluatedNodes = 0;
	}

	public abstract <T> Solution algorithm(Searchable<T> s);


	// get how many nodes were evaluated by the algorithm
	public int getEvaluatedNodes() {
		return evaluatedNodes;
	}

	@SuppressWarnings("unchecked")
	protected <T> State<T> popOpenList() { 
		evaluatedNodes++;
		return (State<T>)openList.poll(); 
	}

	public void incEvaluatedNodes() {
		evaluatedNodes++;
	}
	public void addToOpenList(State<?> state) {
		openList.add(state);
	}

	@Override
	public void reset() {
		if (openList != null) {
			openList.clear();
		}
		evaluatedNodes = 0;
	}

	@Override
	public Solution search(Searchable<?> s) {
		long startTime = System.currentTimeMillis();
		System.out.println("algoSearch started");

		Solution solution = algorithm(s);

		long stopTime = System.currentTimeMillis();
		double elapsedTime = stopTime - startTime;
//		System.out.println("algorithm total seconds: " + elapsedTime/1000);
//		System.out.println("algorithm total evaluated nodes: " + evaluatedNodes);
		return solution;
	}
}

package searcher;

import java.util.Queue;

import searchable.Searchable;
import solver.Solution;
import state.State;

public abstract class CommonSearcher implements Searcher{
	protected Queue<State<?>> openList;
	private int evaluatedNodes;
	
	public CommonSearcher(Queue<State<?>> queue) {
		openList = queue;
		setEvaluatedNodes(0);
	}

	public int getEvaluatedNodes() {
		return evaluatedNodes;
	}

	public void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}
	
	public void incEvaluatedNodes() {
		this.evaluatedNodes++;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> State<T> popOpenList(){
		evaluatedNodes++;
		return (State<T>) openList.poll();
	}
	public void addToOpenList(State<?> state) {
		openList.add(state);
		
	}
	
	public abstract <T> Solution algorithm(Searchable<T> s);
	
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
//		System.out.println("algoSearch started");
		
		Solution solution = algorithm(s);
		
		long stopTime = System.currentTimeMillis();
		double elapsedTime = stopTime - startTime;
//		System.out.println("algoSearch total seconds: " + elapsedTime/1000);
//		System.out.println("algoSearch total evaluated nodes: " + evaluatedNodes);
		return solution;
	}
}

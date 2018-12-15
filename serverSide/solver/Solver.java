package solver;

import searchable.Searchable;
import state.Board;

public interface Solver {
	public Solution solve(Board problem);
	public Searchable<?> createProblem(Board b);//TODO should it get anything else?
}
package searcher;

import searchable.Searchable;
import solver.Solution;

public interface Searcher {
	
	// the search method
	public Solution search(Searchable<?> s);

	public void reset();

}

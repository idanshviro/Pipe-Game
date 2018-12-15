package searcher;
import searchable.Searchable;
import solver.Solution;

public interface Searcher {
	public Solution search(Searchable<?> s);
	public void reset();
}
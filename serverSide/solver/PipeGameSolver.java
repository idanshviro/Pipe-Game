package solver;

import searchable.SearchablePipeGame;
import searcher.Searcher;
import state.Board;
import state.State;

public class PipeGameSolver implements Solver {

	private Searcher searcher;

	public PipeGameSolver(Searcher searcher) {
		this.searcher = searcher;
	}
	
	
	public Searcher getSearcher() {
		return searcher;
	}
	public void setSearcher(Searcher searcher) {
		this.searcher = searcher;
	}


	@Override
	public Solution solve(Board problem) {
		SearchablePipeGame game = createProblem(problem);
		Solution solution = searcher.search(game);
		searcher.reset();
        return solution;
	}
 
	@Override
	public SearchablePipeGame createProblem(Board b) {
		State<Board> newState= new State<Board>(b);
		SearchablePipeGame newPipeGame = new SearchablePipeGame(newState);
		return newPipeGame;
	}

}

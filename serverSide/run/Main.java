package run;

import java.util.ArrayList;
import java.util.List;

import searchable.SearchablePipeGame;
import state.Board;
import state.State;

public class Main {
	public static void main(String[] args){
		List<char[]> boardList = new ArrayList<char[]>();
		boardList.add("s|g".toCharArray());
		Board b = new Board(boardList);
		State<Board> sb = new State<Board>(b);
		System.out.println("before: " + sb.getState().getBoard().get(0)[1]);
		
		SearchablePipeGame spg = new SearchablePipeGame(sb);
		spg.rotateOnBoard(0, 1);
		System.out.println("after: " + sb.getState().getBoard().get(0)[1]);
	}
}
package searchable;

import java.util.ArrayList;
import java.util.List;

import solver.Action;
import state.Board;
import state.State;

public class SearchablePipeGame extends MySearchable<Board>{
	public enum from {start, goal, right, left, up, down};
	private double grade;

	public SearchablePipeGame(State<Board> t) {
		super(t);
		grade = setGrade(t);
	}
	
	public Board rotateOnBoard(int i,int j) {
		Board boardAfterRotation = this.getInitialState().getState();
		boardAfterRotation.getBoard().get(i)[j] =  rotate(boardAfterRotation.getBoard().get(i)[j]);
		return boardAfterRotation;
	}
	
	public char rotate(char ch) {
		switch (ch) {
		case '-':
			return '|';
		case '|':
			return '-';
		case 'L':
			return 'F';
		case 'F':
			return '7';
		case '7':
			return 'J';
		case 'J':
			return 'L';
		case 'S':
		case 's':
			return 'S';
		case 'G':
		case 'g':
			return 'G';
		default:
			return ' ';
		}
	}
	//
	@Override
	public List<State<Board>> getAllPossibleStates(State<Board> s) {
		List<State<Board>> allPosStates = new ArrayList<State<Board>>();
		Board level = s.getState();
		int rows = level.getBoard().size();
		for (int i = 0; i < rows; i++) {
			int cols = level.getBoard().get(i).length;
			for (int j = 0; j < cols; j++) {
				char tile = level.getBoard().get(i)[j];
				if(tile == 's' || tile == 'g' || tile == ' ')
					continue;
				if(tile == '-' || tile == '|') {
					//create rotated board
					List<char[]> boardList = new ArrayList<char[]>();
					for(int k=0;k<s.getState().getBoard().size();k++) {
						String changedLine = new String(s.getState().getBoard().get(k));
						if(k == i) { 
							changedLine = changedLine.substring(0,j) + rotate(changedLine.charAt(j)) + changedLine.substring(j+1,changedLine.length());
						}
						boardList.add(changedLine.toCharArray());
					}
					//create state
					Board board = new Board(boardList);
					State<Board> possibleSate = new State<Board>(board);
					possibleSate.setCameFrom(s);
					possibleSate.setCost(s.getCost() + 1);
					if(isStartConnected(possibleSate) && !possibleSate.getState().outOfBounds(i, j) 
							&& possibleSate.getState().tileIsConnected(i, j))
					{
						Action a = new Action(j,i);
						possibleSate.setAction(a);
						allPosStates.add(possibleSate);
					}
					continue;
				}
				else {
					State<Board> copyOfs = s;
					for (int k = 1; k < 4; k++) {
						//State<Board> possibleSate = new State<Board>(level);
						//create rotated board
						List<char[]> boardList = new ArrayList<char[]>();
						for(int n=0;n<s.getState().getBoard().size();n++) {
							String changedLine = new String(s.getState().getBoard().get(n));
							if(n == i) { 
								for(int h=0;h<k;h++) {
									changedLine = changedLine.substring(0,j) + rotate(changedLine.charAt(j)) + changedLine.substring(j+1,changedLine.length());
								}
							}
							boardList.add(changedLine.toCharArray());
						}
						//create state
						Board board = new Board(boardList);
						State<Board> possibleSate = new State<Board>(board);
						//set state
						possibleSate.setCameFrom(copyOfs);
						Action a = new Action(j,i);
						possibleSate.setAction(a);
						possibleSate.setCost(copyOfs.getCost() + 1);
						if(isStartConnected(possibleSate) && !possibleSate.getState().outOfBounds(i, j) 
								&& possibleSate.getState().tileIsConnected(i, j))
						{
							allPosStates.add(possibleSate);
						}
						copyOfs = possibleSate;
					}
				}
			}
		}
		return allPosStates;
	}
	//	@Override
	//	public List<State<Board>> getAllPossibleStates(State<Board> s) {
	//
	//		List<State<Board>> arr = new ArrayList<State<Board>>();
	//
	//		for(int i=0;i<s.getState().getBoard().size();i++) {//for line to numoflines
	//			String line = new String(s.getState().getBoard().get(i));
	//			for(int j=0;j<line.length();j++) {//for letter to line.length
	//				if((!(line.charAt(j) == 's')) && (!(line.charAt(j) == 'g'))){
	//					List<char[]> boardList = new ArrayList<char[]>();
	//					for(int k=0;k<s.getState().getBoard().size();k++) {
	//						String changedLine = new String(s.getState().getBoard().get(k));
	//						if(k == i) { 
	//							changedLine = changedLine.substring(0,j) + rotate(changedLine.charAt(j)) + changedLine.substring(j+1,changedLine.length());
	//						}
	//						boardList.add(changedLine.toCharArray());
	//					}
	//					Board board = new Board(boardList);
	//					State<Board> possibleSate = new State<Board>(board);
	//					arr.add(possibleSate);
	//
	//					Action a = new Action(j,i);
	//					possibleSate.setAction(a);
	//				}
	//			}
	//		}
	//		return arr;
	//	}



	public int[] findChar(Board b,char ch) {
		int[] charPosition = {-1,-1};
		for (int i=0;i<b.getBoard().size();i++) {
			for (int j=0;j<b.getBoard().get(i).length;j++) {
				if(b.getBoard().get(i)[j] == ch) {
					charPosition[0] = i;
					charPosition[1] = j;
					return charPosition;
				}
			}
		}
		return charPosition;
	}

	private boolean hasPath(Board board, int[] pos, from f) {

		if((pos[0]<0 || pos[1]<0) || (pos[0]>=board.getBoard().size())) {
			return false;
		}
		else if(pos[1]>=board.getBoard().get(pos[0]).length) {
			return false;
		}

		char shape = board.getBoard().get(pos[0])[pos[1]];

		switch (shape) {
		case '-':
			if(f == from.left) {
				pos[1]++;
				return hasPath(board,pos, f);
			}
			else if(f == from.right) {
				pos[1]--;
				return hasPath(board,pos, f);
			}
			else {
				return false;
			}

		case '|':
			if(f == from.up) {
				pos[0]++;
				return hasPath(board, pos,f);
			}
			else if(f == from.down) {
				pos[0]--;
				return hasPath(board,pos,f);
			}
			else {
				return false;
			}

		case 'L':
			if (f == from.up) {
				pos[1]++;
				return hasPath(board,pos,from.left);
			}
			else if(f== from.right) {
				pos[0]--;
				return hasPath(board,pos,from.down);
			}
			else {
				return false;
			}

		case 'F':
			if(f==from.down) {
				pos[1]++;
				return hasPath(board,pos,from.left);
			}
			else if(f==from.right) {
				pos[0]++;
				return hasPath(board,pos,from.up);
			}
			else {
				return false;
			}

		case '7':
			if(f==from.left) {
				pos[0]++;
				return hasPath(board, pos, from.up);
			}
			else if(f==from.down) {
				pos[1]--;
				return hasPath(board, pos, from.right);
			}
			else {
				return false;
			}

		case 'J':
			if(f==from.up) {
				pos[1]--;
				return hasPath(board, pos, from.right);
			}
			else if(f==from.left) {
				pos[0]--;
				return hasPath(board, pos, from.down);
			}
			else {
				return false;
			}

		case 'S': 
		case 's':
			if(f == from.start) {
				int[] temp = {pos[0],pos[1]};
				pos[0]--;
				boolean a1 = hasPath(board, pos,from.down);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[0]++;
				boolean a2 = hasPath(board, pos,from.up);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[1]--;
				boolean a3 = hasPath(board, pos,from.right);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[1]++;
				boolean a4 = hasPath(board, pos,from.left);
				pos[0] = temp[0];
				pos[1] = temp[1];
				return (a1||a2||a3||a4);
			}
			else {
				return false;
			}
		case 'G':
		case 'g':
			if(f==from.up) {
				if(board.getBoard().get(pos[0]-1)[pos[1]] == 's') {
					return false;
				}
				else if(board.getBoard().get(pos[0]-1)[pos[1]] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.down) {
				if(board.getBoard().get(pos[0]+1)[pos[1]] == 's') {
					return false;
				}
				else if(board.getBoard().get(pos[0]+1)[pos[1]] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.left) {
				if(board.getBoard().get(pos[0])[pos[1]-1] == 's') {
					return false;
				}
				else if(board.getBoard().get(pos[0])[pos[1]-1] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.right) {
				if(board.getBoard().get(pos[0])[pos[1]+1] == 's') {
					return false;
				}
				else if(board.getBoard().get(pos[0])[pos[1]+1] == 'S') {
					return false;
				}
				return true;
			}
			return false;
		default:
			return false;
		}
	}

	@Override
	public boolean isGoalState(State<Board> state) {
		Board board = state.getState();
		int[] startPos = findChar(board, 's');
		int[] goalPos = findChar(board, 'g');

		if((startPos[0] < 0)||(startPos[1] < 0)){
			startPos = findChar(board, 'S');
			if((startPos[0] < 0)||(startPos[1] < 0)){
				return false;
			}
		}
		if((goalPos[0] < 0)||(goalPos[1] < 0)){
			goalPos = findChar(board, 'G');
			if((goalPos[0] < 0)||(goalPos[1] < 0)){
				return false;
			}
		}
		return hasPath(board, startPos,from.start);
	}

	private double min(double a1,double a2,double a3,double a4) {
		double min = (a1 < a2)? a1:a2;
		min = (min<a3)?min:a3;
		min = (min<a4)?min:a4;
		return min;
	}

	@Override
	public double getGrade(State<Board> state) {
		grade = setGrade(state);
		return grade;
	}

	private double setGrade(State<Board> state) {
		Board board = state.getState();
		int[] startPos = findChar(board, 's');
		int[] goalPos = findChar(board, 'g');
		if((startPos[0] < 0)||(startPos[1] < 0)){
			startPos = findChar(board, 'S');
			if((startPos[0] < 0)||(startPos[1] < 0)){
				return Double.MAX_VALUE;
			}
		}
		if((goalPos[0] < 0)||(goalPos[1] < 0)){
			goalPos = findChar(board, 'G');
			if((goalPos[0] < 0)||(goalPos[1] < 0)){
				return Double.MAX_VALUE;
			}
		}
		return findGrade(board, startPos, goalPos, from.start);
	}

	private double findGrade(Board board, int[] pos,int[] goal, from f) {

		if((pos[0]<0 || pos[1]<0) || (pos[0]>=board.getBoard().size())) {
			if(f==from.up) {
				if((board.getBoard().get(pos[0]-1)[pos[1]])!='s') {
					return (Math.abs(goal[0]-(pos[0]-1))+ Math.abs(goal[1]-pos[1]));
				}
				else { return Double.MAX_VALUE; }
			} 
			if(f==from.down) {
				if((board.getBoard().get(pos[0]+1)[pos[1]])!='s') {
					return (Math.abs(goal[0]-(pos[0]+1))+ Math.abs(goal[1]-pos[1]));
				}
				else { return Double.MAX_VALUE; }
			}
			if(f==from.left) {
				if((board.getBoard().get(pos[0])[pos[1]-1])!='s') {
					return (Math.abs(goal[0]-(pos[0]))+ Math.abs(goal[1]-(pos[1]-1)));
				}
				else { return Double.MAX_VALUE; }
			}
			if(f==from.right) {
				if((board.getBoard().get(pos[0])[pos[1]+1])!='s') {
					return (Math.abs(goal[0]-(pos[0]))+ Math.abs(goal[1]-(pos[1]+1)));
				}
				else { return Double.MAX_VALUE; }
			}
			return Double.MAX_VALUE; 
		}
		else if(pos[1]>=board.getBoard().get(pos[0]).length) {
			if(f==from.left) {
				if((board.getBoard().get(pos[0])[pos[1]-1])!='s') {
					return (Math.abs(goal[0]-(pos[0]))+ Math.abs(goal[1]-(pos[1]-1)));
				}
				else { return Double.MAX_VALUE; }
			}
		}

		char shape = board.getBoard().get(pos[0])[pos[1]];

		switch (shape) {
		case '-':
			if(f == from.left) {
				pos[1]++;
				return findGrade(board,pos,goal, f);
			}
			else if(f == from.right) {
				pos[1]--;
				return findGrade(board,pos,goal, f);
			}
			else if (f==from.up) {
				if(board.getBoard().get(pos[0]-1)[pos[1]] == 's') {
					return (Math.abs(goal[0]-(pos[0]-1))+ Math.abs(goal[1]-pos[1]));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else if(f==from.down){
				if(board.getBoard().get(pos[0]+1)[pos[1]] == 's') {
					return (Math.abs(goal[0]-(pos[0]+1))+ Math.abs(goal[1]-pos[1]));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}

		case '|':
			if(f == from.up) {
				pos[0]++;
				return findGrade(board, pos,goal,f);
			}
			else if(f == from.down) {
				pos[0]--;
				return findGrade(board, pos,goal,f);
			}
			else if(f==from.left){
				if(board.getBoard().get(pos[0])[pos[1]-1] == 's') {
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-(pos[1]-1)));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else if(f==from.right){
				if(board.getBoard().get(pos[0])[pos[1]+1] == 's') {
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-(pos[1]+1)));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else {
				return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
			}

		case 'L':
			if (f == from.up) {
				pos[1]++;
				return findGrade(board, pos,goal,from.left);
			}
			else if(f== from.right) {
				pos[0]--;
				return findGrade(board, pos,goal,from.down);
			}
			else if(f==from.left){
				if(board.getBoard().get(pos[0])[pos[1]-1] == 's') {
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-(pos[1]-1)));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else if(f==from.down){
				if(board.getBoard().get(pos[0]+1)[pos[1]] == 's') {
					return (Math.abs(goal[0]-(pos[0]+1))+ Math.abs(goal[1]-pos[1]));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else {
				return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
			}

		case 'F':
			if(f==from.down) {
				pos[1]++;
				return findGrade(board, pos,goal,from.left);
			}
			else if(f==from.right) {
				pos[0]++;
				return findGrade(board, pos,goal,from.up);
			}
			else if(f==from.left){
				if(board.getBoard().get(pos[0])[pos[1]-1] == 's') {
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-(pos[1]-1)));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else if(f==from.up){
				if(board.getBoard().get(pos[0]-1)[pos[1]] == 's') {
					return (Math.abs(goal[0]-(pos[0]-1))+ Math.abs(goal[1]-pos[1]));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else {
				return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
			}

		case '7':
			if(f==from.left) {
				pos[0]++;
				return findGrade(board, pos,goal,from.up);
			}
			else if(f==from.down) {
				pos[1]--;
				return findGrade(board, pos,goal,from.right);
			}
			else if(f==from.right){
				if(board.getBoard().get(pos[0])[pos[1]+1] == 's') {
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-(pos[1]+1)));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else if(f==from.up){
				if(board.getBoard().get(pos[0]-1)[pos[1]] == 's') {
					return (Math.abs(goal[0]-(pos[0]-1))+ Math.abs(goal[1]-pos[1]));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else {
				return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
			}

		case 'J':
			if(f==from.up) {
				pos[1]--;
				return findGrade(board, pos,goal,from.right);
			}
			else if(f==from.left) {
				pos[0]--;
				return findGrade(board, pos,goal,from.down);
			}
			else if(f==from.right){
				if(board.getBoard().get(pos[0])[pos[1]+1] == 's') {
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]+1));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else if(f==from.down){
				if(board.getBoard().get(pos[0]+1)[pos[1]] == 's') {
					return (Math.abs(goal[0]-(pos[0]+1))+ Math.abs(goal[1]-pos[1]));
				}
				else{
					return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
				}
			}
			else {
				return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
			}

		case 'S': 
		case 's':
			if(f == from.start) {
				int[] temp = {pos[0],pos[1]};
				pos[0]--;
				Double a1 = findGrade(board, pos,goal,from.down);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[0]++;
				Double a2 = findGrade(board, pos,goal,from.up);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[1]--;
				Double a3 = findGrade(board, pos,goal,from.right);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[1]++;
				Double a4 = findGrade(board, pos,goal,from.left);
				pos[0] = temp[0];
				pos[1] = temp[1];
				return min(a1,a2,a3,a4);
			}
			else {
				return (Math.abs(goal[0]-pos[0])+ Math.abs(goal[1]-pos[1]));
			}
		case 'G':
		case 'g':
			if(f==from.up) {
				if(board.getBoard().get(pos[0]-1)[pos[1]] == 's') {
					return Double.MAX_VALUE;
				}
				else if(board.getBoard().get(pos[0]-1)[pos[1]] == 'S') {
					return Double.MAX_VALUE;
				}
				return 0;
			}
			else if(f==from.down) {
				if(board.getBoard().get(pos[0]+1)[pos[1]] == 's') {
					return Double.MAX_VALUE;
				}
				else if(board.getBoard().get(pos[0]+1)[pos[1]] == 'S') {
					return Double.MAX_VALUE;
				}
				return 0;
			} 
			else if(f==from.left) {
				if(board.getBoard().get(pos[0])[pos[1]-1] == 's') {
					return Double.MAX_VALUE;
				}
				else if(board.getBoard().get(pos[0])[pos[1]-1] == 'S') {
					return Double.MAX_VALUE;
				}
				return 0;
			}
			else if(f==from.right) {
				if(board.getBoard().get(pos[0])[pos[1]+1] == 's') {
					return Double.MAX_VALUE;
				}
				else if(board.getBoard().get(pos[0])[pos[1]+1] == 'S') {
					return Double.MAX_VALUE;
				}
				return 0;
			}
			return Double.MAX_VALUE;
		default:
			return Double.MAX_VALUE;
		}

	}



	private boolean isStartConnected(State<Board> state) {
		int[] startPos = findChar(state.getState(), 's');

		if((startPos[0] < 0)||(startPos[1] < 0)){
			startPos = findChar(state.getState(), 'S');
			if((startPos[0] < 0)||(startPos[1] < 0)){
				return false;
			}
		}

		Board board = state.getState();  

		int startY = startPos[0];
		int startX = startPos[1];

		char up='x',down='x',left='x',right='x';

		if(startY>0) {
			up = board.getBoard().get(startY-1)[startX];
		}

		if(startY<(board.getBoard().size()-1)) {
			down = board.getBoard().get(startY+1)[startX];
		}

		if(startX>0) {
			left = board.getBoard().get(startY)[startX-1];
		}

		if(startX<(board.getBoard().get(startY).length-1)) {
			right = board.getBoard().get(startY)[startX+1];
		}

		if(up == '|'|| up == '7'|| up =='F') {
			return true;
		}

		if(down == '|'|| down == 'L'|| down =='J') {
			return true;
		}

		if(left == '-'|| left == 'F'|| left == 'L') {
			return true;
		}

		if(right == '-'|| right == 'J'|| right =='7') {
			return true;
		}
		return false;
	}

}

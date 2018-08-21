package state;

import java.util.Iterator;
import java.util.List;

public class Board {
	final List<char[]> board;
	private int hash;

	public Board(List<char[]> board){
		this.board = board;
		this.hash = hasHelper();
	}

	//Getter
	public List<char[]> getBoard() {
		return board;
	}

	@Override
	public int hashCode() {
		return hash;
	}

	public int hasHelper() {
		hash = 1;
		Iterator<char[]> i = this.board.iterator();
		while (i.hasNext()) {
			char[] obj = (char[])i.next();
			for(int j=0;j<obj.length;j++){
				hash= 31*hash + (obj==null ? 0 : (int)obj[j]);
			}
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof Board) {
			equals = obj!=null && (this.hashCode() == obj.hashCode());
		}
		return equals;
	}

	@Override
	public String toString() {
		String str = new String(board.get(0));
		for(int i=1;i<board.size();i++) {
			String temp = new String(board.get(i));
			str = str + "," + temp;
		}
		return str;
	}

	//check if specific letter is going out of bounds
	public boolean OutOfBounds(int i,int j) {
		if (board.get(i)[j]=='L') {
			return (i==0)||(j==((board.get(i).length)-1));
		}
		else if (board.get(i)[j]=='F') {
			return (i==(board.size()-1))||(j==((board.get(i).length)-1));
		}
		else if (board.get(i)[j]=='7') {
			return ((i==(board.size()-1))||(j==0));
		}
		else if (board.get(i)[j]=='J') {
			return (i==0)||(j==0);
		}
		else if (board.get(i)[j]=='-') {
			return (j==0)||(j==(board.get(i).length-1));
		}
		else if (board.get(i)[j]=='|') {
			return (i==0)||(i==(board.size()-1));
		}
		else{
			return false;
		}
	}

//check if there is any connection for specific letter
	public boolean tileIsConnected(int i, int j) {
		char tile = board.get(i)[j];
		if(tile == 'L') {
			//go up
			if(i>0) {
				char ch = board.get(i-1)[j];
				switch (ch) {
				case 's':
				case 'g':
				case '|':
				case '7':
				case 'F':
					return true;
				default:
					break;
				}
			}
			//go right
			if(j<(board.get(i).length-1)) {
				char ch = board.get(i)[j+1];
				switch (ch) {
				case 's':
				case 'g':
				case '-':
				case 'J':
				case '7':
					return true;
				default:
					break;
				}
			}
			return false;
		}
		else if(tile == 'F') {
			//go right
			if(j<(board.get(i).length-1)) {
				char ch = board.get(i)[j+1];
				switch (ch) {
				case 's':
				case 'g':
				case '-':
				case 'J':
				case '7':
					return true;
				default:
					break;
				}
			}
			//go down
			if(i<board.size()-1) {
				char ch = board.get(i+1)[j];
				switch (ch) {
				case 's':
				case 'g':
				case '|':
				case 'J':
				case 'L':
					return true;
				default:
					break;
				}
			}
			return false;
		}
		else if(tile == '7') {
			//go down
			if(i<board.size()-1) {
				char ch = board.get(i+1)[j];
				switch (ch) {
				case 's':
				case 'g':
				case '|':
				case 'J':
				case 'L':
					return true;
				default:
					break;
				}
			}
			//go left
			if(j>0) {
				char ch = board.get(i)[j-1];
				switch (ch) {
				case 's':
				case 'g':
				case '-':
				case 'F':
				case 'L':
					return true;
				default:
					break;
				}
			}
			return false;
		}
		else if(tile == 'J') {
			//go left
			if(j>0) {
				char ch = board.get(i)[j-1];
				switch (ch) {
				case 's':
				case 'g':
				case '-':
				case 'F':
				case 'L':
					return true;
				default:
					break;
				}
			}
			//go up
			if(i>0) {
				char ch = board.get(i-1)[j];
				switch (ch) {
				case 's':
				case 'g':
				case '|':
				case 'F':
				case '7':
					return true;
				default:
					break;
				}
			}
			return false;
		}
		else if(tile == '-') {
			//go left
			if(j>0) {
				char ch = board.get(i)[j-1];
				switch (ch) {
				case 's':
				case 'g':
				case '-':
				case 'F':
				case 'L':
					return true;
				default:
					break;
				}
			}
			//go right
			if(j<(board.get(i).length-1)) {
				char ch = board.get(i)[j+1];
				switch (ch) {
				case 's':
				case 'g':
				case '-':
				case 'J':
				case '7':
					return true;
				default:
					break;
				}
			}
			return false;
		}
		else if(tile == '|') {
			//go down
			if(i<board.size()-1) {
				char ch = board.get(i+1)[j];
				switch (ch) {
				case 's':
				case 'g':
				case '|':
				case 'J':
				case 'L':
					return true;
				default:
					break;
				}
			}
			//go up
			if(i>0) {
				char ch = board.get(i-1)[j];
				switch (ch) {
				case 's':
				case 'g':
				case '|':
				case '7':
				case 'F':
					return true;
				default:
					break;
				}
			}
			return false;
		}
		else if(tile == 's') {
			return false;
		}
		else if(tile == 'g') {
			return false;
		}
		else {
			return false; 
		}
	}
}
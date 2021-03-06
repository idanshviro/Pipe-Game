package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import client.Client;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;



public class PipeGameModel implements GameModel{

	public enum from {start, goal, right, left, up, down};
	public ListProperty<char[]> board;
	public BooleanProperty isGoal;
	private int port;
	private String host;



	public PipeGameModel() {
		this.board = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
		initializeBoard();
		this.port = 6400;
		this.host = "localhost";
		this.isGoal = new SimpleBooleanProperty();
	}
	

	public boolean save() throws FileNotFoundException {
		String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
		try (PrintWriter out = new PrintWriter("./resources/Saves/"+timeStamp+".txt")) {
			for(int i=0;i<board.size();i++) {
				String str = new String( (board.getValue().get(i)) );
				if(i==board.size()-1) { 
					out.print(str);
				}
				else{out.println(str);}
			}
			out.close();
			File f = new File("./resources/Saves/"+timeStamp+".txt");
			if(f.exists())
				return true;
			else
				return false;
		}
	}

	public List<String> solve() throws Exception {
		String str = "";
		for(int i=0;i<board.getSize();i++) 
		{
			str += new String(board.getValue().get(i));
			str+= '\n';
		}
		str+= "done" + '\n';
		List<String> solution = Client.getSolutionFromServer(str , port , host);
		return solution;
	}

	public void loadLevel(File f){
		Scanner scanner = null;
//		List<char[]> level = new ArrayList<char[]>();
		try {
			scanner = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(!scanner.equals(null)) {
			this.board.clear();
		}
		while (scanner.hasNext()){
			char[] line=null;
			String string = scanner.nextLine();	
			line = string.toCharArray();
			this.board.add(line);
		}
		scanner.close();
		isGoal.set(isGoalState());
	}




	public void rotate(int i , int j) {
		switch (this.board.get(i)[j]) {
		case '-':
			this.board.get(i)[j] = '|';
			break;
		case '|':
			this.board.get(i)[j] = '-';
			break;
		case 'L':
		case 'l':
			this.board.get(i)[j] = 'F';
			break;
		case 'F':
		case 'f':
			this.board.get(i)[j] = '7';
			break;
		case '7':
			this.board.get(i)[j] = 'J';
			break;
		case 'J':
		case 'j':
			this.board.get(i)[j] = 'L';
			break;
		case 'S':
		case 's':
			this.board.get(i)[j] = 'S';
			break;
		case 'G':
		case 'g':
			this.board.get(i)[j] = 'G';
			break;
		default:
			this.board.get(i)[j] = ' ';
			break;
		}
	}

	public boolean isGoalState() {
		int[] startPos = findChar('s');
		int[] goalPos = findChar('g');

		if((startPos[0] < 0)||(startPos[1] < 0)){
			startPos = findChar('S');
			if((startPos[0] < 0)||(startPos[1] < 0)){
				return false;
			}
		}
		if((goalPos[0] < 0)||(goalPos[1] < 0)){
			goalPos = findChar('G');
			if((goalPos[0] < 0)||(goalPos[1] < 0)){
				return false;
			}
		}
		return hasPath(startPos,from.start);
	}

	public int[] findChar(char ch) {
		int[] charPosition = {-1,-1};
		for (int i=0;i<board.size();i++) {
			for (int j=0;j<board.get(i).length;j++) {
				if(board.get(i)[j] == ch) {
					charPosition[0] = i;
					charPosition[1] = j;
					return charPosition;
				}
			}
		}
		return charPosition;
	}

	private boolean hasPath(int[] pos, from f) {

		if((pos[0]<0 || pos[1]<0) || (pos[0]>=board.getSize())) {
			return false;
		}
		else if(pos[1]>=board.get(pos[0]).length) {
			return false;
		}

		char shape = board.get(pos[0])[pos[1]];

		switch (shape) {
		case '-':
			if(f == from.left) {
				pos[1]++;
				return hasPath(pos, f);
			}
			else if(f == from.right) {
				pos[1]--;
				return hasPath(pos, f);
			}
			else {
				return false;
			}

		case '|':
			if(f == from.up) {
				pos[0]++;
				return hasPath(pos,f);
			}
			else if(f == from.down) {
				pos[0]--;
				return hasPath(pos,f);
			}
			else {
				return false;
			}

		case 'L':
			if (f == from.up) {
				pos[1]++;
				return hasPath( pos,from.left);
			}
			else if(f== from.right) {
				pos[0]--;
				return hasPath(pos,from.down);
			}
			else {
				return false;
			}

		case 'F':
			if(f==from.down) {
				pos[1]++;
				return hasPath(pos,from.left);
			}
			else if(f==from.right) {
				pos[0]++;
				return hasPath(pos,from.up);
			}
			else {
				return false;
			}

		case '7':
			if(f==from.left) {
				pos[0]++;
				return hasPath( pos, from.up);
			}
			else if(f==from.down) {
				pos[1]--;
				return hasPath( pos, from.right);
			}
			else {
				return false;
			}

		case 'J':
			if(f==from.up) {
				pos[1]--;
				return hasPath( pos, from.right);
			}
			else if(f==from.left) {
				pos[0]--;
				return hasPath( pos, from.down);
			}
			else {
				return false;
			}

		case 'S': 
		case 's':
			if(f == from.start) {
				int[] temp = {pos[0],pos[1]};
				pos[0]--;
				boolean a1 = hasPath( pos,from.down);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[0]++;
				boolean a2 = hasPath( pos,from.up);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[1]--;
				boolean a3 = hasPath( pos,from.right);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[1]++;
				boolean a4 = hasPath( pos,from.left);
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
				if(board.get(pos[0]-1)[pos[1]] == 's') {
					return false;
				}
				else if(board.get(pos[0]-1)[pos[1]] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.down) {
				if(board.get(pos[0]+1)[pos[1]] == 's') {
					return false;
				}
				else if(board.get(pos[0]+1)[pos[1]] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.left) {
				if(board.get(pos[0])[pos[1]-1] == 's') {
					return false;
				}
				else if(board.get(pos[0])[pos[1]-1] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.right) {
				if(board.get(pos[0])[pos[1]+1] == 's') {
					return false;
				}
				else if(board.get(pos[0])[pos[1]+1] == 'S') {
					return false;
				}
				return true;
			}
			return false;
		default:
			return false;
		}
	}


	public void initializeBoard() {
		List<char[]> level = new ArrayList<char[]>();
		level.add("s||L".toCharArray());
		level.add("---g".toCharArray());
		this.board.addAll(level);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}

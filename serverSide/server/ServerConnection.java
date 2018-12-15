package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import clientHandler.ClientHandler;


public class ServerConnection implements Runnable, Comparable<ServerConnection>{
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private List<char[]> clientinput;
	private int priority = 5;
	private ClientHandler clienthandler;

	public int getPriority() {
		return priority;
	}

	public ServerConnection(Socket socket,ClientHandler clienthandler) {
		this.socket = socket;
		this.clienthandler = clienthandler;
		try {
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {//sets priority by clientInput
			this.clientinput = getClientInput(this.in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<char[]> getClientInput(InputStream input) throws IOException {
		//Get input from Client and setPriority=numoflines*longestlinelength
		BufferedReader inFClient = new BufferedReader(new InputStreamReader(input));
		List<char[]> clientInput = new ArrayList<char[]>();
		int i=0, maxline = 0;
		try {
			String line;
			while (!(line = inFClient.readLine()).equals("done")) {
				clientInput.add(line.toCharArray());
				if (maxline<line.length()) { maxline = line.length();}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.priority = i * maxline;
		return clientInput; //returns the game board in order to pass it to clientHandler
	}

	public void run() {
		System.out.println(this.getPriority());//print on server to know the order of handling
		clienthandler.handle(in, out, clientinput);
	}

	
	@Override
	public int compareTo(ServerConnection obj) {
		if (this.priority > obj.priority) {return 1;}
		else if(this.priority==obj.priority) {return 0;}
		else {return -1;}
	}
}
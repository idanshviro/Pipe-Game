package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Client {

	public static List<String> getSolutionFromServer(String board , int port , String host) throws Exception {

		Socket theServer = new Socket(host, port);
		BufferedReader inFromUser = new BufferedReader(new StringReader(board));
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());

		String line;
		while (!(line = inFromUser.readLine()).equals("done")) {
			outToServer.println(line);//Don't forget to use line break.
			outToServer.flush();
		}
		outToServer.println("done");
		outToServer.flush();
		List<String> solution = new ArrayList<>();
		String newline;
		while (!(newline = inFromServer.readLine()).equals("done")) {
			solution.add(newline);
		}
		inFromServer.close();
		outToServer.close();
		inFromUser.close();
		theServer.close();
		return solution;
	}

}

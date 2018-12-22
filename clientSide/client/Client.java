package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Arc;

public class Client {

	public static List<String> getSolutionFromServer(String board) throws Exception {

		System.out.println("**** Client Side ****");

		Socket theServer = new Socket("localhost", 6400);
		System.out.println("Connected to server");
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
			System.out.println(newline);
		}
		//		System.out.println("closing everything");
		//Close everything
		inFromServer.close();
		outToServer.close();
		inFromUser.close();
		theServer.close();
		return solution;
	}

}

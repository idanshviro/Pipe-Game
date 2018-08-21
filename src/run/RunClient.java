package run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RunClient {

	public static void main(String[] args) throws Exception {
		//Remove the exception to try/catch
//		System.out.println("*** Client Side ***");

		Socket theServer = new Socket("localhost", 6400);
//		System.out.println("Connected to server");
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());

		String line;
		while (!(line = inFromUser.readLine()).equals("done")) {
			outToServer.println(line);//Don't forget to use line break.
			outToServer.flush();
		}
		outToServer.println("done");
		outToServer.flush();
		String newline;
		while (!(newline = inFromServer.readLine()).equals("done")) {
//			System.out.println(newline);
		}
		System.out.println("closing everything");
		//Close everything
		inFromServer.close();
		outToServer.close();
		inFromUser.close();
		theServer.close(); 
	}

}
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import clientHandler.ClientHandler;

public class MyServer implements Server{

	private ServerSocket serverSocket;
	private int port;
	private boolean stop = false;

	public MyServer(int port) {
		this.port = port;
	}

	private void startServer(ClientHandler clientHandler) throws IOException {
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(1000); 
//		System.out.println("Server connected - waiting");

		while (!stop) {
			try {
				Socket aClient = serverSocket.accept();
//				System.out.println("client connected");

				clientHandler.handle(aClient.getInputStream(), aClient.getOutputStream());
				//the ch is responsible for closing the streams

				aClient.close();
			} catch (SocketTimeoutException e) {
				//		                System.out.println("Client did not connect...");
			}
		}
//		System.out.println("Server closed");
		serverSocket.close();


	}
	@Override 
	public void start(ClientHandler clientHandler) {
		new Thread(() -> {
			try {
				startServer(clientHandler);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}
	public void stop() {
		System.out.println("Done");
	}




}

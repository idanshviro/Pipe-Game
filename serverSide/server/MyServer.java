package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import clientHandler.ClientHandler;

public class MyServer implements Server {
	private ServerSocket serverSocket;
	private int port;
	volatile private boolean stop = false;
	private ThreadPoolExecutor executor;


	public MyServer(int port, int numOfThreads) {
		this.port = port;
		this.executor = new ThreadPoolExecutor(numOfThreads, numOfThreads, 10, TimeUnit.SECONDS,
				new PriorityBlockingQueue<Runnable>());
	}

	public void startServer(ClientHandler clientHandler){

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while(!stop) {

			try {
				serverSocket.setSoTimeout(3000);
				Socket clientSocket = serverSocket.accept();
				new Thread(()->{
					ServerConnection sc = new ServerConnection(clientSocket, clientHandler);
					executor.execute(sc);
				}).start();
			} catch (IOException e) {
			}
		}


		//stop is true
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}



	@Override
	public void start(ClientHandler clientHandler) {
		new Thread(() -> {
			try {
				startServer(clientHandler);
			} catch (Exception e) {
			}
		}).start();
	}

	@Override
	public void stop() {
		this.stop = true;
	}
}

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import clientHandler.ClientHandler;

public class MyServer implements Server {
	private ServerSocket serverSocket;
	private int port;
	volatile private boolean stop = false;
	volatile private boolean done = false;
	volatile private PriorityBlockingQueue<Runnable> pool;
	private ThreadPoolExecutor executor;


	public MyServer(int port, int numOfThreads) {
		this.port = port;
		pool = new PriorityBlockingQueue<Runnable>();
		this.executor = new ThreadPoolExecutor(numOfThreads, numOfThreads, 10, TimeUnit.SECONDS,
				pool);
	}

	public void startServer(ClientHandler clientHandler) {
		Thread addToPool = new Thread(){	
			public void run() {
				try {
					serverSocket = new ServerSocket(port);
					serverSocket.setSoTimeout(1000);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				while (!stop) {
					try {
						Socket aClient = serverSocket.accept();
						ServerConnection sc = new ServerConnection(aClient,clientHandler);
						pool.offer(sc);
					} catch (SocketTimeoutException e) {
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				//stop
				done=true;
			}
		};

		Thread execute = new Thread(){	
			public void run() {
				while(!done) {
					if(!executor.getQueue().isEmpty()){
						try {
							ServerConnection sc = (ServerConnection) pool.take();
							executor.execute(sc);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				executor.shutdown();
				try {
					executor.awaitTermination(5, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		addToPool.start();
		execute.start();

		//stop()
		if(stop) {
			try {
				while(!executor.isShutdown()) 
				{/*wait for shutdown to complete prior to closing the socket*/};
						serverSocket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
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
		stop = true;
	}
}

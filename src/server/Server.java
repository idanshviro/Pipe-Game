package server;


import clientHandler.ClientHandler;

public interface Server {
	public void start(ClientHandler ch);
	public void stop();

}

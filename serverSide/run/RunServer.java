package run;

import clientHandler.MyCHandler;
import server.Server;
import server.MyServer;

public class RunServer {
    public static void main(String[] args) throws Exception {
    	
    	final int M = 5; 
        Server s = new MyServer(6400,M);//Take the port from the args
        s.start(new MyCHandler());
        System.in.read();
        s.stop();
    }
}

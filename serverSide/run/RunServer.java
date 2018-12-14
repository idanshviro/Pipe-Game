package run;

import clientHandler.MyCHandler;
import server.MyServer;
import server.Server;

public class RunServer {
    public static void main(String[] args) throws Exception {
//        System.out.println("**** Server Side ****");

//        Server s = new MyServer(Integer.parseInt(args[0]));//Take the port from the args
        Server s = new MyServer(6400);//Take the port from the args
        s.start(new MyCHandler());
        System.in.read();
        s.stop(); 
//        System.out.println("Closed server");
    }
}

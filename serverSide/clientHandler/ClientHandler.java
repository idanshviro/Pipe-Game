package clientHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface ClientHandler {
    void handle(InputStream inFromClient, OutputStream outToClient,List<char[]> clientInput);
}

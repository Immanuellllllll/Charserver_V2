package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    //private PrintWriter out;
   // private BufferedReader br;
    Datacontainer dc;


    public void startServer() throws IOException {
        serverSocket = new ServerSocket();
        clientSocket = serverSocket.accept();
      //  out = new PrintWriter(clientSocket.getOutputStream(), true);
       // br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stopServer() throws IOException {
        serverSocket.close();
        clientSocket.close();
    }
}

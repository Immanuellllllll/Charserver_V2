package Server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Reciever extends Thread {
    private DataInputStream in = null;
    String message=null;

    @Override
    public void run() {
        while (true) {
            for (int i=0; i<Datacontainer.clientlist.size();i++) {
                Socket socket=Datacontainer.clientlist.get(i).getSocket();

                try {
                    in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    //socket.setSoTimeout(1);
                    message = in.readUTF();
                    Datacontainer.messagelist.add(new Message(socket.toString(),message));
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}

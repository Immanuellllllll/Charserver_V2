package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Sender extends Thread {


    private DataOutputStream out = null;
    Message message = null;


    @Override
    public void run() {
        while (true) {
            if (Datacontainer.messagelist.size() > 0) {
                message = Datacontainer.messagelist.poll();
                for (int i = 0; i < Datacontainer.clientlist.size(); i++) {
                    Socket socket = Datacontainer.clientlist.get(i).getSocket();
                    if (!message.getUser().equals(socket.toString())) {
                        try {
                            out = new DataOutputStream(socket.getOutputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            out.writeUTF(message.getText());
                        } catch (IOException e) {
                            System.out.println(e);
                        }


                        // System.out.println(e);
                    }
                }
            }
        }
    }
}


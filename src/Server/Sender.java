package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sender extends Thread {


    private DataOutputStream out = null;
    Message message = null;
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime myDateObj = LocalDateTime.now();
    String formattedDate = myDateObj.format(myFormatObj);

    @Override
    public void run() {
        while (true) {
            if (!Datacontainer.messagelist.isEmpty()) {
                message = Datacontainer.messagelist.poll();

                for (int i = 0; i < Datacontainer.clientlist.size(); i++) {

                    Socket socket = Datacontainer.clientlist.get(i).getSocket();
                    if (!message.getUser().equals(Datacontainer.clientlist.get(i).getUserName())) {
                        try {
                            out = new DataOutputStream(socket.getOutputStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            out.writeUTF(formattedDate+" "+message.getUser() + ": " + message.getText());
                        } catch (IOException e) {
                            System.out.println(Datacontainer.clientlist.get(i).getUserName()+" disconnected");
                            Datacontainer.clientlist.remove(i);
                            System.out.println(e);
                        }


                        // System.out.println(e);
                    }
                }
            }
        }
    }
}


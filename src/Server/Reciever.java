package Server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

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
                    socket.setSoTimeout(1);
                    message = in.readUTF();
                    if (!message.equals("IMAV")) {
                        Datacontainer.messagelist.add(new Message(Datacontainer.clientlist.get(i).getUserName(), message));
                    }
                    else{
                        Datacontainer.clientlist.get(i).setLastHeartbeat(System.currentTimeMillis());
                    }
                } catch (IOException e) {
                    //System.out.println(e);
                }
                try {
                if (System.currentTimeMillis()>Datacontainer.clientlist.get(i).getLastHeartbeat()+61000) {
                    System.out.println(Datacontainer.clientlist.get(i).getUserName()+" has been removed for inactivity");
                    Datacontainer.messagelist.add(new Message("Server",Datacontainer.clientlist.get(i).getUserName()+" has been removed for inactivity"));
                    Datacontainer.clientlist.remove(i);
                }
                }catch (IndexOutOfBoundsException e){}
            }
        }
    }
}

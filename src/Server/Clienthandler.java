package Server;

import javax.xml.crypto.Data;
import java.net.*;

import java.io.*;

public class Clienthandler {

    private Socket socket = null;

    private ServerSocket server = null;

    private DataInputStream in = null;

    private DataOutputStream out = null;


    public Clienthandler(int port) {

        try {

            server = new ServerSocket(port);

            System.out.println("Server started");

            Thread reciever = new Reciever();
            Thread sender = new Sender();

            reciever.start();
            sender.start();
            boolean x = true;
            while (x) {
                System.out.println("Waiting for a client ...");

                socket = server.accept();
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(socket.getOutputStream());

                String username;
                username = in.readUTF();
                try {
                    if (!validUsername(username)) {
                        System.out.println("invalid username");
                        out.writeUTF("OVER");
                        System.out.println("Sending OVER to Client");
                    } else {
                        out.writeUTF("J_OK");
                        System.out.println("J_OK Sent to Client");
                        Datacontainer.clientlist.add(new User(username, socket));
                        System.out.println("User: " + username + ":" + socket.getPort());
                        System.out.println("Client.Client accepted");
                    }
                } catch (NullPointerException e) {
                    System.out.println("Cant do J_OK");
                }

                //System.out.println(socket);
                //System.out.println(username);

                //System.out.println("User: "+username+":"+ socket.getPort());
                //System.out.println("Client.Client accepted");

                System.out.println("Number of clients: " + Datacontainer.clientlist.size());


            }


            System.out.println("Closing connection");

            socket.close();

            in.close();

        } catch (IOException i) {

            System.out.println(i);

        }

    }

    private boolean validUsername(String message) {
        for (int i = 0; i < Datacontainer.clientlist.size(); i++) {
            if (message.equals(Datacontainer.clientlist.get(i).getUserName())) {
                return false;
            }
        }
        return true;
    }
}

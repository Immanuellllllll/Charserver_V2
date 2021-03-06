package Server;

import javax.xml.crypto.Data;
import java.net.*;

import java.io.*;
import java.sql.Time;
import java.time.LocalDateTime;

public class Clienthandler {

    private Socket socket = null;

    private ServerSocket server = null;

    private DataInputStream in = null;

    private DataOutputStream out = null;


    public Clienthandler(int port) {

        try {

            server = new ServerSocket(port);


            System.out.println("Server started ");
            Reciever reciever = new Reciever();
            Thread sender = new Sender();

            reciever.start();
            sender.start();

            boolean x = true;
            while (x) {
                System.out.println("Waiting for a client ...");

                socket = server.accept();
                System.out.println(socket.getPort());

                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    String username;
                    username = in.readUTF();
                    try {
                        if (!validUsername(username)) {
                            System.out.println("Sending ERROR to Client!");
                        } else {
                            out.writeUTF("J_OK");
                            System.out.println("J_OK Sent to Client");
                            Datacontainer.clientlist.add(new User(username, socket, System.currentTimeMillis()));
                            System.out.println("JOIN " + username + ", " + socket.getInetAddress() + ":" + socket.getPort());
                            System.out.println("Number of clients: " + Datacontainer.clientlist.size());
                            Datacontainer.messagelist.add(new Message("List", getUserList()));
                            break;
                        }
                    } catch (NullPointerException e) {
                        unknownERROR();
                        System.out.println("Cant do J_OK");
                    }
                }
            }


            closeSocket();

        } catch (IOException i) {
            System.out.println(i);
        }
    }

    private String getUserList() {
        String userlist = "";
        for (int i = 0; Datacontainer.clientlist.size() > i; i++) {
            userlist += Datacontainer.clientlist.get(i).getUserName() + " ";
        }
        return userlist;
    }

    private boolean validUsername(String message) throws IOException {
        for (int i = 0; i < Datacontainer.clientlist.size(); i++) {
            if (message.equals(Datacontainer.clientlist.get(i).getUserName())) {
                duplicateERROR();
                return false;
            }
            // TODO: 08-12-2019 Add conditions for unkown command, bad command and other errors
        }
        return true;
    }

    private void duplicateERROR() throws IOException {
        out.writeUTF("J_ER Error-02:" + " Duplicate Username! Try Again!");
    }

    private void unknownERROR() throws IOException {
        out.writeUTF("J_ER Error03:" + " Unknown Command!");
    }

    private void userList() throws IOException {
        System.out.println(Datacontainer.clientlist);
        out.writeUTF("Here is a list of all current users: " + Datacontainer.clientlist);
    }

    private void closeSocket() throws IOException {
        socket.close();
        in.close();
        System.out.println("Closing connection");
        out.writeUTF("Connection Closed By Server!");
    }

    private void clientQUIT() throws IOException {
        System.out.println("Client is Quitting!");
        out.writeUTF("You have been removed from the Chat Server!");
        userList();
    }

}

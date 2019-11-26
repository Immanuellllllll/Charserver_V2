package Server;

import java.net.*;

import java.io.*;

public class Clienthandler

{

    private Socket socket = null;

    private ServerSocket server = null;

    private DataInputStream in = null;

    public Clienthandler(int port)

    {

        try

        {

            server = new ServerSocket(port);

            System.out.println("Server started");

            Thread reciever = new Reciever();
            Thread sender = new Sender();

            reciever.start();
            sender.start();
            boolean x=true;
            while (x) {
                System.out.println("Waiting for a client ...");

                socket = server.accept();

                System.out.println(socket.toString());
                System.out.println("Client.Client accepted");

                Datacontainer.clientlist.add(socket);

                System.out.println("Number of clients: "+Datacontainer.clientlist.size());

            }


            System.out.println("Closing connection");

            socket.close();

            in.close();

        }

        catch(IOException i)

        {

            System.out.println(i);

        }

    }
}

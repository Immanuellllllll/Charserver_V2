package Client;

import java.net.*;

import java.io.*;

public class Client

{

    private Socket socket = null;

    private DataInputStream input = null;

    private DataInputStream in = null;

    private DataOutputStream out = null;

// constructor to put ip address and port

    public Client(String address, int port)

    {

        try

        {

            socket = new Socket(address, port);

            System.out.println("Connected");

            input = new DataInputStream(System.in);

            out = new DataOutputStream(socket.getOutputStream());

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            Thread clientreciever = new ClientReciever();

            clientreciever.start();

        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        String line = "";

        while (!line.equals("Over"))

        {

            try

            {

                line = input.readLine();

                out.writeUTF(line);


            }

            catch(IOException i)

            {

                System.out.println(i);

            }

        }

        try

        {

            input.close();

            out.close();

            socket.close();

        }

        catch(IOException i)

        {

            System.out.println(i);

        }

    }



    private class ClientReciever extends Thread {

        public void run() {
            while (true){
                try {
                    System.out.println(in.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package Client;

import java.net.*;

import java.io.*;
import java.util.Scanner;

public class Client {

    private Socket socket = null;

    private DataInputStream input = null;

    private DataInputStream in = null;

    private DataOutputStream out = null;

    Scanner scanner = new Scanner(System.in);

// constructor to put ip address and port

    public Client(String address, int port) throws IOException {

        try {

            System.out.println("Please Enter Username: (Cannot Contain: @!£#¤$%€&/()[]{})");

            socket = new Socket(address, port);

            //System.out.println("Connected");

            input = new DataInputStream(System.in);

            out = new DataOutputStream(socket.getOutputStream());

            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            Thread clientreciever = new ClientReciever();

            out.writeUTF(JOIN());
            clientreciever.start();

            if (in.readUTF().equals("J_OK")) {
                System.out.println("Connected to Server");
            }

        } catch (IOException i) {
            System.out.println(i);
        } catch (Exception e) {
            System.out.println(e);
        }

        String line = "";

        while (!line.equals("QUIT")) {

            try {
                line = input.readLine();
                out.writeUTF(line);
            } catch (IOException i) {
                System.out.println(i);
            }

            if (in.readUTF().equals("OVER")) {

                try {

                    input.close();

                    out.close();

                    socket.close();

                } catch (IOException i) {

                    System.out.println(i);

                }
            }
        }
        System.out.println("Connection Has Been Closed");

        try {

            input.close();

            out.close();

            socket.close();

        } catch (IOException i) {

            System.out.println(i);

        }

    }

    private String JOIN() {
        String username = scanner.next();
        InetAddress server_ip = socket.getInetAddress();
        int port = socket.getPort();
        return username + server_ip + port;
    }


    private class ClientReciever extends Thread {

        public void run() {
            while (true) {
                try {
                    System.out.println(in.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package Client;

import java.net.*;

import java.io.*;
import java.util.Scanner;

class Client {

    private Socket socket = null;
    private DataInputStream in = null;
    private Scanner scanner = new Scanner(System.in);
    private DataOutputStream out = null;
    private DataInputStream input = null;
    private String userName;

// constructor to put ip address and port

    Client(String address, int port) throws IOException {

        try {
            System.out.println("Please Enter Username: (Cannot Contain: @!£#¤$%€&/()[]{})");
            socket = new Socket(address, port);
            //System.out.println("Connected");
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(System.in);

            Thread clientreciever = new ClientReciever();
            Thread clientsender = new ClientSender();

            out.writeUTF(JOIN());

            clientreciever.start();
            //clientsender.start();

            if (in.readUTF().equals("J_OK")) {
                System.out.println("You have Connected to the Server");
            }

        } catch (Exception e) {
            System.out.println(e);
        }


        String line = "";
        while (!line.equals("QUIT")) {
            System.out.println("hehehe");

            try {
                line = input.readLine();
                if (out != null) {
                    out.writeUTF(line);
                    System.out.println("HEYE"+line);
                    //sendData();
                    //receiveData();
                }

            } catch (IOException i) {
                System.out.println(i);
            }

            if (in != null && in.readUTF().equals("OVER")) {

                try {
                    closeSocket();
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
        }
        try {
            closeSocket();
            System.out.println("Connection Has Been Closed");
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    private void sendData() throws IOException {
        Scanner senderInput = new Scanner(System.in);
        out.writeUTF(senderInput.next());
    }

    private void receiveData() throws IOException {
        System.out.println(in.readUTF());
    }

    private void closeSocket() throws IOException {
        socket.close();
        out.close();
        scanner.close();
    }

    private String JOIN() {
        String username = scanner.next();
        InetAddress server_ip = socket.getInetAddress();
        int port = socket.getPort();
        return username + server_ip + port;
    }

    private void QUIT() throws IOException {
        out.writeUTF("QUIT" + socket.getInetAddress());
        System.out.println("You are leaving the Chat Server!");
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
    private class ClientSender extends Thread{

        public void run() {
            while (true){
                try {
                    out.writeUTF(String.valueOf(input));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

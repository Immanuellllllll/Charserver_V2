package Client;

import java.net.*;

import java.io.*;
import java.sql.Time;
import java.text.Format;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.Scanner;

class Client {

    private Socket socket = null;
    private DataInputStream in = null;
    private Scanner scanner = new Scanner(System.in);
    private DataOutputStream out = null;
    private DataInputStream input = null;

// constructor to put ip address and port

    Client(String address, int port) throws IOException {

        try {
            System.out.println("Please Enter Username: (Cannot Contain: @!£#¤$%€&/()[]{})");
            socket = new Socket(address, port);
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(System.in);

            Thread clientreciever = new ClientReciever();
            Thread heartbeat = new Heartbeat();

            out.writeUTF(JOIN());


            clientreciever.start();
            heartbeat.start();

            if (in.readUTF().equals("J_OK")) {
                System.out.println("You have Connected to the Server");
            }

        } catch (Exception e) {
            System.out.println(e);
        }



        String line = "";
        while (!line.equals("QUIT")) {

            try {
                line = input.readLine();
                if (out != null || !(line.length()>250)) {
                    out.writeUTF(line);

                }
                else{
                    System.out.println("Message not sent");
                }

            } catch (IOException i) {
                System.out.println(i);
            }
        }
        try {
            QUIT();
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
        String username="";
        while (true){
            username = scanner.next();
            if (username.matches("[A-Za-z0-9_]+")|| username.length()<12){
            break;
            }
            System.out.println("Invalid username");
        }
        return username;
    }

    private void QUIT() throws IOException {
        closeSocket();
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

    private class Heartbeat extends Thread {
        String message = "IMAV";

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(6000);
                    out.writeUTF(message);
                    //System.out.println(message);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

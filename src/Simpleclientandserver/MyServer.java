package Simpleclientandserver;

import java.io.*;
import java.net.*;
public class MyServer {
    public static void main(String[] args){
        try{
            ServerSocket ss=new ServerSocket(8000);
            Socket s=ss.accept();//Lytter og accepterer klient. socket indholder klientens addresse port mm.
            DataInputStream in=new DataInputStream(s.getInputStream()); //Er ansvarlig for at læse
            DataOutputStream out=new DataOutputStream(s.getOutputStream());//Er ansvarlig for at skrive.
            String msg=in.readUTF(); //Venter på input og læser det.
            System.out.println("Modtog besked: "+msg);
            out.writeUTF("Tak for din besked. Her er den igen: "+msg); //Sender en besked tilbage.
            ss.close();
        }catch(Exception e){System.out.println(e);}
    }
}

package Simpleclientandserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) {
        try{
            Socket s=new Socket("localhost",8000);
            DataOutputStream out=new DataOutputStream(s.getOutputStream());
            DataInputStream in=new DataInputStream(s.getInputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));//læser system input.
            out.writeUTF(br.readLine());
            String msg = in.readUTF();
            System.out.println("Serveren svarede: " + msg);
            out.flush();
            out.close();
            s.close();
        }catch(Exception e){System.out.println(e);}
    }
}
package aFileTransfer;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8000);
        Socket s = server.accept();
        DataInputStream input = new DataInputStream(s.getInputStream());
        String path = input.readUTF();
        FileOutputStream writer = new FileOutputStream(path);
        byte[] b = new byte[2000];
        input.read(b,0,b.length);
        writer.write(b,0,b.length);
    }
}

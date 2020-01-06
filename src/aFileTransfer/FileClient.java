package aFileTransfer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 8000);
        DataOutputStream out=new DataOutputStream(s.getOutputStream());

        String filename = "file.txt";
        String dpath="C:\\Users\\Immanuel\\Desktop\\Charserver_V1\\src\\aFileTransfer\\Serverfiles\\"+filename;
        String spath="C:\\Users\\Immanuel\\Desktop\\Charserver_V1\\src\\aFileTransfer\\Clientfiles\\"+filename;

        out.writeUTF(dpath);
        FileInputStream fr = new FileInputStream(spath);
        byte[] b = new byte[2000];
        fr.read(b,0,b.length);
        out.write(b,0,b.length);
    }
}

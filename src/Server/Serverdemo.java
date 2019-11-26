package Server;

import java.io.IOException;

public class Serverdemo {

    public static void main(String args[]) throws IOException {

        Clienthandler clienthandler=new Clienthandler(5000);
    }
}

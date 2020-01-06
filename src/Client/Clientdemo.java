package Client;

import java.io.IOException;
import java.time.LocalDateTime;

public class Clientdemo {
    public static void main(String args[]) throws IOException {
        Client client = new Client("localhost", 5000);
    }
}


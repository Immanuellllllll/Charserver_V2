package Client;

import java.io.DataOutputStream;
import java.io.IOException;

public class Heartbeat extends Thread {
    private DataOutputStream out = null;
    String message = "IMAV";

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(6000);
                out.writeUTF(message);
                System.out.println(message);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}

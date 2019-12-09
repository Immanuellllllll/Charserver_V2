package Server;

import java.net.Socket;
import java.sql.Time;
import java.time.LocalDateTime;

public class User {
    private String userName;
    private Socket socket;
    private Long lastHeartbeat;

    public User(String userName, Socket socket, Long lastHeartbeat) {
        this.userName = userName;
        this.socket = socket;
        this.lastHeartbeat=lastHeartbeat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Long getLastHeartbeat() {
        return lastHeartbeat;
    }

    public void setLastHeartbeat(Long lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }
}

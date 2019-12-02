package Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Datacontainer {
    public volatile static List<User> clientlist=new ArrayList<User>();

    public volatile static Queue<Message> messagelist= new LinkedList<Message>();
}



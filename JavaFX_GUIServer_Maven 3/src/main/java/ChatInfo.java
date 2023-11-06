import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.scene.control.ListView;

import java.io.*;
import java.net.Socket;
import java.util.List;

//import Server.ClientThread;

public class ChatInfo implements Serializable {
    String message;
    ArrayList<String> ListOfAllClients;
    ArrayList<Integer> ListOfSomeClients;

    public ChatInfo() {
        message = "";
        ListOfAllClients = new ArrayList<String>();
        ListOfSomeClients = new ArrayList<Integer>();
    }

    public void Message(String message) {
        this.message = message;
    }

    public void ListOfALLClientsUpdate(String clientNum) {
        this.ListOfAllClients.add(clientNum);
    }

    public void ListOfSomeClientsUpdate(int clientNum) {
        this.ListOfSomeClients.add(clientNum);
    }
}

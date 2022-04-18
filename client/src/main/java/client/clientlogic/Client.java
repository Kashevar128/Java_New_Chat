package client.clientlogic;

import network.ClientProfile;
import network.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;


public class Client {

    private int PORT;
    private String IP_ADDR;
    private Socket socket;
    private ObjectInputStream inObj;
    private ObjectOutputStream outObj;
    private ClientProfile clientProfile;
    private boolean authentication;

    public boolean isAuthentication() {
        return authentication;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public Client (ClientProfile clientProfile) {
        this.socket = new Socket("localhost", 8189);
        this.inObj = new ObjectInputStream(socket.getInputStream());
        this.outObj = new ObjectOutputStream(socket.getOutputStream());
        this.clientProfile = clientProfile;
        setAuthentication(false);
        Thread t = new Thread(() -> {

        });

    }

    private void readMessage() throws IOException, ClassNotFoundException {
        while (true) {
            Message<String> message = (Message<String>) inObj.readObject();

        }
    }



}

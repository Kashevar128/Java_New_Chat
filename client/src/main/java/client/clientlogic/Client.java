package client.clientlogic;

import network.ClientProfile;
import network.Message;
import network.TypeMessage;

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

    public void setClientProfile(ClientProfile clientProfile) {
        this.clientProfile = clientProfile;
    }

    public ObjectOutputStream getOutObj() {
        return outObj;
    }

    public Client(ClientProfile clientProfile) throws IOException {
        this.socket = new Socket("localhost", 8189);
        this.inObj = new ObjectInputStream(socket.getInputStream());
        this.outObj = new ObjectOutputStream(socket.getOutputStream());
        this.clientProfile = clientProfile;
        setAuthentication(false);
        Thread t = new Thread(() -> {
            try {
                readMessage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private void readMessage() throws IOException, ClassNotFoundException {
        while (true) {
            authDone();
            Message<String> message = (Message<String>) inObj.readObject();
            System.out.println(message.getObj());
        }
    }

    private boolean authDone() throws IOException, ClassNotFoundException {
        Message msg = new Message<String>(null, clientProfile, TypeMessage.SERVICE_MESSAGE_AUTHORIZATION);
        sendMsg(msg);
        while (true) {
            Message<String> message = (Message<String>) inObj.readObject();
            if (message.getTypeMessage().equals(TypeMessage.SERVICE_MESSAGE_AUTHORIZATION) &&
                    message.getObj().equals("/authok")) {
                setClientProfile(message.getClientProfile());
                setAuthentication(true);
                return true;
            }
        }
    }

    public void sendMsg(Message<String> message) {
        if (message.getTypeMessage().equals(TypeMessage.VERBAL_MESSAGE)) {
            try {
                outObj.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(message.getObj());
        }
    }
}

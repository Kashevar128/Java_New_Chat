package server;

import network.ClientProfile;
import network.Message;
import network.TypeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientHandler {
    private MyServer server;
    private Socket socket;
    private ObjectOutputStream outObj;
    private ObjectInputStream inObj;
    private Thread readThread;
    private Thread writeThread;
    private boolean authClient;

    private ClientProfile clientProfile;

    public void setClientProfile(ClientProfile clientProfile) {
        this.clientProfile = clientProfile;
    }

    public void setAuthClient(boolean authClient) {
        this.authClient = authClient;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public ClientHandler(MyServer server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.outObj = new ObjectOutputStream(socket.getOutputStream());
            this.inObj = new ObjectInputStream(socket.getInputStream());
            this.clientProfile = null;
            readThread = new Thread(() -> {
                try {
                    //  authentication();
                    setAuthClient(true);
                    readMessage();
                } catch (IOException | ClassNotFoundException /*| SQLException*/ e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
            });
            writeThread = new Thread(() -> {
                writeMessage();
            });
            readThread.start();
            writeThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    private void authentication() throws IOException, ClassNotFoundException, SQLException {
        while (true) {
            Message message = (Message) inObj.readObject();
            setClientProfile(message.getClientProfile());
            if (message.getTypeMessage().equals(TypeMessage.SERVICE_MESSAGE_AUTHORIZATION)) {
                if (server.getAuthService().auth(clientProfile)) {
                    server.subscribe(this, false);
                    break;
                }
            }
            if (message.getTypeMessage().equals(TypeMessage.SERVICE_MESSAGE_REGISTRATION)) {
                if (server.getAuthService().reg(clientProfile)) {
                    server.subscribe(this, true);
                    break;
                }
            }
        }
        clientProfile.setAvatar(server.getAuthService().getBaseAvatar(clientProfile.getName()));
        Message<String> msg = new Message<>("/authok", clientProfile, TypeMessage.SERVICE_MESSAGE_AUTHORIZATION);
        sendMsg(msg);
    }

    private void readMessage() throws IOException, ClassNotFoundException {
        while (true) {
            Message<String> message = (Message<String>) inObj.readObject();
            System.out.println(message.getObj());
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

    private void writeMessage() {
        Scanner in = new Scanner(System.in);
        String message = null;
        while (in.hasNext()) {
            message = in.next();
            Message<String> msg = new Message<>(message, null, TypeMessage.VERBAL_MESSAGE);
            sendMsg(msg);
        }
    }

    private void disconnect() {
        readThread.interrupt();
        writeThread.interrupt();
        try {
            inObj.close();
            outObj.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

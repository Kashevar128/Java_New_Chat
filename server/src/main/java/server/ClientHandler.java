package server;

import network.ClientProfile;
import network.Message;
import network.TypeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler {
    private MyServer server;
    private Socket socket;
    private ObjectInputStream inObj;
    private ObjectOutputStream outObj;
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
            this.inObj = new ObjectInputStream(socket.getInputStream());
            this.outObj = new ObjectOutputStream(socket.getOutputStream());
            this.clientProfile = null;
            readThread = new Thread(() -> {
                try {
                    authentication();
                    setAuthClient(true);
                    readMessage();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    disconnect();
                }
            });
            writeThread = new Thread(() -> {
                writeMessage();
            });
            readThread.start();
            if(authClient) writeThread.start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    private void authentication() throws IOException, ClassNotFoundException {
        while (true) {
            Message message = (Message) inObj.readObject();
            setClientProfile(message.getClientProfile());
            if (message.getTypeMessage().equals(TypeMessage.SERVICE_MESSAGE_AUTHORIZATION)) {
                if (server.getAuthService().auth(clientProfile)) {
                    server.subscribe(this, false);
                }
            }
            if (message.getTypeMessage().equals(TypeMessage.SERVICE_MESSAGE_REGISTRATION)) {
                if (server.getAuthService().reg(clientProfile)) {
                    server.subscribe(this, true);
                }
            }
        }
    }

    private void readMessage() throws IOException, ClassNotFoundException {
        while (true) {
            Message<String> message = (Message<String>) inObj.readObject();
            System.out.println(clientProfile.getName() + ": " + message.getObj());
        }
    }

    public void sendMsg(Message<String> message) {
        if (message.getTypeMessage().equals(TypeMessage.VERBAL_MESSAGE)) {
            try {
                outObj.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(server.getAdminProfile().getName() + ": " + message.getObj());
        }
    }

    private Message<String> writeMessage() {
        Scanner in = new Scanner(System.in);
        while (true) {
            allOrOne(createAdminMessage(in));
        }
    }

    private Message<String> createAdminMessage(Scanner scanner) {
        String message = null;
        message = scanner.next();
        System.out.println(server.getAdminProfile().getName() + ": " + message);
        Message<String> messageAdmin = new Message<>(message, server.getAdminProfile(), TypeMessage.VERBAL_MESSAGE);
        return messageAdmin;
    }

    private void allOrOne(Message<String> message) {
        String msg = message.getObj();
        char slash = '/';
        if (msg.charAt(0) == slash) {
            sendMsg(message);
        } else {
            server.broadcastMsg(message);
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

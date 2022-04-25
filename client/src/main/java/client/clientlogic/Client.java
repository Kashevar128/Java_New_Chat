package client.clientlogic;

import network.Message;
import network.TypeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    private String IP_ADDR;
    private final int PORT = 8189;
    private final String HOST = "localhost";
    private Socket socket;
    private ObjectInputStream inObj;
    private ObjectOutputStream outObj;
    Thread readThread;
    Thread writeThread;

    {
        try {
            IP_ADDR = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Client() {
        try {
            socket = new Socket(HOST, PORT);
            outObj = new ObjectOutputStream(socket.getOutputStream());
            inObj = new ObjectInputStream(socket.getInputStream());
            System.out.println("Клиент запущен");
            readThread = new Thread(() -> {
                while (true) {
                    try {
                        readMsg();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            writeThread = new Thread(() -> {
                while (true) {
                    writeMsg();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        readThread.start();
        writeThread.start();
    }

    private void readMsg() throws IOException, ClassNotFoundException {
        while (true) {
            Message<String> message = (Message<String>) inObj.readObject();
            System.out.println(message.getObj());
        }
    }

    private void writeMsg() {
        Scanner in = new Scanner(System.in);
        String message = null;
        while (in.hasNext()) {
            message = in.next();
            Message<String> msg = new Message<>(message, null, TypeMessage.VERBAL_MESSAGE);
            sendMsg(msg);
        }
    }

    private void sendMsg(Message msg) {
        try {
            outObj.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(msg.getObj());
    }

}

package network;

import common.ClientProfile;
import messageDTO.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class TCPConnection {

    private final Socket socket;
    private Thread readThread;
    private final TCPConnectionListener eventListener;
    private final ObjectOutputStream outObj;
    private final ObjectInputStream inObj;

    public TCPConnection(String ipAdd, int port, TCPConnectionListener eventListener) throws IOException {
        this(new Socket(ipAdd, port), eventListener);
    }

    public TCPConnection(Socket socket, TCPConnectionListener eventListener) throws IOException {
        this.socket = socket;
        outObj = new ObjectOutputStream(socket.getOutputStream());
        inObj = new ObjectInputStream(socket.getInputStream());
        this.eventListener = eventListener;
        readThread = new Thread(() -> {
            try {
                eventListener.onConnectionReady(TCPConnection.this);
                while (!readThread.isInterrupted()) {
                    Message msg = (Message) inObj.readObject();
                    eventListener.onReceivePackage(TCPConnection.this, msg);
                }
            } catch (IOException | ClassNotFoundException e) {
                eventListener.onException(TCPConnection.this, e);
            } finally {
                try {
                    eventListener.onDisconnect(TCPConnection.this);
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        readThread.start();
    }

    public Socket getSocket() {
        return socket;
    }


    public synchronized void sendMessage(Message msg) {
        try {
            outObj.writeObject(msg);
            outObj.flush();
            outObj.reset();
        } catch (IOException e) {
            throw new RuntimeException("Connection close");
        }
    }

    public synchronized void disconnect() {
        readThread.interrupt();
        try {
            inObj.close();
            outObj.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}

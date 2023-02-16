package server;


import common.ClientProfile;
import messageDTO.Message;
import messageDTO.TypeMessage;
import messageDTO.requests.AuthOrRegMessageRequest;
import messageDTO.respons.AuthOrRegMessageResponse;
import network.*;
import dataBase.DataBaseImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

import static common.Constants.AUTH;
import static common.Constants.REG;

public class MyServer extends JFrame implements TCPConnectionListener, ActionListener {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private final JTextArea textArea = new JTextArea();
    private final JTextField fieldNickname = new JTextField("Admin");
    private final JTextField fieldInput = new JTextField();
    private TCPConnection connection = null;
    private final DataBaseImpl dataBase = new DataBaseImpl();

    public MyServer() throws HeadlessException {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
  //      setAlwaysOnTop(true);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        fieldInput.addActionListener(this);

        add(textArea, BorderLayout.CENTER);
        add(fieldInput, BorderLayout.SOUTH);
        add(fieldNickname, BorderLayout.NORTH);
        setResizable(false);

        setVisible(true);

        printMsg("Server running...");
        printMsg("You have to wait connection");

        dataBase.start();

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                dataBase.stop();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                dataBase.stop();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                connection = new TCPConnection(serverSocket.accept(), this);
            }
        } catch (IOException e) {
            printMsg("Client kicked");
        }


    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        printMsg("Client " + tcpConnection + " connect");
    }

    @Override
    public void onReceivePackage(TCPConnection tcpConnection, Message msg) {
        messageClientHandler(msg, tcpConnection);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) throws SocketException {
        tcpConnection.disconnect();
        connections.remove(tcpConnection);
        printMsg("Client " + tcpConnection + " disconnect");
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println(tcpConnection);
        e.printStackTrace();
    }

    @Override
    public void onSendPackage(TCPConnection tcpConnection, Message msg) {
        tcpConnection.sendMessage(msg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        printMsg(fieldInput.getText());
        fieldInput.setText("");
    }

    private void sendAll(Message msg) {
        connections.forEach((tcpConnection -> onSendPackage(tcpConnection, msg)));
    }

    private synchronized void messageClientHandler(Message msg, TCPConnection tcpConnection) {
        TypeMessage typeMessage = msg.getTypeMessage();
        AuthOrRegMessageRequest authOrRegMessageRequest = (AuthOrRegMessageRequest) msg;
        switch (typeMessage) {
            case SERVICE_MESSAGE_AUTH_REG:
                if (authOrRegMessageRequest.getOperation() == AUTH) {
                    Function<ClientProfile, Boolean> dataBaseAuthFunction = dataBase::auth;
                    authOrReg(authOrRegMessageRequest, tcpConnection, dataBaseAuthFunction);
                }
                if (authOrRegMessageRequest.getOperation() == REG) {
                    Function<ClientProfile, Boolean> dataBaseRegFunction = dataBase::reg;
                    authOrReg(authOrRegMessageRequest, tcpConnection, dataBaseRegFunction);
                }
        }
    }

    private synchronized void printMsg(String msg) {
        consoleCommand(msg);
        SwingUtilities.invokeLater(() -> {
            textArea.append(msg + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }

    private void consoleCommand(String msg) {
        if (msg.equals("exit")) closeServer();
    }

    private void closeServer() {
        if (connection != null) {
            connection.disconnect();
        }
        this.dispose();
        System.exit(0);
    }

    private void authOrReg(AuthOrRegMessageRequest authOrRegMessageRequest, TCPConnection tcpConnection,
                           Function<ClientProfile, Boolean> dataBaseFunction) {
        ClientProfile clientProfile = authOrRegMessageRequest.getClientProfile();
        boolean authOrReg = dataBaseFunction.apply(clientProfile);
        Message message;
        if (authOrReg) {
            String nameUser = authOrRegMessageRequest.getClientProfile().getName();
            printMsg(nameUser + " authentication complete!");
            byte[] baseAvatar = dataBase.getBaseAvatar(nameUser);
            clientProfile.setAvatar(baseAvatar);
            message = new AuthOrRegMessageResponse(true, clientProfile);
            onSendPackage(tcpConnection, message);
            return;
        }
        message = new AuthOrRegMessageResponse(false);
        onSendPackage(tcpConnection, message);
        printMsg("authentication false!");
    }


}

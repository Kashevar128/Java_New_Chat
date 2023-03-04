package server;


import common.ClientProfile;
import controllers.ServerConsoleController;
import messageDTO.Message;
import messageDTO.TypeMessage;
import messageDTO.requests.AuthOrRegMessageRequest;
import messageDTO.requests.VerbalMessageRequest;
import messageDTO.respons.AuthOrRegMessageResponse;
import messageDTO.respons.UpdateUsersResponse;
import messageDTO.respons.VerbalMessageResponse;
import network.*;
import dataBase.DataBaseImpl;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static common.Constants.AUTH;
import static common.Constants.REG;

public class MyServer implements TCPConnectionListener {
    private final Map<TCPConnection, ClientProfile> connections = new HashMap<>();
    private final List<VerbalMessageResponse> historyMessages = new ArrayList<>();
    private final File imageAdmin = new File("server/src/main/resources/img/544_oooo.plus.png");
    private ClientProfile serverProfile = null;
    private TCPConnection connection = null;
    private final DataBaseImpl dataBase = new DataBaseImpl();
    private ServerConsoleController serverConsoleController;
    private Thread thread;

    public MyServer() throws HeadlessException, IOException {

        dataBase.start();

        byte[] bytesImage = Files.readAllBytes(imageAdmin.toPath());
        serverProfile = new ClientProfile("Admin", null, bytesImage);

        thread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(8189)) {
                while (true) {
                    connection = new TCPConnection(serverSocket.accept(), this);
                }
            } catch (IOException e) {
                serverConsoleController.printMsg("Client kicked");
            }
        });

        thread.start();


    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        serverConsoleController.printMsg("Client " + tcpConnection + " connect");
    }

    @Override
    public void onReceivePackage(TCPConnection tcpConnection, Message msg) {
        messageClientHandler(msg, tcpConnection);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) throws SocketException {
        tcpConnection.disconnect();
        connections.remove(tcpConnection);
        updateListUsers(tcpConnection);
        serverConsoleController.printMsg("Client " + tcpConnection + " disconnect");
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

    public void actionPerformed(String entry) {
        consoleCommand(entry);
        if (entry.startsWith("$")) return;
        VerbalMessageResponse verbalMessageResponse = new VerbalMessageResponse(entry, serverProfile);
        historyMessages.add(verbalMessageResponse);
        sendAll(verbalMessageResponse, null);
    }

    private void sendAll(Message msg, TCPConnection tcpConnection) {
        connections.keySet().stream()
                .filter(tcpConnection1 -> !tcpConnection1.equals(tcpConnection))
                .forEach(tcp -> onSendPackage(tcp, msg));
    }

    private synchronized void messageClientHandler(Message msg, TCPConnection tcpConnection) {
        TypeMessage typeMessage = msg.getTypeMessage();
        switch (typeMessage) {
            case SERVICE_MESSAGE_AUTH_REG:
                assert msg instanceof AuthOrRegMessageRequest;
                AuthOrRegMessageRequest authOrRegMessageRequest = (AuthOrRegMessageRequest) msg;
                if (authOrRegMessageRequest.getOperation() == AUTH) {
                    Function<ClientProfile, Boolean> dataBaseAuthFunction = dataBase::auth;
                    authOrReg(authOrRegMessageRequest, tcpConnection, dataBaseAuthFunction);
                    break;
                }
                if (authOrRegMessageRequest.getOperation() == REG) {
                    Function<ClientProfile, Boolean> dataBaseRegFunction = dataBase::reg;
                    authOrReg(authOrRegMessageRequest, tcpConnection, dataBaseRegFunction);
                    break;
                }
                break;

            case VERBAL_MESSAGE:
                assert msg instanceof VerbalMessageRequest;
                VerbalMessageRequest verbalMessageRequest = (VerbalMessageRequest) msg;
                String msgStr = verbalMessageRequest.getMessage();
                String name = verbalMessageRequest.getClientProfile().getName();
                serverConsoleController.printMsg(name + ": " + msgStr);
                VerbalMessageResponse verbalMessageResponse = new VerbalMessageResponse(msgStr,
                        verbalMessageRequest.getClientProfile());
                historyMessages.add(verbalMessageResponse);
                sendAll(verbalMessageResponse, tcpConnection);
        }
    }

    private void consoleCommand(String msg) {
        if (msg.equals("$help")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Команды: \n");
            stringBuilder.append("$help - вызвать помощь \n");
            stringBuilder.append("$ban <name user> - разорвать связь с пользователем \n");
            stringBuilder.append("$exit - закрыть сервер \n");
            stringBuilder.append("$clear - очистить консоль сервера \n");
            stringBuilder.append("$getUsers - показать список пользователей онлайн \n");
            serverConsoleController.printMsg(stringBuilder.toString());
            return;
        }
        if (msg.indexOf("$ban") == 0) {
            String[] commandArray = msg.split(" ");
            if (commandArray[0].equals("$ban")) {
                ban(commandArray[1]);
            }
            return;
        }
        if (msg.equals("$exit")) {
            closeServer();
            return;
        }
        if (msg.equals("$clear")) {
            serverConsoleController.clearLog();
            return;
        }
        if (msg.equals("$getUsers")) {
            printAllUsers();
            return;
        }
        serverConsoleController.printMsg("Неизвестная команда");
    }

    private void closeConnect() {
        if (connection != null) {
            connection.disconnect();
        }
    }

    private void closeData() {
        dataBase.stop();
    }

    public void closeServer() {
        closeConnect();
        closeData();
        System.exit(0);
    }

    private void authOrReg(AuthOrRegMessageRequest authOrRegMessageRequest, TCPConnection tcpConnection,
                           Function<ClientProfile, Boolean> dataBaseFunction) {
        ClientProfile clientProfile = authOrRegMessageRequest.getClientProfile();
        boolean authOrReg = dataBaseFunction.apply(clientProfile);
        Message message;
        if (authOrReg) {
            String nameUser = authOrRegMessageRequest.getClientProfile().getName();
            serverConsoleController.printMsg(nameUser + " authentication complete!");
            byte[] baseAvatar = dataBase.getBaseAvatar(nameUser);
            clientProfile.setAvatar(baseAvatar);
            connections.put(tcpConnection, clientProfile);
            message = new AuthOrRegMessageResponse(true, clientProfile, createUserList(), historyMessages);
            onSendPackage(tcpConnection, message);
            updateListUsers(tcpConnection);
            return;
        }
        message = new AuthOrRegMessageResponse(false);
        onSendPackage(tcpConnection, message);
        serverConsoleController.printMsg("authentication false!");
    }

    private void updateListUsers(TCPConnection tcpConnection) {
        Message msg = new UpdateUsersResponse(createUserList());
        sendAll(msg, tcpConnection);
    }

    private void printAllUsers() {
        serverConsoleController.printMsg(getAllUsers().toString());
    }

    private List<String> getAllUsers() {
        return createUserList().stream().map(ClientProfile::getName).toList();
    }

    private void ban(String name) {
        List<String> listNames = getAllUsers();
        if (listNames.contains(name)) {
            connections.forEach((key, value) -> {
                if (value.getName().equals(name)) {
                    key.disconnect();
                    connections.remove(key);
                }
            });
        }
    }

    private List<ClientProfile> createUserList() {
        return new ArrayList<>(connections.values());
    }

    public void setServerConsoleController(ServerConsoleController serverConsoleController) {
        this.serverConsoleController = serverConsoleController;
    }
}

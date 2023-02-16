package client;

import common.ClientProfile;
import common.Operations;
import controllers.ClientController;
import guiWindows.AlertWindowsClass;
import guiWindows.ClientGui;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import messageDTO.Message;
import messageDTO.TypeMessage;
import messageDTO.respons.AuthOrRegMessageResponse;
import messageDTO.respons.UpdateUsersResponse;
import network.TCPConnection;
import network.TCPConnectionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class Client implements TCPConnectionListener {

    private static String IP_ADDR;
    private static final int PORT = 8189;
    private final String HOST = "localhost";
    private Socket socket;
    private TCPConnection connection;
    private Stage authStage;
    private Stage regStage;
    private ClientController clientController;
    private ClientProfile clientProfile;

    static {
        try {
            IP_ADDR = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    public Client() {
        try {
            connection = new TCPConnection(IP_ADDR, PORT, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAuthStage(Stage authStage) {
        this.authStage = authStage;
    }

    public void setRegStage(Stage regStage) {
        this.regStage = regStage;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connecting to the server " + tcpConnection + " complete");
    }

    @Override
    public void onReceivePackage(TCPConnection tcpConnection, Message msg) {
        messageServerHandler(msg, tcpConnection);
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) throws SocketException {
        tcpConnection.disconnect();
        System.out.println("Connecting to the server interrupt");
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

    private void messageServerHandler(Message msg, TCPConnection tcpConnection) {
        TypeMessage typeMessage = msg.getTypeMessage();
        switch (typeMessage){
            case SERVICE_MESSAGE_AUTH_REG:
                AuthOrRegMessageResponse authOrRegMessageResponse = (AuthOrRegMessageResponse) msg;
                if (authOrRegMessageResponse.isRegOK()) {
                    clientProfile = authOrRegMessageResponse.getClientProfile();
                    Platform.runLater(() -> {
                        try {
                            if (regStage != null) regStage.close();
                            if (authStage != null) authStage.close();
                            AlertWindowsClass.showAuthComplete();
                            new ClientGui(this);
                            Image image = Operations.byteArrayDecodeToImage(clientProfile.getAvatar());
                            clientController.setNameLabel(clientProfile.getName());
                            clientController.setUserAva(image);
                            clientController.updateUsers(authOrRegMessageResponse.getClientProfiles());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    return;
                }
                Platform.runLater(AlertWindowsClass::showAuthFalse);

            case SERVICE_MESSAGE_UPDATE_USERS:
                UpdateUsersResponse updateUsersResponse = (UpdateUsersResponse) msg;
                clientController.updateUsers(updateUsersResponse.getProfilesUsers());
        }
    }

    public void sendMsg(Message msg) {
        onSendPackage(connection, msg);
    }

    public void closeConnect() {
        connection.disconnect();
    }
}

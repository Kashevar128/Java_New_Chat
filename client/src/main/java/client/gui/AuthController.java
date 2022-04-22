package client.gui;

import client.clientlogic.Client;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import network.ClientProfile;
import network.Message;
import network.Operations;
import network.TypeMessage;

import java.io.IOException;

public class AuthController {

    private static AuthController authController;

    public TextField login;
    public TextField password;
    public TextField IP_server;
    public TextField PORT_server;
    public CheckBox test;
    private Client client;


    public AuthController() {
        authController = this;
    }

    public static AuthController getAuthController() {
        return authController;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void enter() {
        boolean auth = false;
        String loginStr = login.getText();
        String passwordStr = password.getText();
        if (Operations.filterStringEmpty(loginStr, passwordStr)) {
            login.clear();
            password.clear();
            return;
        }
        try {
            auth = authClient(loginStr, passwordStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (auth) {
            Platform.runLater(()->{
                try {
                    new ClientGui();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public void reg() {
        login.getScene().getWindow().hide();
        try {
            new RegGui();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean authClient(String loginStr, String passwordStr) throws IOException {
        ClientProfile clientProfile = new ClientProfile(loginStr, passwordStr, null);
        setClient(new Client(clientProfile, "auth"));
        return true;
    }
}

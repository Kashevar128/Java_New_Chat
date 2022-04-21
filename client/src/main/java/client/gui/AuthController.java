package client.gui;

import client.clientlogic.Client;
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

    public AuthController() {
        authController = this;
    }

    public static AuthController getAuthController() {
        return authController;
    }


    public void enter() {
        boolean auth = false;
        String loginStr = login.getText();
        String passwordStr = password.getText();
        if (Operations.filterStringEmpty(loginStr, passwordStr)) return;
        try {
            auth = authClient(loginStr, passwordStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (auth) {
            //TODO: здесь будет создаваться новый clientgui
        }

    }

    public void reg() {
        //TODO: здесь создается новый reggui
    }

    private boolean authClient(String loginStr, String passwordStr) throws IOException {
        ClientProfile clientProfile = new ClientProfile(loginStr, passwordStr, null);
        Client client = new Client(clientProfile);
        return true;
    }
}

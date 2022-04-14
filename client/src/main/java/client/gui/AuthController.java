package client.gui;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import network.ClientProfile;
import network.Message;
import network.Operations;

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
        String loginStr = login.getText();
        String passwordStr = password.getText();

        if(Operations.filterStringEmpty(loginStr, passwordStr)) return;

        boolean auth =

    }

    public void reg() {

    }

    private void authClient(String loginStr, String passwordStr) {
        ClientProfile clientProfile = new ClientProfile();
        Message message = new Message<>(null, );
    }
}

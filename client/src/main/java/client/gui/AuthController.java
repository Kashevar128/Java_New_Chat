package client.gui;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import network.Operations;

import java.io.IOException;

public class AuthController {

    private static AuthController authController;

    public TextField login;
    public TextField password;
    public TextField IP_server;
    public TextField PORT_server;
    public CheckBox test;


    private AuthController() {
        authController = this;
    }

    public static AuthController getAuthController() {
        return authController;
    }

    public void enter() {
        String loginStr = login.getText();
        String passwordStr = password.getText();
        if (Operations.filterStringEmpty(loginStr, passwordStr)) {
            login.clear();
            password.clear();
            return;
        }

        Platform.runLater(() -> {
            try {
                new ClientGui();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void reg() {
        login.getScene().getWindow().hide();
        try {
            new RegGui();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


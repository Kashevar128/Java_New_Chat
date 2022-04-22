package client.gui;

import client.clientlogic.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import network.ClientProfile;
import network.Operations;

import java.io.IOException;

public class RegController {
    @FXML
    public TextField login;
    public TextField password;

    public void enter() {
        boolean reg = false;
        String loginStr = login.getText();
        String passwordStr = password.getText();
        if (Operations.filterStringEmpty(loginStr, passwordStr)) {
            login.clear();
            password.clear();
            return;
        }

        try {
            reg = regClient(loginStr, passwordStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (reg) {
            Platform.runLater(()-> {
                try {
                    new ClientGui();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public void back() {
        login.getScene().getWindow().hide();
        try {
            new RegGui();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean regClient(String loginStr, String passwordStr) throws IOException {
        ClientProfile clientProfile = new ClientProfile(loginStr, passwordStr, null);
        new Client(clientProfile, "reg");
        return true;
    }
}

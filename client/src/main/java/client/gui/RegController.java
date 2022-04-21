package client.gui;

import client.clientlogic.Client;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import network.ClientProfile;
import network.Operations;

import java.io.IOException;

public class RegController {

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
            
        }

    }

    public void back() {

    }

    private boolean regClient(String loginStr, String passwordStr) throws IOException {
        ClientProfile clientProfile = new ClientProfile(loginStr, passwordStr, null);
        new Client(clientProfile, "reg");
        return true;
    }
}

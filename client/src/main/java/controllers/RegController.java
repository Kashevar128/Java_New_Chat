package controllers;

import client.Client;
import common.ClientProfile;
import common.Operations;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import messageDTO.Message;
import messageDTO.requests.RegistrationMessageRequest;

public class RegController {

    private Stage regStage;

    public TextField login;

    public TextField password;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public void enter(ActionEvent actionEvent) {
        if (login.getText().isEmpty() || password.getText().isEmpty()) return;
        String strLogin = Operations.delSpace(login.getText());
        String strPass = Operations.delSpace(password.getText());
        ClientProfile clientProfile = new ClientProfile(strLogin, strPass);
        Message message = new RegistrationMessageRequest(clientProfile);
        System.out.println(strLogin + ", " + strPass);
        client.sendMsg(message);
    }

    public void back(ActionEvent actionEvent) {
    }

    public void setRegStage(Stage regStage) {
        this.regStage = regStage;
    }
}

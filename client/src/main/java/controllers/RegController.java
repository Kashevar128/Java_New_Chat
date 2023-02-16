package controllers;

import client.Client;
import common.ClientProfile;
import common.Constants;
import common.Operations;
import guiWindows.AlertWindowsClass;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import messageDTO.Message;
import messageDTO.requests.AuthOrRegMessageRequest;

import java.util.function.Function;

import static common.Constants.REG;

public class RegController {

    private Stage regStage;

    public TextField login;

    public TextField password;

    private Client client;

    private final Function<ClientProfile, Message> sendMsgFun = (clientProfile) ->
            new AuthOrRegMessageRequest(clientProfile, REG);

    public void setClient(Client client) {
        this.client = client;
    }

    public void enter(ActionEvent actionEvent) {
        if (!filter(login, password)) return;
        authOrReg(login, password, client, sendMsgFun);
        clearAll(login, password);
    }

    public void back(ActionEvent actionEvent) {
    }

    public void setRegStage(Stage regStage) {
        this.regStage = regStage;
    }

    static void authOrReg(TextField login, TextField password, Client client, Function<ClientProfile, Message> sendMsgFun) {
        if (login.getText().isEmpty() || password.getText().isEmpty()) return;
        String strLogin = Operations.delSpace(login.getText());
        String strPass = Operations.delSpace(password.getText());
        ClientProfile clientProfile = new ClientProfile(strLogin, strPass);
        Message message = sendMsgFun.apply(clientProfile);
        System.out.println(strLogin + ", " + strPass);
        client.sendMsg(message);
    }

    static boolean filter(TextField login, TextField password) {
        if (login.getText().isEmpty() || password.getText().isEmpty()) {
            Platform.runLater(AlertWindowsClass::showDataIncorrect);
            return false;
        }
        return true;
    }

    static void clearAll(TextField login, TextField password) {
        login.clear();
        password.clear();
    }
}

package controllers;

import client.Client;
import common.ClientProfile;
import guiWindows.RegGui;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import messageDTO.Message;
import messageDTO.requests.AuthOrRegMessageRequest;
import java.io.IOException;
import java.util.function.Function;
import static common.Constants.AUTH;
import static controllers.RegController.*;

public class AuthController {

    private Client client;
    private Stage stage;
    private final Function<ClientProfile, Message> sendMsgFun = (clientProfile) ->
            new AuthOrRegMessageRequest(clientProfile, AUTH);

    public void setClient(Client client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextField login;
    public TextField password;

    public void enter(ActionEvent actionEvent) {
        if (!filter(login, password)) return;
        authOrReg(login, password, client, sendMsgFun);
        clearAll(login, password);
    }

    public void reg(ActionEvent actionEvent) {
        try {
            stage.close();
            new RegGui(client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

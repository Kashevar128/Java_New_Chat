package controllers;

import client.Client;
import guiWindows.RegGui;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {

    private Client client;
    private Stage stage;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextField login;
    public TextField password;

    public void enter(ActionEvent actionEvent) {
    }

    public void reg(ActionEvent actionEvent) {
        try {
            stage.close();
            RegGui regGui = new RegGui(client);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

package client.gui;

import client.clientlogic.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import network.Message;
import network.TypeMessage;

public class ClientController {

    private Client client;

    public ClientController(Client client) {
        this.client = client;
    }

    @FXML
    public TextField input;
    public ListView listUsers;
    public ListView listDialog;

    public void send() {
        BoubbleOutGoing boubble = new BoubbleOutGoing(input.getText());
        HBoxChat hBoxChat = new HBoxChat(boubble);
        Message<BoubbleOutGoing> message = new Message<>(boubble, null, TypeMessage.VERBAL_MESSAGE);
        client.sendMsg(message);
    }
}

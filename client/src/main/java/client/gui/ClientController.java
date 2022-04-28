package client.gui;

import client.clientlogic.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import network.Message;
import network.TypeMessage;

public class ClientController {

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public TextField input;
    public ListView listUsers;
    public ListView <HBoxChat> listDialog;

    public void send() {
        Label bubbleOut = new BubbleOutGoing(input.getText());
        HBoxChat hBoxChat = new HBoxChat(bubbleOut);
        Message<String> message = new Message<>(input.getText(), null, TypeMessage.VERBAL_MESSAGE);
        //client.sendMsg(message);
        Platform.runLater(() -> {
            listDialog.getItems().add(hBoxChat);
        });
    }
}

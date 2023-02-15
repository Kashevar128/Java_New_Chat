package controllers;

import client.Client;
import guiElements.BubbleOutGoing;
import guiElements.HBoxChat;
import guiElements.HBoxUsers;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ClientController {

    public Label nameLabel;
    public ImageView userAva;
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    public TextField input;
    public ListView <HBoxUsers> listUsers;
    public ListView <HBoxChat> listDialog;

    public void send() {
        System.out.println(input.getText());
        Label bubbleOut = new BubbleOutGoing(input.getText());
        HBoxChat hBoxChat = new HBoxChat(bubbleOut);
  //      Message<String> message = new Message<>(input.getText(), null, TypeMessage.VERBAL_MESSAGE);
   //     client.sendMsg(message);
        Platform.runLater(() -> {
            listDialog.getItems().add(hBoxChat);
        });
    }

    public void setNameLabel(String name) {
        Platform.runLater(() -> nameLabel.setText(name));
    }

    public void setUserAva(Image ava) {
        Platform.runLater(() -> userAva.setImage(ava));
    }

    public void addUserToList(HBoxUsers hBoxUsers) {
        listUsers.getItems().add(hBoxUsers);
    }
}

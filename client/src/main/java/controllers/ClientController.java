package controllers;

import client.Client;
import common.ClientProfile;
import guiElements.BubbleOutGoing;
import guiElements.HBoxChat;
import guiElements.HBoxUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import messageDTO.Message;
import messageDTO.requests.VerbalMessageRequest;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ClientController {

    @FXML
    public Label nameLabel;
    public ImageView userAva;
    public TextField input;
    public ListView<HBoxUser> listUsers;
    public ListView<HBoxChat> listDialog;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public void send() {
        System.out.println(input.getText());
        Label bubbleOut = new BubbleOutGoing(input.getText());
        HBoxChat hBoxChat = new HBoxChat(bubbleOut);
        Message message = new VerbalMessageRequest(input.getText(), client.getClientProfile());
        client.sendMsg(message);
        Platform.runLater(() -> {
            listDialog.getItems().add(hBoxChat);
        });
    }

    public void updateUsers(List<ClientProfile> listProfiles) {
        Function<ClientProfile, HBoxUser> createHBoxUserFunction =
                (clientProfile) -> new HBoxUser((clientProfile.getName()),
                        clientProfile.getAvatar());
        List<HBoxUser> hBoxUsers = listProfiles.stream()
                .filter(clientProfile -> !clientProfile.getName().equals(client.getClientProfile().getName()))
                .map(createHBoxUserFunction)
                .collect(Collectors.toList());
        Platform.runLater(() -> {
            listUsers.getItems().clear();
            listUsers.getItems().addAll(hBoxUsers);
        });
    }

    public void setNameLabel(String name) {
        Platform.runLater(() -> nameLabel.setText(name));
    }

    public void setUserAva(Image ava) {
        Platform.runLater(() -> userAva.setImage(ava));
    }

    public void addUserToList(HBoxUser hBoxUser) {
        listUsers.getItems().add(hBoxUser);
    }
}

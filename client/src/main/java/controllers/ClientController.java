package controllers;

import client.Client;
import common.ClientProfile;
import guiElements.BubbleInGoing;
import guiElements.BubbleOutGoing;
import guiElements.HBoxChat;
import guiElements.HBoxUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import messageDTO.Message;
import messageDTO.requests.VerbalMessageRequest;
import messageDTO.respons.VerbalMessageResponse;

import java.util.List;
import java.util.function.Function;


public class ClientController {

    @FXML
    public Label nameLabel;
    public ImageView userAva;
    public TextField input;
    public ListView<HBoxUser> listUsers;
    public ListView<HBoxChat> listDialog;
    public ImageView sendImage;
    public Button exitButton;
    public ImageView exitImage;
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNameLabel(String name) {
        Platform.runLater(() -> nameLabel.setText(name));
    }

    public void setUserAva(Image ava) {
        Platform.runLater(() -> userAva.setImage(ava));
    }

    public void send() {
        Label bubbleOut = new BubbleOutGoing(input.getText());
        HBoxChat hBoxChat = new HBoxChat(bubbleOut, client.getClientProfile().getAvatar());
        Message message = new VerbalMessageRequest(input.getText(), client.getClientProfile());
        input.clear();
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
                .map(createHBoxUserFunction).toList();
        Platform.runLater(() -> {
            listUsers.getItems().clear();
            listUsers.getItems().addAll(hBoxUsers);
        });
    }

    public void updateListDialog(List<VerbalMessageResponse> verbalMessageResponseList) {
        List<HBoxChat> hBoxChats = verbalMessageResponseList.stream().map(verbalMessageResponse -> {
            String message = verbalMessageResponse.getMessage();
            ClientProfile clientProfile = verbalMessageResponse.getClientProfile();
            Label label;
            if (clientProfile.getName().equals(client.getClientProfile().getName())) {
                label = new BubbleOutGoing(message);
            } else {
                label = new BubbleInGoing(message);
            }
            return new HBoxChat(label, clientProfile.getAvatar());
        }).toList();
        Platform.runLater(() -> listDialog.getItems().addAll(hBoxChats));
    }

    public void addToListDialog(String msg, byte[] image) {
        BubbleInGoing bubbleInGoing = new BubbleInGoing(msg);
        HBoxChat hBoxChat = new HBoxChat(bubbleInGoing, image);
        Platform.runLater(() -> listDialog.getItems().add(hBoxChat));
    }

    public void exitClient() {
        client.setEmergencyExit(false);
        client.closeConnect();
        client.getClientStage().close();
    }

    public void addUserToList(HBoxUser hBoxUser) {
        listUsers.getItems().add(hBoxUser);
    }
}

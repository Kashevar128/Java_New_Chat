package client.gui;

import client.clientlogic.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.ClientProfile;

import java.io.IOException;

public class ClientGui {
    private Stage stage;

    public ClientGui() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chatWork.fxml"));
        Parent chat = loader.load();
        stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(false);
        stage.show();

        Client client = new Client();
        ClientController clientController = new ClientController();
        clientController.setClient(client);
    }


}

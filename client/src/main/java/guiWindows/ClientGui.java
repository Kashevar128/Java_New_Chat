package guiWindows;

import client.Client;
import controllers.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientGui {
    private Stage stage;
    private Client client;

    public ClientGui(Client client) throws IOException {
        this.client = client;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chatWork.fxml"));
        Parent chat = loader.load();
        stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            client.setEmergencyExit(false);
            client.closeConnect();
        });

        stage.show();

        ClientController clientController = loader.getController();
        clientController.setClient(client);
        client.setClientController(clientController);
        client.setClientStage(stage);
    }

    public void setClient(Client client) {
        this.client = client;
    }


}

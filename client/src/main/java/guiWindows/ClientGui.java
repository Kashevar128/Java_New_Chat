package guiWindows;

import client.Client;
import controllers.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientGui {
    private Stage stage;
    private Client client;
    private Image imagePlain = new Image("/img/paper_plane_48px.png");
    private Image imageExitDoor = new Image("/img/exit_icon_143020.png");

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
        clientController.input.setOnAction(actionEvent -> {
            clientController.send();
        });
        clientController.input.setFont(new Font("Arial", 20));
        clientController.listDialog.setSelectionModel(null);
        clientController.listUsers.setSelectionModel(null);
        clientController.sendImage.setImage(imagePlain);
        clientController.exitImage.setImage(imageExitDoor);


    }

    public void setClient(Client client) {
        this.client = client;
    }


}

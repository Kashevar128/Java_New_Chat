package guiWindows;

import client.Client;
import controllers.RegController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class RegGui {
    private Stage stage;
    private RegController regController;
    private Client client;

    public RegGui(Client client) throws IOException {
        this.client = client;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
        Parent reg = loader.load();
        regController = loader.getController();

        stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(reg));
        stage.setResizable(false);
        regController.setRegStage(stage);
        regController.setClient(client);
        client.setRegStage(stage);

        stage.setOnCloseRequest(windowEvent -> {
            client.closeConnect();
        });

        stage.show();
    }
}

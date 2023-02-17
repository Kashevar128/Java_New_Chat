package guiWindows;

import client.Client;
import controllers.AuthController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthGui {
    private Stage stage;
    private AuthController authController;
    private Client client;

    public AuthGui() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auth.fxml"));
        Parent auth = loader.load();
        stage = new Stage();
        stage.setTitle("Авторизация");
        stage.setScene(new Scene(auth));
        stage.setResizable(false);

        authController = loader.getController();
        client = new Client();
        authController.setClient(client);
        authController.setStage(stage);
        client.setAuthStage(stage);

        stage.setOnCloseRequest(windowEvent -> {
            client.setEmergencyExit(false);
            client.closeConnect();
        });

        stage.show();

    }
}

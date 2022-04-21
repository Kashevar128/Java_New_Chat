package client.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthGui {
    private Stage stage;

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auth.fxml"));
    Parent auth = loader.load();

    public AuthGui() throws IOException {
        stage = new Stage();
        stage.setTitle("Авторизация");
        stage.setScene(new Scene(auth));
        stage.setResizable(false);
        stage.show();
    }
}

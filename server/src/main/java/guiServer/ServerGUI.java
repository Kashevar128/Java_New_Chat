package guiServer;

import controllers.ServerConsoleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.MyServer;

import java.io.IOException;

public class ServerGUI {
    private Stage stage;
    private ServerConsoleController serverConsoleController;
    private MyServer myServer;

    public ServerGUI() {
        try {
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/serverConsole.fxml"));
            Parent root = loader.load();
            serverConsoleController = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Java Server Console");
            stage.setResizable(false);
            myServer = new MyServer();
            myServer.setServerConsoleController(serverConsoleController);
            serverConsoleController.setMyServer(myServer);
            stage.setOnCloseRequest(windowEvent -> {
                myServer.closeServer();
            });

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Stage getStage() {
        return stage;
    }
}

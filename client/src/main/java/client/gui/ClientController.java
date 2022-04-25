package client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClientController {
    private ClientController clientController;

    @FXML
    public TextField input;
    public ListView listUsers;
    public ListView listDialog;
    private



    public void send() {
        new Boubble();
    }
}

package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import server.MyServer;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerConsoleController implements Initializable {
    @FXML
    public TextArea outConsole;
    public TextField inConsole;

    private MyServer myServer;

    public void printMsg(String msg) {
        if (msg.equals("$clear") || msg.equals("$help")) return;
        Platform.runLater(()-> {
            outConsole.appendText(msg + "\n");
        });
    }

    public void clearLog() {
        outConsole.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        outConsole.setEditable(false);
        inConsole.setOnAction(actionEvent -> {
            if (inConsole.getText().isBlank() || inConsole.getText().isEmpty()) return;
            printMsg(inConsole.getText());
            myServer.actionPerformed(inConsole.getText());
            inConsole.clear();
        });
        printMsg("Server running...");
        printMsg("You have to wait connection.");
        printMsg("To help, enter \"$help\" in the console.");
        printMsg("To start writing to everyone, just start writing.");
        printMsg("If you want to enter a command, start with '$'.");
        printMsg("_________________________________________________________");
    }

    public void setMyServer(MyServer myServer) {
        this.myServer = myServer;
    }
}

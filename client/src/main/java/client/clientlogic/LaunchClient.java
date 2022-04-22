package client.clientlogic;

import client.gui.AuthGui;
import javafx.application.Platform;

import java.io.IOException;

public class LaunchClient {

    public static void main(String[] args) {
        running();
    }

    public static void running() {
        Platform.startup(()-> {
            try {
                new AuthGui();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

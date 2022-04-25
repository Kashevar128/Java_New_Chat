package client.clientlogic;

import client.gui.ClientGui;
import javafx.application.Platform;

import java.io.IOException;

public class LaunchClient {

    public static void main(String[] args) {
        Platform.startup(()->{
            try {
                new ClientGui();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

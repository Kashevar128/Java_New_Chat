package client;

import guiWindows.AuthGui;
import javafx.application.Platform;

import java.io.IOException;

public class LaunchClient {

    public static void main(String[] args) {
        Platform.startup(()->{
            try {
                new AuthGui();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

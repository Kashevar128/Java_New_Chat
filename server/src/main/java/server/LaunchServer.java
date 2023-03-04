package server;

import guiServer.ServerGUI;
import javafx.application.Platform;

import java.io.*;
import java.nio.file.Files;

public class LaunchServer {

    public static void main(String[] args) throws IOException {
        Platform.startup(ServerGUI::new);
    }
}

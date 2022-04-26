package client.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import network.Operations;

public class BoubbleOutGoing extends HBox {
    private final String stylesInGoingLabel = "-fx-background-color: #D3EEDF;" +
            "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

    private Label label;
    private Image image = new Image("C:/Users/Vinogradov_M/IdeaProjects/Java_New_Chat/client/src/main/resources/img/544_oooo.plus.png");
    private ImageView imageView;

    BoubbleOutGoing(String msg) {
        this.imageView = Operations.imageConverter(image);
        this.label = new Label(msg);
        label.setStyle(stylesInGoingLabel);
        label.setFont(new Font("Arial", 16));
        label.setMaxWidth(450);
        label.setWrapText(true);
    }





}

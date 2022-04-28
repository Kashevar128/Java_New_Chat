package client.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import network.Operations;

public class HBoxChat extends HBox {

    private Label label;
    private Image image = new Image("/img/544_oooo.plus.png");

    public HBoxChat(Label label) {
        ImageView imageView = Operations.imageConverter(image);
        if(label instanceof BubbleOutGoing) {
            this.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().addAll(imageView, label);
        }
    }
}

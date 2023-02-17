package guiElements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import common.Operations;

public class HBoxChat extends HBox {

    private Label label;
    private byte[] imageByte;

    public HBoxChat(Label label, byte[] imageByte) {
        this.label = label;
        this.imageByte = imageByte;
        ImageView imageView = Operations.byteArrayDecodeToImageView(imageByte);
        if(label instanceof BubbleOutGoing) {
            this.setAlignment(Pos.CENTER_LEFT);
        }
        if(label instanceof  BubbleInGoing) {
            this.setAlignment(Pos.CENTER_RIGHT);
        }
        this.getChildren().addAll(imageView, label);
    }
}

package guiElements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import common.Operations;

public class HBoxUsers extends HBox {
    private Label label;
    private Image image;

    public HBoxUsers(Label label, Image image) {
        this.label = label;
        this.image = image;
        ImageView imageView = Operations.imageToImageViewConverter(image);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(imageView, label);
    }

    public HBoxUsers(Label label, byte[] imageByte) {
        this.label = label;
        this.image = Operations.byteArrayDecodeToImage(imageByte);
        ImageView imageView = Operations.imageToImageViewConverter(image);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(imageView, label);
    }
}
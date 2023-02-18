package guiElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import common.Operations;
import javafx.scene.text.Font;

public class HBoxUser extends HBox {

    private String name;
    private byte[] imageByte;

    public HBoxUser(String name, byte[] imageByte) {
        this.name = name;
        this.imageByte = imageByte;
        Label label = new Label(name);
        label.setFont(new Font("Arial", 16));
        label.setMaxWidth(200);
        label.setWrapText(true);
        Image image = Operations.byteArrayDecodeToImage(imageByte);
        ImageView imageView = Operations.imageToImageViewConverter(image);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(imageView, label);
        HBox.setMargin(imageView, new Insets(10, 10, 10, 10));
        HBox.setMargin(label, new Insets(20, 10, 20, 10));
    }
}

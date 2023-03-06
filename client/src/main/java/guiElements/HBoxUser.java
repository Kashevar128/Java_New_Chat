package guiElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import common.Operations;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import static common.Constants.USER_ONLINE;

public class HBoxUser extends HBox {

    private final String name;
    private final byte[] imageByte;
    private Label labelOnline;

    public HBoxUser(String name, byte[] imageByte, int userConstant) {
        this.name = name;
        this.imageByte = imageByte;
        Label label = new Label(name);
        label.setFont(new Font("Arial", 16));
        label.setMaxWidth(200);
        label.setWrapText(true);
        ImageView imageView = Operations.byteArrayDecodeToImageView(imageByte);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(imageView, label);
        if (userConstant == USER_ONLINE) {
            labelOnline = new Label("online");
            Paint paint = new Color(0.5843, 0.6039, 0.5216, 1.0);
            labelOnline.setTextFill(paint);
            this.getChildren().add(labelOnline);
        }
        HBox.setMargin(imageView, new Insets(10, 10, 10, 10));
        HBox.setMargin(label, new Insets(20, 10, 20, 10));
    }
}

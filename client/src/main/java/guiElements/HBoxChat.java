package guiElements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import common.Operations;

public class HBoxChat extends HBox {

    private Label label;
    private Image image = new Image("/img/544_oooo.plus.png");

    public HBoxChat(Label label) {
        System.out.println("Здесь текст лэйбла" + label.getText());
        ImageView imageView = Operations.imageToImageViewConverter(image);
        if(label instanceof BubbleOutGoing) {
            this.setAlignment(Pos.CENTER_LEFT);
            this.getChildren().addAll(imageView, label);
        }
    }
}

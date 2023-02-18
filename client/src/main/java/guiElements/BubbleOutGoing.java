package guiElements;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class BubbleOutGoing extends Label {
    private final String stylesOutGoingLabel = "-fx-background-color: #afdec4;" +
            "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

    public BubbleOutGoing(String msg) {
        this.setText(msg);
        this.setStyle(stylesOutGoingLabel);
        this.setFont(new Font("Arial", 16));
        this.setMaxWidth(350);
        this.setWrapText(true);
    }

}

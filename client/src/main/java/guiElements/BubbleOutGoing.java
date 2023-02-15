package guiElements;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class BubbleOutGoing extends Label {
    private final String stylesInGoingLabel = "-fx-background-color: #D3EEDF;" +
            "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

    public BubbleOutGoing(String msg) {
        this.setText(msg);
        this.setStyle(stylesInGoingLabel);
        this.setFont(new Font("Arial", 16));
        this.setMaxWidth(450);
        this.setWrapText(true);
    }

}

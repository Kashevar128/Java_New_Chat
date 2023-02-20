package guiElements;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class BubbleInGoing extends Label {
    private final String stylesInGoingLabel = "-fx-background-color: #97daf7;" +
            "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

    public BubbleInGoing(String msg) {
        this.setText(msg);
        this.setStyle(stylesInGoingLabel);
        this.setFont(new Font("Arial", 16));
        this.setMaxWidth(350);
        this.setWrapText(true);
    }

}

package guiElements;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.Date;

public class BubbleOutGoing extends Label {
    private final String stylesOutGoingLabel = "-fx-background-color: #afdec4;" +
            "-fx-background-radius: 25px;" + "-fx-background-insets: -10;";

    public BubbleOutGoing(String msg) {
        Date date = new Date();
        String msgText = String.format("%s \n [%s]", msg, date);
        this.setText(msgText);
        this.setStyle(stylesOutGoingLabel);
        this.setFont(new Font("Arial", 16));
        this.setMaxWidth(350);
        this.setWrapText(true);
    }

}

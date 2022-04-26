package network;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Operations {

    public static boolean filterStringEmpty (String msg1, String msg2){
        return msg1.equals("") || msg2.equals("");
    };

    public static ImageView imageConverter (Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        return imageView;
    }

}

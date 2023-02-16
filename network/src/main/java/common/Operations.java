package common;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import messageDTO.Message;
import messageDTO.requests.AuthOrRegMessageRequest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Operations {

    public static boolean filterStringEmpty (String msg1, String msg2){
        return msg1.equals("") || msg2.equals("");
    };

    public static ImageView imageToImageViewConverter(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        return imageView;
    }

    public static Image byteArrayDecodeToImage(byte[] bytes) {
        Image imageFX = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try {
            BufferedImage image = ImageIO.read(bais);
            imageFX = SwingFXUtils.toFXImage(image, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFX;
    }

    public static ImageView byteArrayDecodeToImageView(byte[] bytes){
        Image image = byteArrayDecodeToImage(bytes);
        return imageToImageViewConverter(image);
    }

    public static String delSpace(String str) {
        String newStr = str.trim();
        newStr = newStr.replaceAll(" ", "");
        return newStr;
    }


}

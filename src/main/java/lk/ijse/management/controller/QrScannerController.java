package lk.ijse.management.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class QrScannerController implements Initializable {

    @FXML
    private ImageView qrPick;

    public static Image image;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setImage();
    }

    private void setImage() {
        qrPick.setImage(image);
    }


}

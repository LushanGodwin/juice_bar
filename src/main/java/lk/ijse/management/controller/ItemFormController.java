package lk.ijse.management.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton btnBeverage;

    @FXML
    private JFXButton btnFood;

    @FXML
    void btnBeverageOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/beveragesForm.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);

    }

    @FXML
    void btnFoodOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/foodItemForm.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

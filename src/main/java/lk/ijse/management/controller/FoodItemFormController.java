package lk.ijse.management.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FoodItemFormController {
    @FXML
    private TableView<?> tblCustomer;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemPrice;

    @FXML
    private JFXTextField txtItemId;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemPrice;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtItemIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtItemNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtItemPriceOnAction(ActionEvent event) {

    }
}

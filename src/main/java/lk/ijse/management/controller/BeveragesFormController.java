package lk.ijse.management.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.management.dto.Item;
import lk.ijse.management.dto.Supplier;
import lk.ijse.management.model.BeveragesModel;
import lk.ijse.management.model.SupplierModel;
import lk.ijse.management.regexPattern.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BeveragesFormController implements Initializable {
    @FXML
    private JFXTextField txtJuiceId;

    @FXML
    private JFXTextField txtJuiceName;

    @FXML
    private JFXTextField txtJuicePrice;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TableView<Item> tblJuice;

    @FXML
    private TableColumn<?, ?> colJuiceId;

    @FXML
    private TableColumn<?, ?> colJuiceName;

    @FXML
    private TableColumn<?, ?> colJuicePrice;

    ObservableList<Item> observableList = FXCollections.observableArrayList();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String juiceID = txtJuiceId.getText();
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
        if (result.orElse(no) == yes){
            try {
                boolean isRemoved = BeveragesModel.remove(juiceID);
                if(isRemoved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Juice Deleted !").show();
                    txtJuiceId.setText("");
                    txtJuiceName.setText("");
                    txtJuicePrice.setText("");
                    observableList.clear();
                    getAll();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Juice Not Deleted !").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String juiceId = txtJuiceId.getText();
        String juiceName = txtJuiceName.getText();
        double juicePrice = Double.parseDouble(txtJuicePrice.getText());

        try {
            boolean isValid = checkValid();
            if(isValid) {
                boolean isSaved = BeveragesModel.add(new Item(juiceId,juiceName,juicePrice,"Juice"));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Juice Added !").show();
                    txtJuiceId.setText("");
                    txtJuiceName.setText("");
                    txtJuicePrice.setText("");
                    observableList.clear();
                    getAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Juice Not Added !").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
        }
    }

    private boolean checkValid() {
        String id = txtJuiceId.getText();
        boolean isValid = true;
        if(!RegexPatterns.getJuiceIdPattern().matcher(id).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Juice Id!").show();
            isValid = false;
        }return isValid;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String juiceId = txtJuiceId.getText();
        String juiceName = txtJuiceName.getText();
        double juicePrice = Double.parseDouble(txtJuicePrice.getText());

        try {
            boolean isUpdate = BeveragesModel.update(new Item(juiceId,juiceName,juicePrice,"Juice"));
            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Juice Updates !").show();
                txtJuiceId.setText("");
                txtJuiceName.setText("");
                txtJuicePrice.setText("");
                observableList.clear();
                getAll();
            }else {
                new Alert(Alert.AlertType.ERROR,"Juice not Update !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
        }
    }

    @FXML
    void txtJuiceIdOnAction(ActionEvent event) {
        String juiceId = txtJuiceId.getText();
        try {
            Item item = BeveragesModel.get(juiceId);
            if(item == null){
                new Alert(Alert.AlertType.ERROR,"Invalid Id !").show();
            }else {
                txtJuiceName.setText(item.getItem_name());
                txtJuicePrice.setText(String.valueOf(item.getItem_price()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
            //e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colJuiceId.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colJuiceName.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        colJuicePrice.setCellValueFactory(new PropertyValueFactory<>("item_price"));
    }

    private void getAll() {
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        try {
            List<Item> itemList = BeveragesModel.getAll();
            for(Item item : itemList) {
                observableList.add(new Item(item.getItem_id(),item.getItem_name(),item.getItem_price(),"Juice"));
            }tblJuice.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

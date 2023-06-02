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
import lk.ijse.management.model.FoodItemModel;
import lk.ijse.management.regexPattern.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FoodItemFormController implements Initializable {
    @FXML
    private TableView<Item> tblFood;

    @FXML
    private TableColumn<?, ?> colFoodId;

    @FXML
    private TableColumn<?, ?> colFoodName;

    @FXML
    private TableColumn<?, ?> colFoodPrice;

    @FXML
    private JFXTextField txtFoodId;

    @FXML
    private JFXTextField txtFoodName;

    @FXML
    private JFXTextField txtFoodPrice;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    ObservableList<Item> observableList = FXCollections.observableArrayList();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String foodID = txtFoodId.getText();
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
        if (result.orElse(no) == yes){
            try {
                boolean isRemoved = FoodItemModel.remove(foodID);
                if(isRemoved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Food Deleted !").show();
                    txtFoodId.setText("");
                    txtFoodName.setText("");
                    txtFoodPrice.setText("");
                    observableList.clear();
                    getAll();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Food Not Deleted !").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String foodId = txtFoodId.getText();
        String foodName = txtFoodName.getText();
        double foodPrice = Double.parseDouble(txtFoodPrice.getText());

        try {
            boolean isValid = checkValid();
            if(isValid) {
                boolean isSaved = FoodItemModel.add(new Item(foodId,foodName,foodPrice,"Food"));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Food Added !").show();
                    txtFoodId.setText("");
                    txtFoodName.setText("");
                    txtFoodPrice.setText("");
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
        String id = txtFoodId.getText();
        boolean isValid = true;
        if(!RegexPatterns.getFoodIdPattern().matcher(id).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Food Id!").show();
            isValid = false;
        }return isValid;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String foodId = txtFoodId.getText();
        String foodName = txtFoodName.getText();
        double foodPrice = Double.parseDouble(txtFoodPrice.getText());

        try {
            boolean isUpdate = FoodItemModel.update(new Item(foodId,foodName,foodPrice,"Food"));
            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Food Updates !").show();
                txtFoodId.setText("");
                txtFoodName.setText("");
                txtFoodPrice.setText("");
                observableList.clear();
                getAll();
            }else {
                new Alert(Alert.AlertType.ERROR,"Food not Update !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
        }
    }

    @FXML
    void txtItemIdOnAction(ActionEvent event) {
        String foodId = txtFoodId.getText();
        try {
            Item item = FoodItemModel.get(foodId);
            if(item == null){
                new Alert(Alert.AlertType.ERROR,"Invalid Id !").show();
            }else {
                txtFoodName.setText(item.getItem_name());
                txtFoodPrice.setText(String.valueOf(item.getItem_price()));
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
        colFoodId.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colFoodName.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        colFoodPrice.setCellValueFactory(new PropertyValueFactory<>("item_price"));
    }

    private void getAll() {
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        try {
            List<Item> itemList = FoodItemModel.getAll();
            for(Item item : itemList) {
                observableList.add(new Item(item.getItem_id(),item.getItem_name(),item.getItem_price(),"Food"));
            }tblFood.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

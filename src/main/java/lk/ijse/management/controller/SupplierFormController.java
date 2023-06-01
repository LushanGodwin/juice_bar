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
import javafx.scene.layout.AnchorPane;
import lk.ijse.management.dto.Supplier;
import lk.ijse.management.model.SupplierModel;
import lk.ijse.management.regexPattern.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    @FXML
    private AnchorPane pane2;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtSupplierAddress;

    @FXML
    private JFXTextField txtSupplierContact;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableColumn<?, ?> colSupAddress;

    @FXML
    private TableColumn<?, ?> colSupContact;

    ObservableList<Supplier> observableList = FXCollections.observableArrayList();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
        if (result.orElse(no) == yes){
            try {
                boolean isRemoved = SupplierModel.remove(supplierId);
                if(isRemoved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Deleted !").show();
                    txtSupplierId.setText("");
                    txtSupplierName.setText("");
                    txtSupplierAddress.setText("");
                    txtSupplierContact.setText("");
                    observableList.clear();
                    getAll();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Supplier Not Deleted !").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContact = txtSupplierContact.getText();

        try {
            boolean isValid = checkValid();
            if(isValid) {
                boolean isSaved = SupplierModel.add(new Supplier(supplierId, supplierName, supplierContact, supplierAddress));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added !").show();
                    txtSupplierId.setText("");
                    txtSupplierName.setText("");
                    txtSupplierAddress.setText("");
                    txtSupplierContact.setText("");
                    observableList.clear();
                    getAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Supplier Not Added !").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        String supplierName = txtSupplierName.getText();
        String supplierAddress = txtSupplierAddress.getText();
        String supplierContact = txtSupplierContact.getText();

        try {
            boolean isUpdate = SupplierModel.update(new Supplier(supplierId,supplierName,supplierAddress,supplierContact));
            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Updates !").show();
                txtSupplierId.setText("");
                txtSupplierName.setText("");
                txtSupplierAddress.setText("");
                txtSupplierContact.setText("");
                observableList.clear();
                getAll();
            }else {
                new Alert(Alert.AlertType.ERROR,"Supplier not Update !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
        }
    }

    private boolean checkValid() {
        String id = txtSupplierId.getText();
        String contact = txtSupplierContact.getText();
        boolean isValid = true;
        if (!RegexPatterns.getSupplierIdPattern().matcher(id).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Supplier Id!").show();
            isValid = false;
        } else if (!RegexPatterns.getMobilePattern().matcher(contact).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Supplier Contact!").show();
            isValid = false;
        } return isValid;
    }

    @FXML
    void txtSupplierIdOnAction(ActionEvent event) {
        String supplierId = txtSupplierId.getText();
        try {
            Supplier supplier = SupplierModel.get(supplierId);
            if(supplier == null){
                new Alert(Alert.AlertType.ERROR,"Invalid Id !").show();
            }else {
                txtSupplierName.setText(supplier.getSupplierName());
                txtSupplierAddress.setText(supplier.getSupplierAddress());
                txtSupplierContact.setText(supplier.getSupplierContact());
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
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("supplierContact"));
    }

    private void getAll() {
        ObservableList<Supplier> observableList = FXCollections.observableArrayList();
        try {
            List<Supplier> supplierList = SupplierModel.getAll();
            for(Supplier supplier : supplierList) {
                observableList.add(new Supplier(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getSupplierContact(),supplier.getSupplierAddress()));
            }tblSupplier.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

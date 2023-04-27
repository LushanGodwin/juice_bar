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
import lk.ijse.management.db.DBConnection;
import lk.ijse.management.dto.Customer;
import lk.ijse.management.model.CustomerModel;
import lk.ijse.management.regexPattern.RegexPatterns;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerContact;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TableView<Customer> tblCustomer;

    @FXML
    private TableColumn<Customer, String> colCustId;

    @FXML
    private TableColumn<Customer, String> colCustName;

    @FXML
    private TableColumn<Customer, String> colCustAddress;

    @FXML
    private TableColumn<Customer, String> colCustContact;

    @FXML
    private JFXButton btnReport;

    ObservableList<Customer> observableList = FXCollections.observableArrayList();

    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/customer_report.jrxml");
        JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
        JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasPrint,false);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
        if (result.orElse(no) == yes) {
            try {
                boolean isRemoved = CustomerModel.remove(customerId);
                if (isRemoved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted !").show();
                    txtCustomerId.setText("");
                    txtCustomerName.setText("");
                    txtCustomerAddress.setText("");
                    txtCustomerContact.setText("");
                    observableList.clear();
                    getAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Deleted !").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Sql Error !").show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        String customerName = txtCustomerName.getText();
        String customerAddress = txtCustomerAddress.getText();
        String customerContact = txtCustomerContact.getText();

        try {
            boolean isValid = checkValid();
            if (isValid) {
                boolean isSaved = CustomerModel.add(new Customer(customerId, customerName, customerAddress, customerContact));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Added !").show();
                    txtCustomerId.setText("");
                    txtCustomerName.setText("");
                    txtCustomerAddress.setText("");
                    txtCustomerContact.setText("");
                    observableList.clear();
                    getAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer Not Added !").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Sql Error !").show();
        }
    }

    private boolean checkValid() {
        String id = txtCustomerId.getText();
        String contact = txtCustomerContact.getText();
        boolean isValid = true;
        if (!RegexPatterns.getCustomerIdPattern().matcher(id).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer Id!").show();
            isValid = false;
        } else if (!RegexPatterns.getMobilePattern().matcher(contact).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Customer Contact!").show();
            isValid = false;
        } return isValid;
    }

    @FXML
    void txtCustomerIdOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        try {
            Customer customer = CustomerModel.get(customerId);
            if (customer == null) {
                new Alert(Alert.AlertType.ERROR, "Invalid Id !").show();
            } else {
                txtCustomerName.setText(customer.getCustomerName());
                txtCustomerAddress.setText(customer.getCustomerAddress());
                txtCustomerContact.setText(customer.getCustomerContact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Sql Error !").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String customerId = txtCustomerId.getText();
        String customerName = txtCustomerName.getText();
        String customerAddress = txtCustomerAddress.getText();
        String customerContact = txtCustomerContact.getText();

        boolean isUpdate = false;
        try {
            isUpdate = CustomerModel.update(new Customer(customerId, customerName, customerAddress, customerContact));

            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated !").show();
                txtCustomerId.setText("");
                txtCustomerName.setText("");
                txtCustomerAddress.setText("");
                txtCustomerContact.setText("");
                observableList.clear();
                getAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Updated !").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Sql Error !").show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        getAll();
    }

    private void setCellValueFactory() {
        colCustId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colCustName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colCustAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        colCustContact.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
    }

    private void getAll() {
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        try {
            List<Customer> customerList = CustomerModel.getAll();
            customerList.forEach(customer -> observableList.add(new Customer(customer.getCustomerId(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerContact())));
            tblCustomer.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



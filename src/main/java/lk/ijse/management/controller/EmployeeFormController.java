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
import lk.ijse.management.dto.Customer;
import lk.ijse.management.dto.Employee;
import lk.ijse.management.model.CustomerModel;
import lk.ijse.management.model.EmployeeModel;
import lk.ijse.management.regexPattern.RegexPatterns;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private AnchorPane pane1;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtEmployeeName;

    @FXML
    private JFXTextField txtEmployeeAddress;

    @FXML
    private JFXTextField txtEmployeeContact;
    @FXML
    private TableView<Employee> tblEmployee;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colEmployeeAddress;

    @FXML
    private TableColumn<?, ?> colEmployeeContact;

    ObservableList<Employee> observableList = FXCollections.observableArrayList();

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
        if (result.orElse(no) == yes) {
            try {
                boolean isRemoved = EmployeeModel.remove(employeeId);
                if (isRemoved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted !").show();
                    txtEmployeeId.setText("");
                    txtEmployeeName.setText("");
                    txtEmployeeAddress.setText("");
                    txtEmployeeContact.setText("");
                    observableList.clear();
                    getAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee Not Deleted !").show();
                }
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
            }
        }
    }
    @FXML
    void txtEmployeeIdOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        try {
            Employee employee = EmployeeModel.get(employeeId);
            if(employee == null){
                new Alert(Alert.AlertType.ERROR,"Invalid Id !").show();
            }else {

                txtEmployeeName.setText(employee.getEmployeeName());
                txtEmployeeAddress.setText(employee.getEmployeeAddress());
                txtEmployeeContact.setText(employee.getEmployeeContact());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        String employeeName = txtEmployeeName.getText();
        String employeeAddress = txtEmployeeAddress.getText();
        String employeeContact = txtEmployeeContact.getText();

        try {
            boolean isValid = checkValid();
            if(isValid) {
                boolean isSaved = EmployeeModel.add(new Employee(employeeId, employeeName, employeeAddress, employeeContact));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Added !").show();
                    txtEmployeeId.setText("");
                    txtEmployeeName.setText("");
                    txtEmployeeAddress.setText("");
                    txtEmployeeContact.setText("");
                    observableList.clear();
                    getAll();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee Not Added !").show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
            //e.printStackTrace();
        }
    }

    private boolean checkValid() {
        String id = txtEmployeeId.getText();
        String contact = txtEmployeeContact.getText();
        boolean isValid = true;
        if (!RegexPatterns.getEmpIdPattern().matcher(id).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Employee Id!").show();
            isValid = false;
        } else if (!RegexPatterns.getMobilePattern().matcher(contact).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Employee Contact!").show();
            isValid = false;
        } return isValid;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String employeeId = txtEmployeeId.getText();
        String employeeName = txtEmployeeName.getText();
        String employeeAddress = txtEmployeeAddress.getText();
        String employeeContact = txtEmployeeContact.getText();


        try {
            boolean isUpdate = EmployeeModel.update(new Employee(employeeId,employeeName,employeeAddress,employeeContact));

            if(isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated !").show();
                txtEmployeeId.setText("");
                txtEmployeeName.setText("");
                txtEmployeeAddress.setText("");
                txtEmployeeContact.setText("");
                observableList.clear();
                getAll();
            }else {
                new Alert(Alert.AlertType.ERROR,"Employee Not Updated !").show();
            }} catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Sql Error !").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAll();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        colEmployeeContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
    }

    private void getAll() {
        ObservableList<Employee> observableList = FXCollections.observableArrayList();
        try {
            List<Employee> employeeList = EmployeeModel.getAll();
            for (Employee employee : employeeList) {
                observableList.add(new Employee(employee.getEmployeeId(),employee.getEmployeeName(),employee.getEmployeeAddress(),employee.getEmployeeContact()));
            }tblEmployee.setItems(observableList);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package lk.ijse.management.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.management.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPageFormController {
    public Label loginId;
    public JFXButton btnLogin;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtUserName;



    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {

        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        boolean isValid= false;
        try {
            isValid = UserModel.checkUser(userName,password);
        } catch (SQLException e) {
        }
        if(isValid) {

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"));
            Scene scene = new Scene(anchorPane);
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
        }else{
            new Alert(Alert.AlertType.ERROR,"Incorrect User name or Password!").show();
        }
    }
    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnLogin.fire();

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }
}

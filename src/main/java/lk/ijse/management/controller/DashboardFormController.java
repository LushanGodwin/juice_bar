package lk.ijse.management.controller;

import com.gn.lab.CalendarPane;
import com.gn.lab.GNCalendarTile;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.management.model.CustomerModel;
import lk.ijse.management.model.OrderModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    public AnchorPane root;
    public AnchorPane pane;
    public Label lblDate;
    public Label lblTime;

    public CalendarPane calenderPane;
    public GNCalendarTile calender;

    @FXML
    private ResourceBundle resources;
    @FXML
    private JFXButton btnBack;
    @FXML
    private URL location;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblTotalCustomer;



    @FXML
    private BarChart<String, Integer> barGrapch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblDate.setText(String.valueOf(LocalDate.now()));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        event -> lblTime.setText(
                                new SimpleDateFormat("hh:mm").format(Calendar.getInstance().getTime()))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        setBarGraphValues();
        setTotalValues();
    }

    private void setTotalValues() {
        try {
            String totalOrders = OrderModel.getTotalOrders();
            lblTotalOrders.setText(totalOrders);
            String totalCustomers = CustomerModel.getTotalCustomers();
            lblTotalCustomer.setText(totalCustomers);
        }catch (SQLException e){

        }

    }

    private void setBarGraphValues() {
        XYChart.Series<String, Integer> series = new XYChart.Series();
        series.setName("No. of Customers");
        series.getData().add(new XYChart.Data("JAN", 10));
        series.getData().add(new XYChart.Data("FEB", 10));
        series.getData().add(new XYChart.Data("MAR", 20));
        series.getData().add(new XYChart.Data("APR", 45));
        series.getData().add(new XYChart.Data("MAY", 10));
        series.getData().add(new XYChart.Data("JUN", 20));
        series.getData().add(new XYChart.Data("JUL", 50));
        series.getData().add(new XYChart.Data("AUG", 10));
        series.getData().add(new XYChart.Data("SEP", 10));
        series.getData().add(new XYChart.Data("OCT", 320));
        series.getData().add(new XYChart.Data("NOV", 320));
        series.getData().add(new XYChart.Data("DEC", 210));

        barGrapch.getData().addAll(series);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
        btnBack.setDisable(false);
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/employeeForm.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
        btnBack.setDisable(false);
    }

    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/itemForm.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
        btnBack.setDisable(false);
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/orderForm.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
        btnBack.setDisable(false);
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/supplierForm.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
        btnBack.setDisable(false);

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/mainPageForm.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(anchorPane);
        btnBack.setDisable(true);
    }

}

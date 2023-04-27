package lk.ijse.management.controller;

import com.gn.lab.GNCalendarTile;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.management.model.CustomerModel;
import lk.ijse.management.model.OrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainPageFormController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private BarChart<String, Integer> barGrapch;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private GNCalendarTile calender;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblTotalCustomer;


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

}

package lk.ijse.management.controller;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.management.db.DBConnection;
import lk.ijse.management.dto.Cart;
import lk.ijse.management.dto.Item;
import lk.ijse.management.model.CustomerModel;
import lk.ijse.management.model.ItemModel;
import lk.ijse.management.model.OrderModel;
import lk.ijse.management.model.PlaceOrderModel;
import lk.ijse.management.model.tm.AddToCartTM;
import lk.ijse.management.qr.QrGenerator;
import lk.ijse.management.regexPattern.RegexPatterns;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrderFormController implements Initializable {
    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblCustomerName;
    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private Label txtItemName;

    @FXML
    private Label txtItemPrice;

    @FXML
    private Label txtQtyOnHand;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXButton btnAddToCart;
    @FXML
    private Label lblNetTotal;

    @FXML
    private TableView<AddToCartTM> tblItem;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colItemPrice;

    @FXML
    private TableColumn<?, ?> colItemQty;

    @FXML
    private TableColumn<?, ?> colItemTotal;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private JFXButton btnReport;

    private ObservableList<AddToCartTM> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrderDate();
        setOrderId();
        setCustomerId();
        setItemId();
        setCellValueFactory();
    }
    @FXML
    void btnQrOnAction(ActionEvent event) {
        if (!lblNetTotal.getText().isEmpty()) {
            QrGenerator qrGenerator = new QrGenerator();
            qrGenerator.setData("Rs."+(lblNetTotal.getText()));
            try {
                qrGenerator.getGenerator();
            } catch (IOException | WriterException e) {
                new Alert(Alert.AlertType.ERROR, String.valueOf(e)).show();
            }
            File file = new File(qrGenerator.getPath());
            Image image = new Image(file.toURI().toString());
            QrScannerController.image = image;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/qrScanner.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("QR Window");
                stage.centerOnScreen();
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Input Data First! ").show();
        }
    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws FileNotFoundException, JRException, SQLException {
        JasperDesign jasDesign = JRXmlLoader.load("src/main/resources/reports/order_report.jrxml");
        JasperReport jasReport = JasperCompileManager.compileReport(jasDesign);
        JasperPrint jasPrint = JasperFillManager.fillReport(jasReport, null, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasPrint,false);
    }


    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("item_name"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("item_price"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("item_qty"));
        colItemTotal.setCellValueFactory(new PropertyValueFactory<>("item_total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("item_action"));
    }

    private void setItemId() {
        List<String> ides = null;
        try {
            ides =ItemModel.getIdes();

            ObservableList<String> observableList = FXCollections.observableArrayList();
            for (String id :ides) {
                observableList.add(id);
            }cmbItemId.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        boolean isValid = checkValid();
        if (isValid) {

            String itemId = cmbItemId.getValue();
            String itemName = txtItemName.getText();
            Double itemPrice = Double.valueOf(txtItemPrice.getText());
            int itemQty = Integer.parseInt(txtQty.getText());
            double total = itemQty * itemPrice;

            Button btnRemove = new Button("Remove");
            btnRemove.setCursor(Cursor.HAND);
            setRemoveBtnOnAction(btnRemove); /*set button remove action*/

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblItem.getItems().size(); i++) {
                    if (colItemId.getCellData(i).equals(itemId)) {
                        itemQty += (int) colItemQty.getCellData(i);
                        total = itemQty * itemPrice;

                        obList.get(i).setItem_qty(itemQty);
                        obList.get(i).setItem_total(total);
                        calculateNetTotal();
                        tblItem.refresh();

                        return;
                    }

                }
            }


            AddToCartTM addToCartTM = new AddToCartTM(itemId, itemName, itemPrice, itemQty, total, btnRemove);
            obList.add(addToCartTM);
            tblItem.setItems(obList);
            calculateNetTotal();

            txtQty.setText("");

        }
    }

    private boolean checkValid() {
        String qty = txtQty.getText();
        boolean isValid = true;
        if (!RegexPatterns.getIntPattern().matcher(qty).matches()){
            new Alert(Alert.AlertType.ERROR,"Invalid Qty!").show();
            isValid = false;
        }return isValid;
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblItem.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                tblItem.refresh();
                calculateNetTotal();

                txtQty.setText("");
            }

        });
    }
    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

        String oId = lblOrderId.getText();
        String cusId = cmbCustomerId.getValue();
        String netTotal = lblNetTotal.getText();


        List<Cart> cartDTOList = new ArrayList<>();

        for (int i = 0; i < tblItem.getItems().size(); i++) {
            AddToCartTM tm = obList.get(i);

            Cart cartDTO = new Cart(tm.getItem_id(), tm.getItem_qty(), tm.getItem_price());
            cartDTOList.add(cartDTO);
        }

        try {
            boolean isPlaced = PlaceOrderModel.placeOrder(oId, cusId, cartDTOList,netTotal);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }


    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblItem.getItems().size(); i++) {
            double total  = (double) colItemTotal.getCellData(i);
            netTotal += total;
        }
       lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String customerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        String name = null;
        try {
            name = CustomerModel.searchById(customerId);
        lblCustomerName.setText(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerId() {
        List<String> ides = null;
        try {
            ides = CustomerModel.getIdes();

        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (String id :ides) {
            observableList.add(id);
        }cmbCustomerId.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setOrderId() {
        String orderId = null;
        try {
            orderId = OrderModel.nextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }
    @FXML
    void cmdItemOnAction(ActionEvent event) {
        String itemId = cmbItemId.getSelectionModel().getSelectedItem();

        try {
            Item item = ItemModel.searchById(itemId);
            txtItemName.setText(item.getItem_name());
            txtItemPrice.setText(String.valueOf(item.getItem_price()));
            txtQtyOnHand.setText(item.getItem_qty());

            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

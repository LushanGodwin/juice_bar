package lk.ijse.management.model;

import lk.ijse.management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderModel {
    public static String nextOrderId() throws SQLException {
        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()){
            return splitOrderId(resultSet.getString(1));
        }return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if(currentOrderId!=null){
            String[] string = currentOrderId.split("O0");
            int id = Integer.parseInt(string[1]);
            id++;
            return "O0"+id;
        }return "O001";
    }

    public static boolean save(String oId, String cusId, LocalDate now, String netTotal) throws SQLException {
        String sql = "INSERT INTO orders(order_id, order_date,order_payment,cust_id) VALUES (?, ?, ?,?)";

        return CrudUtil.execute(sql,oId,now,netTotal,cusId);
    }

    public static String getTotalOrders() throws SQLException {
        String sql = "SELECT COUNT(order_id) FROM orders";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return resultSet.getString(1);
        }return "00";
    }
}

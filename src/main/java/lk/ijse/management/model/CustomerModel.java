package lk.ijse.management.model;

import com.jfoenix.controls.JFXTextField;
import lk.ijse.management.dto.Customer;
import lk.ijse.management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static boolean add(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer(cust_id,cust_name,cust_address,cust_contact) VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,customer.getCustomerId(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerContact());
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET cust_name = ?,cust_address = ?,cust_contact = ? WHERE cust_id =?";
        return CrudUtil.execute(sql,customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerContact(),customer.getCustomerId());

    }

    public static boolean remove(String customerId) throws SQLException {
        String sql = "DELETE FROM customer WHERE cust_id = ?";
        return CrudUtil.execute(sql,customerId);
    }

    public static Customer get(String txtCustomerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE cust_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,txtCustomerId);
        if(resultSet.next()){
            return new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
        } return null;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Customer> data = new ArrayList<>();
        while (resultSet.next()){
            data.add(new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
        }return data;
    }

    public static List<String> getIdes() throws SQLException {
        String sql = "SELECT cust_id FROM customer";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<String> id = new ArrayList<>();
        while (resultSet.next()){
            id.add(resultSet.getString(1));
        }return  id;
    }

    public static String searchById(String customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE cust_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,customerId);
        if(resultSet.next()){
            return resultSet.getString(2);
        }return  null;
    }

    public static String getTotalCustomers() throws SQLException {
        String sql = "SELECT COUNT(cust_id) FROM customer";
        ResultSet resultSet = CrudUtil.execute(sql);
        if (resultSet.next()){
            return resultSet.getString(1);
        }return "00";
    }
}

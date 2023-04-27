package lk.ijse.management.model;

import lk.ijse.management.dto.Supplier;
import lk.ijse.management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static boolean add(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier(supply_id,supply_name,supply_contact,supply_address) VALUES (?,?,?,?)";
        return CrudUtil.execute(sql,supplier.getSupplierId(),supplier.getSupplierName(),supplier.getSupplierContact(),supplier.getSupplierAddress());
    }

    public static Supplier get(String supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supply_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, supplierId);
        if(resultSet.next()){
            return new Supplier(resultSet.getString(1),resultSet.getString(2), resultSet.getString(3), resultSet.getString(4) );
        } return null;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<Supplier> data = new ArrayList<>();
        while (resultSet.next()){
            data.add(new Supplier(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
        }return data;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET supply_name = ?,supply_address = ?,supply_contact =? WHERE supply_id =?";
        return CrudUtil.execute(sql,supplier.getSupplierName(),supplier.getSupplierAddress(),supplier.getSupplierContact(),supplier.getSupplierId());
    }

    public static boolean remove(String supplierId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supply_id = ?";
        return CrudUtil.execute(sql,supplierId);
    }
}

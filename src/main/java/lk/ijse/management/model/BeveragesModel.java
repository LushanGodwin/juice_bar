package lk.ijse.management.model;

import lk.ijse.management.dto.Customer;
import lk.ijse.management.dto.Item;
import lk.ijse.management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeveragesModel {
    public static boolean remove(String juiceID) throws SQLException {
        String sql = "DELETE FROM item WHERE item_id = ?";
        return CrudUtil.execute(sql,juiceID);
    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM item where item_type=?";
        ResultSet resultSet = CrudUtil.execute(sql,"Juice");
        List<Item> data = new ArrayList<>();
        while (resultSet.next()){
            data.add(new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4)));
        }return data;
    }
    public static Item get(String juiceId) throws SQLException {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,juiceId);
        if(resultSet.next()){
            return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4));
        } return null;

    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE item SET item_name = ?,item_price = ? WHERE item_id =?";
        return CrudUtil.execute(sql,item.getItem_name(),item.getItem_price(),item.getItem_id());

    }

    public static boolean add(Item item) throws SQLException {
        String sql = "INSERT INTO item(item_id,item_name,item_price,item_type) VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,item.getItem_id(),item.getItem_name(),item.getItem_price(),item.getItem_type());
    }
}

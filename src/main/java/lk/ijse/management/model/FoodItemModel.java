package lk.ijse.management.model;

import lk.ijse.management.dto.Item;
import lk.ijse.management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodItemModel {

    public static boolean remove(String foodID) throws SQLException {
        String sql = "DELETE FROM item WHERE item_id = ?";
        return CrudUtil.execute(sql,foodID);
    }

    public static boolean add(Item food) throws SQLException {
        String sql = "INSERT INTO item(item_id,item_name,item_price,item_type) VALUES(?,?,?,?)";
        return CrudUtil.execute(sql,food.getItem_id(),food.getItem_name(),food.getItem_price(),food.getItem_type());
    }

    public static boolean update(Item food) throws SQLException {
        String sql = "UPDATE item SET item_name = ?,item_price = ? WHERE item_id =?";
        return CrudUtil.execute(sql,food.getItem_name(),food.getItem_price(),food.getItem_id());
    }

    public static Item get(String foodId) throws SQLException {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql,foodId);
        if(resultSet.next()){
            return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4));
        } return null;
    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM item where item_type=?";
        ResultSet resultSet = CrudUtil.execute(sql,"Food");
        List<Item> data = new ArrayList<>();
        while (resultSet.next()){
            data.add(new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4)));
        }return data;
    }
}

package lk.ijse.management.model;

import lk.ijse.management.dto.Cart;
import lk.ijse.management.dto.Item;
import lk.ijse.management.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public static Item searchById(String itemId) throws SQLException {
        String sql="SELECT * FROM item WHERE item_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, itemId);

        if(resultSet.next()){
            return (new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getString(4)));
        }
        return null;
    }

    public static List<String> getIdes() throws SQLException {
        String sql = "SELECT item_id FROM item";
        ResultSet resultSet = CrudUtil.execute(sql);
        List<String> id = new ArrayList<>();
        while (resultSet.next()){
            id.add(resultSet.getString(1));
        }return  id;

    }

    public static boolean updateQty(List<Cart> cartDTOList) throws SQLException {
        for (Cart dto : cartDTOList) {
            if (!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(Cart dto) throws SQLException {
        String sql = "UPDATE item SET item_qty = (item_qty - ?) WHERE item_id = ?";
        return CrudUtil.execute(sql, dto.getQty(), dto.getCode());
    }

}

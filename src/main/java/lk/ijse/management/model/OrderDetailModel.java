package lk.ijse.management.model;

import lk.ijse.management.dto.Cart;
import lk.ijse.management.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public static boolean save(String oId, List<Cart> cartDTOList) throws SQLException {
        for (Cart dto : cartDTOList) {
            if (!save(oId, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, Cart dto) throws SQLException {

        String sql = "INSERT INTO orders_details(order_id, item_id, order_qyt) VALUES (?, ?, ?)";

        return CrudUtil.execute(sql, oId, dto.getCode(), dto.getQty());

    }
}

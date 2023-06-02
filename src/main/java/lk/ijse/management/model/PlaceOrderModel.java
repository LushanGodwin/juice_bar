package lk.ijse.management.model;

import lk.ijse.management.db.DBConnection;
import lk.ijse.management.dto.Cart;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderModel {
    public static boolean placeOrder(String oId, String cusId, List<Cart> cartDTOList, String netTotal) throws SQLException {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = OrderModel.save(oId, cusId, LocalDate.now(), netTotal);
            if (isSaved) {
//                boolean isUpdated = ItemModel.updateQty(cartDTOList);
//                if (isUpdated) {
                boolean isOrderDetailSaved = OrderDetailModel.save(oId, cartDTOList);
                     if (isOrderDetailSaved) {
                        con.commit();
                        return true;
                    }
                }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            con.setAutoCommit(true);
        }
    }

}

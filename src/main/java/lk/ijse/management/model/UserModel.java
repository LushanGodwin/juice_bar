package lk.ijse.management.model;

import lk.ijse.management.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean checkUser(String userName, String password) throws SQLException {
        boolean valid=false;
        Connection connection = DBConnection.getInstance().getConnection();
        String sql="SELECT * FROM user WHERE user_name= ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,userName);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            String user_password = resultSet.getString(2);
            if(user_password.equals(password)){
                valid=true;
            }
        }
        return valid;
        }

}

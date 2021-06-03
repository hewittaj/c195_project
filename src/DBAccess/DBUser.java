package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

import Models.User;


public class DBUser {
    public static ObservableList<User> getAllUsersInfo(){
        ObservableList<User> users = FXCollections.observableArrayList();

        try{
            String sql = "SELECT User_ID, User_Name, Password FROM Users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(userId, userName, password);

                // Add new user to our observable list
                users.add(user);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return users;
    }
}

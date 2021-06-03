package Controllers;

import DBAccess.DBUser;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ErrorChecker {

    public static boolean validateLogin(String username, String password){
        ObservableList<User> users = DBUser.getAllUsersInfo();
        for (User user: users){
            System.out.println("User_id" + user.getUserId() + " user_name" + user.getUserName() + " password: " + user.getPassword());
        }
        return true;
    }
}

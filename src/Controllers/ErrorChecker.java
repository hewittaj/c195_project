package Controllers;

import DBAccess.DBUser;
import Models.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public class ErrorChecker {

    public static boolean validateLogin(String username, String password){
        // Initialize an observable list of users and a boolean for if the login is valid
        ObservableList<User> users = DBUser.getAllUsersInfo();
        boolean validLogin = false;

        // Loop through users to validate correct username/password combo
        for (User user: users){
            if(user.getUserName().equals(username)){
                if(user.getPassword().equals(password)){
                    validLogin = true;
                }
            }
        }
        return validLogin;
    }

    public static boolean validateAddCustomerTextFields(ArrayList<TextField> textFieldArrayList){
        boolean textFieldIsEmpty = false;
        for(TextField field: textFieldArrayList){
            if(field.getText().isEmpty()){
                textFieldIsEmpty = true;
                break;
            }
        }
        return textFieldIsEmpty;
    }
}

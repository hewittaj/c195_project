package Controllers;

import DBAccess.DBUser;
import Models.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
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

    /**
     * This method validates the text fields in the add customer view for any errors
     * @param textFieldArrayList Array list containing all text fields present on screen
     * @return Boolean value indicating if a text field is empty or not
     */
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

    /**
     * This method checks all fields on the add customer for any errors
     * @param textFields Array list containing all text fields present on the screen
     * @param contactInfo Combo box that is passed that contains contact information present on the screen
     * @param dateBoxes Array list of the date boxes that contains date information on the screen
     * @param startTimeBoxes Array list of the start time boxes that contains starting time information on the screen
     * @param endTimeBoxes Array list of the end time boxes that contains ending time information on the screen
     * @return Boolean returned whether or not an error is detected. True means a detected error was found, false means no error detected
     */
    public static boolean validateAddCustomerFields(ArrayList<TextField> textFields, ComboBox contactInfo,
                                                    ArrayList<ComboBox> dateBoxes, ArrayList<ComboBox> startTimeBoxes,
                                                    ArrayList<ComboBox> endTimeBoxes){
        boolean errorDetected = false;
        for(TextField textField: textFields){

        }

        return errorDetected;
    }
}

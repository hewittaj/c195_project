package Controllers;

import DBAccess.DBFirstLevelDivisions;
import DBAccess.DBUser;
import Models.Appointment;
import Models.Customer;
import Models.User;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ErrorChecker {

    public static boolean validateLogin(String username, String password) {
        // Initialize an observable list of users and a boolean for if the login is valid
        ObservableList<User> users = DBUser.getAllUsersInfo();
        boolean validLogin = false;

        // Loop through users to validate correct username/password combo
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                if (user.getPassword().equals(password)) {
                    validLogin = true;
                }
            }
        }
        return validLogin;
    }

    /**
     * This method validates the text fields in the add customer view for any errors
     *
     * @param textFieldArrayList Array list containing all text fields present on screen
     * @return Boolean value indicating if a text field is empty or not
     */
    public static boolean validateAddCustomerTextFields(ArrayList<TextField> textFieldArrayList) {
        boolean textFieldIsEmpty = false;
        for (TextField field : textFieldArrayList) {
            if (field.getText().isEmpty()) {
                textFieldIsEmpty = true;
                break;
            }
        }
        return textFieldIsEmpty;
    }

    /**
     * This method checks all fields on the add customer for any errors, will return -1 if no errors detected
     *
     * @param textFields     Array list containing all text fields present on the screen
     * @param contactInfo    Combo box that is passed that contains contact information present on the screen
     * @param dateBoxes      Array list of the date boxes that contains date information on the screen
     * @param startTimeBoxes Array list of the start time boxes that contains starting time information on the screen
     * @param endTimeBoxes   Array list of the end time boxes that contains ending time information on the screen
     * @return Boolean returned whether or not an error is detected. True means a detected error was found, false means no error detected
     */
    public static int validateAppointmentFields(ArrayList<TextField> textFields, ArrayList<TextField> idTextFieldsOnly,
                                                ComboBox contactInfo,
                                                ArrayList<ComboBox> dateBoxes, ArrayList<ComboBox> startTimeBoxes,
                                                ArrayList<ComboBox> endTimeBoxes) {
        // Initialize variables
        int errorNumber = 0;

        // If there is an empty text field
        for (TextField empty : textFields) {
            if (empty.getText().isEmpty()) {
                errorNumber = 2;
                return errorNumber;
            }
        }

        // If there is alphabetic letter in the id number fields
        for (TextField idTextField : idTextFieldsOnly) {
            if (idTextField.getText().matches("^[a-zA-Z]*$")) {
                errorNumber = 3;
                return errorNumber;
            }
        }

        // If no contact is selected
        if (contactInfo.getSelectionModel().getSelectedItem() == null) {
            errorNumber = 4;
            return errorNumber;
        }

        // If a combo box for the dates isn't selected
        for (ComboBox dateBox : dateBoxes) {
            if (dateBox.getSelectionModel().getSelectedItem() == null) {
                errorNumber = 5;
                return errorNumber;
            }
        }

        // If the start and end time box don't have a box selected
        for (ComboBox startTimeBox : startTimeBoxes) {
            if (startTimeBox.getSelectionModel().getSelectedItem() == null) {
                errorNumber = 6;
                return errorNumber;
            }
        }

        for (ComboBox endTimeBox : endTimeBoxes) {
            if (endTimeBox.getSelectionModel().getSelectedItem() == null) {
                errorNumber = 6;
                return errorNumber;
            }
        }



        // If no error is detected return -1
        errorNumber = -1;
        return errorNumber;
    }

    /**
     * This method detects if a customer is selected to be deleted. If it is a null customer it will return
     * an error number
     *
     * @param customer Customer object that is passed to detect if a customer was selected on main screen
     * @return Returns an integer to be given to the show alerts class if no customer is selected
     */
    public static int customerIsSelected(Customer customer) {

        // If customer is not selected, return number 7 for our show alert screen
        if (customer == null) {
            return 7;
        }
        // Otherwise no error was detected.
        return -1;
    }

    /**
     * This method detects if an appointment is selected to be deleted. If it is a null appointment it will return
     * an error number
     *
     * @param appointment Appointment object that is passed to detect if an appointment was selected on the main screen
     * @return Returns an integer to be given to the show alert class if no appointment is selected
     */
    public static int appointmentIsSelected(Appointment appointment) {

        // If customer is not selected, return number 8 for our show alert screen
        if (appointment == null) {
            return 8;
        }

        // Otherwise no error was detected
        return -1;
    }

    /**
     * This method detects if an customer name text field in the main screen is empty/null
     *
     * @param newName New name the customer has been given
     * @return Returns an error number that dictates if an error is shown or not
     */
    public static int customerNameTextBoxEditEvent(String newName) {

        // If the new customer name is empty or null then return an error number
        if (newName.isEmpty()) {
            return 9;
        }

        // Otherwise no error detected
        return -1;
    }

    public static int customerAddressTextBoxEditEvent(String newAddress) {

        // If the new address text box is empty
        if (newAddress.isEmpty()) {
            return 10;
        }

        return -1;
    }

    public static int zipCodeTextBoxEditEvent(String newZipCode) {

        // If the new zip code text box is empty
        if (newZipCode.isEmpty()) {
            return 11;
        }

        return -1;
    }

    public static int phoneNumberTextBoxEditEvent(String newPhoneNumber) {

        // If the new phone number text box is empty
        if (newPhoneNumber.isEmpty()) {
            return 12;
        }

        return -1;
    }

    public static int divisionIdTextBoxEvent(int newDivisionId) {

        // If the division id text box does not contain a valid division Id
        if (DBFirstLevelDivisions.validateDivisionId(newDivisionId) == false) {
            return 14;
        }

        return -1;
    }
}

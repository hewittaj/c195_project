package Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ShowAlerts {

    /**
     * Method that shows an alert based on what number is passed to it
     * @param number Number that tells the method which alert to display
     */
    public static void showAlert(int number){
        // If no customer was selected on the main screen to be modified, i.e. NullPointerException thrown
        if(number == 0){
            // Set up an alert for no value selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No Value Selected");
            alert.setHeaderText("No customer was selected to update!\nPlease select a customer and try again.");
            alert.setContentText("Click 'OK' to confirm.");
            Optional<ButtonType> decision = alert.showAndWait();
            return;
        }

        // Add Customer Screen detected an empty text field
        if (number == 1) {
            // Set up an alert for an empty text field
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Empty Text Field");
            alert.setHeaderText("Not all text fields are filled out.\n" +
                    "Please fill them out or go back to the main screen");
            alert.setContentText("Click 'OK' to confirm.");
            Optional<ButtonType> decision = alert.showAndWait();
        }

        // Empty text field in appointment screen
        if (number == 2){
            // Set up an alert for an empty text field
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Empty Text Field");
            alert.setHeaderText("Not all text fields are filled out.\n" +
                    "Please fill them out or go back to the main screen");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Alphabetic letter in id fields in appointment screen
        if(number == 3){
            // Set up an alert for incorrect input
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Please only input numbers into the user id\nand customer id fields.");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // No contact selected in appointment screen
        if(number == 4){
            // Set up an alert for no value selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("No contact selected, please select a contact.");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Date combo boxes are not all set to an item in appointment screen
        if(number == 5){
            // Set up an alert for not all date combo boxes selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Not all date combo boxes have been selected!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Not all time combo boxes are all set to an item in appointment screen
        if(number == 6){
            // Set up an alert for not all time combo boxes selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Not all time combo boxes have been selected!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // No customer was selected to be deleted
        if(number == 7){
            // Set up an alert for no customer selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid selection!");
            alert.setHeaderText("No customer was selected!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // No appointment was selected to be deleted
        if(number == 8){
            // Set up an alert for no customer selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid selection!");
            alert.setHeaderText("No appointment was selected!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Customer name text box edit event is blank
        if(number == 9){
            // Set up an alert for improper customer name
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer name must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Customer address text box edit event is blank
        if(number == 10){
            // Set up an alert for improper address
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer address must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Customer zip code text box edit event is blank
        if (number == 11){
            // Set up an alert for improper customer zip code
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer zip code must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Customer phone number text box edit event is blank
        if(number == 12){
            // Set up an alert for improper customer phone number
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer phone number must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Division id text box edit event is blank
        if(number == 13){
            // Set up an alert for improper customer division id
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer division ID must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Division id is not valid/in database
        if(number == 14){
            // Set up an alert for improper customer division id
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid ID!");
            alert.setHeaderText("Customer division ID must be in database!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }
    }
}

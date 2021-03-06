package Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * This class is utilized to show any alerts that are detected from various parts of the program
 */
public class ShowAlerts {

    /**
     * Method that shows an alert based on what number is passed to it
     *
     * @param number Number that tells the method which alert to display
     */
    public static void showAlert(int number) {
        // If no customer was selected on the main screen to be modified, i.e. NullPointerException thrown
        if (number == 0) {
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
        if (number == 2) {
            // Set up an alert for an empty text field
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Empty Text Field");
            alert.setHeaderText("Not all text fields are filled out.\n" +
                    "Please fill them out or go back to the main screen");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Alphabetic letter in id fields in appointment screen
        if (number == 3) {
            // Set up an alert for incorrect input
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Please only input numbers into the user id\nand customer id fields.");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // No contact selected in appointment screen
        if (number == 4) {
            // Set up an alert for no value selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("No contact selected, please select a contact.");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Date combo boxes are not all set to an item in appointment screen
        if (number == 5) {
            // Set up an alert for not all date combo boxes selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Not all date combo boxes have been selected!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Not all time combo boxes are all set to an item in appointment screen
        if (number == 6) {
            // Set up an alert for not all time combo boxes selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Not all time combo boxes have been selected!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // No customer was selected to be deleted
        if (number == 7) {
            // Set up an alert for no customer selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid selection!");
            alert.setHeaderText("No customer was selected!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // No appointment was selected to be deleted
        if (number == 8) {
            // Set up an alert for no customer selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid selection!");
            alert.setHeaderText("No appointment was selected!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Customer name text box edit event is blank
        if (number == 9) {
            // Set up an alert for improper customer name
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer name must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Customer address text box edit event is blank
        if (number == 10) {
            // Set up an alert for improper address
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer address must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Customer zip code text box edit event is blank
        if (number == 11) {
            // Set up an alert for improper customer zip code
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer zip code must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Customer phone number text box edit event is blank
        if (number == 12) {
            // Set up an alert for improper customer phone number
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer phone number must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Division id text box edit event is blank
        if (number == 13) {
            // Set up an alert for improper customer division id
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid input!");
            alert.setHeaderText("Customer division ID must be entered!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Division id is not valid/in database
        if (number == 14) {
            // Set up an alert for improper customer division id
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid ID!");
            alert.setHeaderText("Customer division ID must be in database!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Appointment start time is after end time
        if (number == 15) {
            // Set up an alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Appointment Times!");
            alert.setHeaderText("Start time cannot be after end time!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Appointment end time is before start time
        if (number == 16) {
            // Set up an alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Appointment Times!");
            alert.setHeaderText("End time cannot be before start time!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Appointment start time is before 8 am EST
        if (number == 17) {
            // Set up an alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Appointment Times!");
            alert.setHeaderText("Start time cannot be before 8am Eastern Time!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Appointment end time cannot be after 10 pm EST
        if (number == 18) {
            // Set up an alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Appointment Times!");
            alert.setHeaderText("End time cannot be after 10pm Eastern Time!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Appointment start or end time must be greater than the current date/time
        if (number == 19) {
            // Set up an alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Appointment Times!");
            alert.setHeaderText("Start/end time appointment time must be greater than the current date or time!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // Overlapped appointment
        if (number == 20) {
            // Set up an alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Invalid Appointment Times!");
            alert.setHeaderText("This customer already has an appointment at this time, try another time!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // No appointment selected to be modified
        if (number == 21) {
            // Set up an alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No Appointment Selected!");
            alert.setHeaderText("No appointment was selected to modify. Please select an appointment!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }

        // No appointment within 15 minutes
        if (number == 22) {
            // Set up an alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("No Upcoming Appointment");
            alert.setHeaderText("No appointment in the next 15 minutes!");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
        }
    }
}

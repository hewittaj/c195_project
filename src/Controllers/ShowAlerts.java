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
            // Set up an alert for no value selected
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Empty Text Field");
            alert.setHeaderText("Not all text fields are filled out.\n" +
                    "Please fill them out or go back to the main screen");
            alert.setContentText("Click 'OK' to confirm.");
            Optional<ButtonType> decision = alert.showAndWait();
        }

    }
}

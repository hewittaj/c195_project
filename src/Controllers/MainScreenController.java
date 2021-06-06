package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {


    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public Button reportButton;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    /**
     * The addCustomerAction detects whether or not the add customer button was pushed and loads the next screen
     * @param actionEvent Event caught for the add customer button being pushed
     * @throws IOException Exception that is caught in case of any IO errors
     */
    public void addCustomerAction(ActionEvent actionEvent) throws IOException {
        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_customer_screen.fxml"));

        // Set parent and scene
        Parent mainScreenParent = loader.load();
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void modifyCustomerAction(ActionEvent actionEvent) throws IOException {

        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_customer_screen.fxml"));

        // Set parent and scene
        Parent mainScreenParent = loader.load();
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void addAppointmentAction(ActionEvent actionEvent) throws IOException {
        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_appointment_screen.fxml"));

        // Set parent and scene
        Parent mainScreenParent = loader.load();
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void modifyAppointmentAction(ActionEvent actionEvent) throws IOException {
        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_appointment_screen.fxml"));

        // Set parent and scene
        Parent mainScreenParent = loader.load();
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * @param actionEvent
     */
    public void reportButtonAction(ActionEvent actionEvent) {
    }

    /**
     * @param actionEvent
     */
    public void deleteCustomerAction(ActionEvent actionEvent) {
    }

    /**
     * @param actionEvent
     */
    public void deleteAppointmentAction(ActionEvent actionEvent) {
    }
}

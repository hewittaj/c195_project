package Controllers;

import DBAccess.DBCustomers;
import Models.Appointment;
import Models.Customer;
import Controllers.ShowAlerts;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML public Button addCustomerButton;
    @FXML public Button modifyCustomerButton;
    @FXML public Button deleteCustomerButton;
    @FXML public Button addAppointmentButton;
    @FXML public Button modifyAppointmentButton;
    @FXML public Button deleteAppointmentButton;
    @FXML public Button reportButton;
    @FXML public TableColumn<Customer, Integer> customerIDColumn;
    @FXML public TableColumn<Customer, String> customerNameColumn;
    @FXML public TableColumn<Customer, String> customerAddressColumn;
    @FXML public TableColumn<Customer, String> zipCodeColumn;
    @FXML public TableColumn<Customer, String> phoneNumberColumn;
    @FXML public TableColumn<Customer, Integer> divisionIDColumn;
    @FXML public TableView<Appointment> appointmentTableView;
    @FXML public TableView<Customer> customerTableView;

    // List of customers currently in database
    ObservableList<Customer> customers = DBCustomers.getMainScreenCustomerInfo();

    public String loggedInUser;  // Currently logged in user

    public void passLoggedInUser(String loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        customers = DBCustomers.getMainScreenCustomerInfo();

        // Initialize customer table
        customerTableView.setItems(customers);

        // Initialize customer column names -> string must match the model's spelling/capitalization
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        zipCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("zipCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));

    }

    /**
     * The addCustomerAction detects whether or not the add customer button was pushed and loads the next screen
     * @param actionEvent Event caught for the add customer button being pushed
     * @throws IOException Exception that is caught in case of any IO errors
     */
    public void addCustomerAction(ActionEvent actionEvent) throws IOException {
        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/add_customer_screen.fxml"));

        // Set parent
        Parent mainScreenParent = loader.load();

        // Instantiate controller and call functions to pass info between screens
        AddCustomerScreenController controller = loader.getController();
        controller.passNumberOfCustomers(customers.size());
        controller.passLoggedInUser(loggedInUser);

        // Set scene
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
        try{
            Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
            // Load next screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_customer_screen.fxml"));

            // Set parent
            Parent mainScreenParent = loader.load();

            // Instantiate controller and call functions to pass info between screens
            ModifyCustomerScreenController controller = loader.getController();
            controller.passCustomer(selectedCustomer);
            controller.passLoggedInUser(loggedInUser);

            // Set scene
            Scene mainScreenScene = new Scene(mainScreenParent);

            // This line gets the Stage information
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        }
        catch(NullPointerException e){
            ShowAlerts.showAlert(0);
        }

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

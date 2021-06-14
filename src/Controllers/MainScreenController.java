package Controllers;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Models.Appointment;
import Models.Customer;

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
import java.time.LocalDateTime;
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
    @FXML public TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML public TableColumn<Appointment, Integer> userIdAppointmentColumn;
    @FXML public TableColumn<Appointment, Integer> appointmentCustomerIdColumn;
    @FXML public TableColumn<Appointment, String> titleColumn;
    @FXML public TableColumn<Appointment, String> descriptionColumn;
    @FXML public TableColumn<Appointment, String> locationColumn;
    @FXML public TableColumn<Appointment, String> contactIdColumn;
    @FXML public TableColumn<Appointment, String> typeColumn;
    @FXML public TableColumn<Appointment, LocalDateTime> startTimeColumn;
    @FXML public TableColumn<Appointment, LocalDateTime> endTimeColumn;
    @FXML public TableColumn<Appointment, LocalDateTime> dateColumn;
    @FXML public TableView<Appointment> appointmentTableView;
    @FXML public TableView<Customer> customerTableView;


    // List of customers/appointments currently in database
    ObservableList<Customer> customers = DBCustomers.getMainScreenCustomerInfo();
    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();

    public String loggedInUser;  // Currently logged in user

    public void passLoggedInUser(String loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        // Populate our lists
        customers = DBCustomers.getMainScreenCustomerInfo();
        appointments = DBAppointments.getAllAppointments();

        // Initialize customer and appointment tables
        customerTableView.setItems(customers);
        appointmentTableView.setItems(appointments);
        // Initialize customer column names -> string must match the model's spelling/capitalization
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        zipCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("zipCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));

        // Initialize appointment column names -> string must match the model's spelling/capitalization
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        userIdAppointmentColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
        appointmentCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
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

        // Set parent
        Parent mainScreenParent = loader.load();

        // Instantiate controller and call functions to pass info between screens
        AddAppointmentScreenController controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        controller.passNumberOfAppointments(appointmentTableView.getItems().size());

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

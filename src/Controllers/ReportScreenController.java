package Controllers;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomers;
import Models.Appointment;
import Models.Contact;
import Models.CustomersWithSameZipCode;
import Models.MonthAndTypeReport;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This is the class that controls the report screen
 */
public class ReportScreenController implements Initializable {
    // Table Views
    @FXML
    public TableView<MonthAndTypeReport> monthAndTypeOfAppointmentsTableView;
    @FXML
    public TableView<Appointment> appointmentsByContactTableView;
    @FXML
    public TableView<CustomersWithSameZipCode> customersWithSameZipCodeTableView;

    // Appointments by type and month report
    @FXML
    public TableColumn<MonthAndTypeReport, String> monthColumn;
    @FXML
    public TableColumn<MonthAndTypeReport, String> typeColumn;
    @FXML
    public TableColumn<MonthAndTypeReport, Integer> countColumn;

    // Appointments by contact report
    @FXML
    public ComboBox contactComboBox;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    public TableColumn<Appointment, String> contactTypeColumn;
    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> startDateTimeColumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> endDateTimeColumn;
    @FXML
    public TableColumn<Appointment, Integer> customerIdColumn;

    // Customers with same zip code report
    @FXML
    public TableColumn<CustomersWithSameZipCode, String> zipCodeColumn;
    @FXML
    public TableColumn<CustomersWithSameZipCode, Integer> countZipColumn;

    @FXML
    public Button backButton;

    public ObservableList<Contact> allContacts = DBContacts.getAllContacts();
    public ObservableList<MonthAndTypeReport> monthAndTypeReports =
            DBAppointments.getMonthlyAppointmentsByTypeAndMonth();
    public ObservableList<CustomersWithSameZipCode> zipCodesReport = DBCustomers.getCountOfCustomersWithSameZipCode();
    public String loggedInUser;


    /**
     * This method initializes the data in the table view and the report screen
     *
     * @param url Not used
     * @param rb  Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateContactList();
        populateMonthAndTypeReport();
        populateCustomersWithSameZipCodeReport();

        // Initialize month and type report column names -> string must match the model's spelling/capitalization
        monthColumn.setCellValueFactory(new PropertyValueFactory<MonthAndTypeReport, String>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<MonthAndTypeReport, String>("type"));
        countColumn.setCellValueFactory(new PropertyValueFactory<MonthAndTypeReport, Integer>("count"));

        // Initialize appointments by contact report column names
        // -> string must match the model's spelling/capitalization
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        contactTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));

        // Initialize customers with same zip code column names
        // -> string must match the model's spelling/capitalization
        zipCodeColumn.setCellValueFactory(new PropertyValueFactory<CustomersWithSameZipCode, String>("zipCode"));
        countZipColumn.setCellValueFactory(new PropertyValueFactory<CustomersWithSameZipCode, Integer>("count"));

    }

    /**
     * This method detects if the contact combo box is selected and populates the report info based on the contact
     * selected
     *
     * @param actionEvent Event that is caught to detect if the combo box was selected
     */
    public void contactComboBoxSelected(ActionEvent actionEvent) {

        // Get contact id
        int contactId = DBContacts.getContactIdFromName(
                contactComboBox.getSelectionModel().getSelectedItem().toString());

        // Create list of appointments for that contact
        ObservableList<Appointment> appointmentsByContact = DBAppointments.getSpecificContactsAppointments(contactId);
        appointmentsByContactTableView.setItems(appointmentsByContact);
    }

    /**
     * This method populates the contact information in the add appointment screen
     */
    public void populateContactList() {
        for (Contact contact : allContacts) {
            contactComboBox.getItems().add(contact);
        }
    }

    /**
     * This method populates the month and type report table view
     */
    public void populateMonthAndTypeReport() {
        monthAndTypeOfAppointmentsTableView.getItems().setAll(monthAndTypeReports);
    }

    /**
     * This method populates the customers with same zip code report table view
     */
    public void populateCustomersWithSameZipCodeReport() {
        customersWithSameZipCodeTableView.getItems().setAll(zipCodesReport);
    }

    /**
     * This method detects if the back button was selected and loads the main screen
     *
     * @param actionEvent Event that is caught to detect if the back button was selected
     * @throws IOException Exception that is caught to detect any IO exceptions
     */
    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));

        // Set parent
        Parent mainScreenParent = loader.load();

        // Instantiate controller and call functions to pass info between screens
        MainScreenController controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);

        // Set scene
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * This method passes the currently logged in user from the main screen to the report screen, for database purposes
     *
     * @param loggedInUser Currently logged in user
     */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

}

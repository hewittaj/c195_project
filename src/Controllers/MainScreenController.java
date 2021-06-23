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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    public Button addCustomerButton;
    @FXML
    public Button modifyCustomerButton;
    @FXML
    public Button deleteCustomerButton;
    @FXML
    public Button addAppointmentButton;
    @FXML
    public Button modifyAppointmentButton;
    @FXML
    public Button deleteAppointmentButton;
    @FXML
    public Button reportButton;
    @FXML
    public TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    public TableColumn<Customer, String> customerNameColumn;
    @FXML
    public TableColumn<Customer, String> customerAddressColumn;
    @FXML
    public TableColumn<Customer, String> zipCodeColumn;
    @FXML
    public TableColumn<Customer, String> phoneNumberColumn;
    @FXML
    public TableColumn<Customer, Integer> divisionIDColumn;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    public TableColumn<Appointment, Integer> appointmentCustomerIdColumn;
    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    public TableColumn<Appointment, String> locationColumn;
    @FXML
    public TableColumn<Appointment, String> contactIdColumn;
    @FXML
    public TableColumn<Appointment, String> typeColumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> startTimeColumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> endTimeColumn;
    @FXML
    public TableView<Appointment> appointmentTableView;
    @FXML
    public TableView<Customer> customerTableView;
    public String loggedInUser;  // Currently logged in user
    // List of customers/appointments currently in database
    ObservableList<Customer> customers = DBCustomers.getMainScreenCustomerInfo();
    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();

    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate our lists
        customers = DBCustomers.getMainScreenCustomerInfo();
        appointments = DBAppointments.getAllAppointments();

        // Initialize customer and appointment tables
        customerTableView.setItems(customers);
        appointmentTableView.setItems(appointments);
        // Initialize customer column names -> string must match the model's spelling/capitalization
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        customerAddressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        zipCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("zipCode"));
        zipCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));
        divisionIDColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        // Initialize appointment column names -> string must match the model's spelling/capitalization
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
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
     *
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
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void modifyCustomerAction(ActionEvent actionEvent) throws IOException {
        try {
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
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        } catch (NullPointerException e) {
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
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * @param actionEvent
     * @throws IOException
     */
    public void modifyAppointmentAction(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/modify_appointment_screen.fxml"));

        // Set parent
        Parent mainScreenParent = loader.load();

        // Instantiate controller and call functions to pass info between screens
        ModifyAppointmentScreenController controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        controller.passAppointment(selectedAppointment);
        // Set scene
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * @param actionEvent
     */
    public void reportButtonAction(ActionEvent actionEvent) {
        customerIDColumn.setOnEditStart(customerIDColumn.getOnEditCommit());
    }

    /**
     * @param actionEvent
     */
    public void deleteCustomerAction(ActionEvent actionEvent) {
        // Initialize our selected customer and detect if nothing was selected
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        int errorNumber = ErrorChecker.customerIsSelected(selectedCustomer);

        if (errorNumber == 7) {
            ShowAlerts.showAlert(errorNumber);
            return;
        }

        // Initialize how many appointments the customer has
        int numOfAppointments = DBAppointments.getNumberOfAppointments(selectedCustomer.getCustomerId());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (numOfAppointments == 0) {

            // Set up an alert to confirm deletion of customer
            alert.setTitle("Confirm Deletion!");
            alert.setHeaderText("Are you sure you want to delete this customer?");
            alert.setContentText("Click 'OK' to confirm.");
            Optional<ButtonType> decision = alert.showAndWait();

            // If user wants to continue to delete customer then do it
            if (decision.get() == ButtonType.OK) {
                DBCustomers.deleteCustomer(selectedCustomer);
                customers = DBCustomers.getMainScreenCustomerInfo();
                customerTableView.setItems(customers);

                // Let user know customer has been deleted
                Alert customerDeleted = new Alert(Alert.AlertType.INFORMATION);
                customerDeleted.setTitle("Customer Deleted!");
                customerDeleted.setHeaderText("Customer has been deleted!");
                customerDeleted.setContentText("Click OK to continue.");
                customerDeleted.showAndWait();
            } else {
                return;
            }
        } else {
            // Customer still has an appointment associated with them, throw an error screen
            alert.setTitle("Cannot Delete!");
            alert.setHeaderText("Customer still has appointments associated to them!\n" +
                    "Please delete appointments first before deleting customer.");
            alert.setContentText("Click 'OK' to confirm.");
            alert.showAndWait();
            return;
        }
    }

    /**
     * This method detects if the delete appointment button has been pressed and will validate if it can be done.
     * If it can be done, then it will delete, otherwise an error will be thrown
     *
     * @param actionEvent Event that is caught to detect delete button press
     */
    public void deleteAppointmentAction(ActionEvent actionEvent) {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        int errorNumber = ErrorChecker.appointmentIsSelected(selectedAppointment);

        if (errorNumber == 8) {
            ShowAlerts.showAlert(errorNumber);
            return;
        }

        // Set up an alert to confirm deletion of appointment
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion!");
        alert.setHeaderText("Are you sure you want to delete this appointment?");
        alert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = alert.showAndWait();

        // If user wants to continue to delete customer then do it
        if (decision.get() == ButtonType.OK) {
            DBAppointments.deleteAppointment(selectedAppointment);
            appointments = DBAppointments.getAllAppointments();
            appointmentTableView.setItems(appointments);

            // Let user know appointment has been deleted
            Alert customerDeleted = new Alert(Alert.AlertType.INFORMATION);
            customerDeleted.setTitle("Appointment Deleted!");
            customerDeleted.setHeaderText("Appointment ID: " + selectedAppointment.getAppointmentId()
                    + " has been deleted!\n" +
                    "Type: " + selectedAppointment.getType());
            customerDeleted.setContentText("Click OK to continue.");
            customerDeleted.showAndWait();
        } else {
            return;
        }
    }

    /**
     * This method detects if a customer name record is being edited on the table view
     *
     * @param customerStringCellEditEvent Event that is caught to detect the cell being edited
     */
    public void customerNameEditCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
        String newCustomerName = customerStringCellEditEvent.getNewValue();
        int errorNumber = ErrorChecker.customerNameTextBoxEditEvent(newCustomerName);

        if (errorNumber != -1) {
            ShowAlerts.showAlert(errorNumber);

            // Refresh table view
            customerTableView.setItems(customers);
            return;
        } else {
            // Get index of customer
            int index = customerStringCellEditEvent.getTablePosition().getRow();

            // Update database
            DBCustomers.editCustomerNameEvent(newCustomerName, customers.get(index));

            // Refresh table view
            customers = DBCustomers.getMainScreenCustomerInfo();
            customerTableView.setItems(customers);
        }
    }

    /**
     * This method detects if a customer address record is being edited on the table view
     *
     * @param customerStringCellEditEvent Event that is caught to detect the cell being edited
     */
    public void customerAddressEditCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
        String newCustomerAddress = customerStringCellEditEvent.getNewValue();
        int errorNumber = ErrorChecker.customerAddressTextBoxEditEvent(newCustomerAddress);

        if (errorNumber != -1) {
            ShowAlerts.showAlert(errorNumber);
        } else {
            // Get index of customer
            int index = customerStringCellEditEvent.getTablePosition().getRow();

            // Update database
            DBCustomers.editCustomerAddressEvent(newCustomerAddress, customers.get(index));

            // Refresh table view
            customers = DBCustomers.getMainScreenCustomerInfo();
            customerTableView.setItems(customers);
        }
    }

    /**
     * This method detects if a customer zip code record is being edited on the table view
     *
     * @param customerStringCellEditEvent Event that is caught to detect cell being edited
     */
    public void zipCodeEditCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
        String newZipCode = customerStringCellEditEvent.getNewValue();
        int errorNumber = ErrorChecker.zipCodeTextBoxEditEvent(newZipCode);

        if (errorNumber != -1) {
            ShowAlerts.showAlert(errorNumber);

            // Refresh table view

            customerTableView.setItems(customers);
            return;
        } else {
            // Get index of customer
            int index = customerStringCellEditEvent.getTablePosition().getRow();

            // Update database
            DBCustomers.editZipCodeEvent(newZipCode, customers.get(index));

            // Refresh table view
            customers = DBCustomers.getMainScreenCustomerInfo();
            customerTableView.setItems(customers);
        }
    }

    /**
     * This method detects if a customer phone record is being edited on the table view
     *
     * @param customerStringCellEditEvent Event that is caught to detect cell being edited
     */
    public void phoneNumberEditCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
        String newPhoneNumber = customerStringCellEditEvent.getNewValue();
        int errorNumber = ErrorChecker.phoneNumberTextBoxEditEvent(newPhoneNumber);

        if (errorNumber != -1) {
            ShowAlerts.showAlert(errorNumber);

            // Refresh table view
            customerTableView.setItems(customers);
            return;
        } else {
            // Get index of customer
            int index = customerStringCellEditEvent.getTablePosition().getRow();

            // Update database
            DBCustomers.editPhoneNumberEvent(newPhoneNumber, customers.get(index));

            // Refresh table view
            customers = DBCustomers.getMainScreenCustomerInfo();
            customerTableView.setItems(customers);
        }
    }

    /**
     * This method detects if a customer division id record is being edited on the table view
     *
     * @param customerIntegerCellEditEvent Evenet that is caught to detect cell being edited
     */
    public void divisionIdEditCommit(TableColumn.CellEditEvent<Customer, Integer> customerIntegerCellEditEvent) {
        int newDivisionId = 0;
        // Try catch is to detect if the text box is empty
        try {
            newDivisionId = customerIntegerCellEditEvent.getNewValue();
        } catch (NullPointerException e) {
            ShowAlerts.showAlert(13);
            return;
        }
        // Retrieve error number
        int errorNumber = ErrorChecker.divisionIdTextBoxEvent(newDivisionId);

        if (errorNumber != -1) {
            ShowAlerts.showAlert(errorNumber);

            // Refresh table view
            customerTableView.setItems(customers);
            return;
        } else {
            // Get index of customer
            int index = customerIntegerCellEditEvent.getTablePosition().getRow();

            // Update database
            DBCustomers.editDivisionIdEvent(newDivisionId, customers.get(index));

            // Refresh table view
            customers = DBCustomers.getMainScreenCustomerInfo();
            customerTableView.setItems(customers);
        }
    }
}

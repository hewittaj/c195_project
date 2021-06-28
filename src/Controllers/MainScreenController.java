package Controllers;

import DBAccess.DBAppointments;
import DBAccess.DBCustomers;
import Models.Appointment;
import Models.Customer;
import javafx.collections.FXCollections;
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
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class controls the main screen
 */
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
    @FXML
    public RadioButton allAppointmentsRadioButton;
    @FXML
    public RadioButton monthlyAppointmentsRadioButton;
    @FXML
    public RadioButton weeklyAppointmentsRadioButton;

    public String loggedInUser;  // Currently logged in user

    // List of customers/appointments currently in database
    ObservableList<Customer> customers = DBCustomers.getMainScreenCustomerInfo();
    ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();

    /**
     * This method passes the logged in user between screens
     *
     * @param loggedInUser Logged in user currently logged in
     */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * This method initializes the main screen and the tableviews with associated database data
     *
     * @param url Not used
     * @param rb  Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate our lists
        customers = DBCustomers.getMainScreenCustomerInfo();
        appointments = DBAppointments.getAllAppointments();

        // Initialize customer and appointment tables
        customerTableView.setItems(customers);
        appointmentTableView.setItems(appointments);
        allAppointmentsRadioButton.setSelected(true);

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
     * This method detects whether the modify customer button was pressed and loads the screen
     *
     * @param actionEvent Action event that is caught to detect the button press
     * @throws IOException Exception that is caught to detect IO exception
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
     * This method detects if the add appointment action button was pressed
     *
     * @param actionEvent Event that is caught to detect the button press of add appointment
     * @throws IOException Exception that is caught to detect IO exception
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
     * This method detects if the modify appointment button has been pressed
     *
     * @param actionEvent Event that is caught to detect button press
     * @throws IOException IO exception that is caught in case of IO exception
     */
    public void modifyAppointmentAction(ActionEvent actionEvent) throws IOException {
        Appointment selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            ShowAlerts.showAlert(21);
            return;
        }
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
     * This method detects if the report button has been pressed
     *
     * @param actionEvent Event that is caught to detect button press
     * @throws IOException Exception that is caught to detect IO exception
     */
    public void reportButtonAction(ActionEvent actionEvent) throws IOException {
        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/report_screen.fxml"));

        // Set parent
        Parent mainScreenParent = loader.load();

        // Instantiate controller and call functions to pass info between screens
        ReportScreenController controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        // Set scene
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    /**
     * This method detects if the customer delete button was pressed and handles any errors that may arise
     *
     * @param actionEvent Event that is caught to detect delete button press
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

    /**
     * This method detects if the all appointments radio button was selected and displays the appropriate data
     *
     * @param actionEvent Event that is caught if the radio button was selected
     */
    public void allAppointmentsAction(ActionEvent actionEvent) {
        // Deselect other buttons and clear table view
        monthlyAppointmentsRadioButton.setSelected(false);
        weeklyAppointmentsRadioButton.setSelected(false);
        appointmentTableView.setItems(null);

        // Set table view to contain all appointments
        appointmentTableView.setItems(appointments);
    }

    /**
     * This method detects if the monthly appointments radio button was selected, and displays appropriate data.
     * <p>
     * Lambda expression: In the lambda expression used it checks each appointment in our observable list for
     * whether or not the appointment start and date time is within the month. This simplifies the for loop code
     * and makes it more accessible to the developer.
     *
     * @param actionEvent Event that is caught if the radio button is selected
     */
    public void monthlyAppointmentsAction(ActionEvent actionEvent) {
        // Deselect other buttons and clear table view
        allAppointmentsRadioButton.setSelected(false);
        weeklyAppointmentsRadioButton.setSelected(false);
        appointmentTableView.setItems(null);

        ObservableList<Appointment> onlyThisMonthsAppointments = FXCollections.observableArrayList();
        LocalDate maxDate = YearMonth.from(Instant.now().atZone(ZoneId.systemDefault())).atEndOfMonth();
        LocalDate minDate = YearMonth.from(Instant.now().atZone(ZoneId.systemDefault())).atDay(1);

        // Lambda expression
        appointments.forEach((appointment) -> {
            // If appointment is within the month add it to our observable list
            if (appointment.getStartDateTime().toLocalDate().isBefore(maxDate) &&
                    appointment.getStartDateTime().toLocalDate().isAfter(minDate)) {
                onlyThisMonthsAppointments.add(appointment);
            }
        });

        appointmentTableView.setItems(onlyThisMonthsAppointments);
    }

    /**
     * This method detects if the weekly appointment radio button was selected and displays the appropriate data from
     * the database
     *
     * @param actionEvent Event that is caught to detect if the weekly radio button was selected
     */
    public void weeklyAppointmentsAction(ActionEvent actionEvent) {
        // Deselect other buttons and clear table view
        allAppointmentsRadioButton.setSelected(false);
        monthlyAppointmentsRadioButton.setSelected(false);
        appointmentTableView.setItems(null);

        ObservableList<Appointment> onlyThisWeeksAppointments = FXCollections.observableArrayList();

        // Get start and end of week
        DayOfWeek firstDay = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        LocalDate startOfWeek = LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDay));

        DayOfWeek lastDay = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek().plus(6);
        LocalDate endOfWeek = LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDay));

        System.out.println("start of week: " + startOfWeek + " end of week: " + endOfWeek);
        for (Appointment appointment : appointments) {
            // If appointment is in the current week
            if (appointment.getStartDateTime().toLocalDate().isAfter(startOfWeek) &&
                    appointment.getStartDateTime().toLocalDate().isBefore(endOfWeek)) {
                onlyThisWeeksAppointments.add(appointment);
            }
        }

        appointmentTableView.setItems(onlyThisWeeksAppointments);
    }
}

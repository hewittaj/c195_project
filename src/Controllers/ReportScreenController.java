package Controllers;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import Models.*;
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

public class ReportScreenController implements Initializable {
    // Table Views
    @FXML
    public TableView<MonthAndTypeReport> monthAndTypeOfAppointmentsTableView;
    @FXML
    public TableView<AppointmentsByContact> appointmentsByContactTableView;
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
    public String loggedInUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateContactList();
        populateMonthAndTypeReport();


        // Initialize month and type report column names -> string must match the model's spelling/capitalization
        monthColumn.setCellValueFactory(new PropertyValueFactory<MonthAndTypeReport, String>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<MonthAndTypeReport, String>("type"));
        countColumn.setCellValueFactory(new PropertyValueFactory<MonthAndTypeReport, Integer>("count"));

        // Initialize appointments by contact report column names
        // -> string must match the model's spelling/capitalization
        contactComboBox.setValue(allContacts.get(0));

    }

    public void contactComboBoxSelected(ActionEvent actionEvent) {
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
     * This method populates the month and type report
     */
    public void populateMonthAndTypeReport() {
        monthAndTypeOfAppointmentsTableView.getItems().setAll(monthAndTypeReports);
    }

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

    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

}
package Controllers;

import Models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    public void contactComboBoxSelected(ActionEvent actionEvent) {
    }
}

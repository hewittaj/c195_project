package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentScreenController implements Initializable {

    @FXML public Button saveButton;
    @FXML public Button backButton;
    @FXML public TextField appointmentIDTextField;
    @FXML public TextField userIDTextField;
    @FXML public TextField customerIDTextField;
    @FXML public TextField titleTextField;
    @FXML public TextField descriptionTextField;
    @FXML public TextField locationTextField;
    @FXML public ComboBox contactComboBox;
    @FXML public TextField typeTextField;
    @FXML public ComboBox startHourComboBox;
    @FXML public ComboBox startMinuteComboBox;
    @FXML public ComboBox startAMPMComboBox;
    @FXML public ComboBox endHourComboBox;
    @FXML public ComboBox endMinuteComboBox;
    @FXML public ComboBox endAMPMComboBox;
    @FXML public ComboBox startMonthComboBox;
    @FXML public ComboBox startDayComboBox;
    @FXML public ComboBox startYearComboBox;
    @FXML public ComboBox endMonthComboBox;
    @FXML public ComboBox endDayComboBox;
    @FXML public ComboBox endYearComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void saveButtonAction(ActionEvent actionEvent) {
    }

    public void backButtonAction(ActionEvent actionEvent) {
    }
}

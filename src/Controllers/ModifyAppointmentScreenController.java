package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void saveButtonAction(ActionEvent actionEvent) {
        Timestamp lastUpdatedTime = Timestamp.valueOf(LocalDateTime.now());
    }

    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));

        // Set parent and scene
        Parent mainScreenParent = loader.load();
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    public void monthBoxSelected(ActionEvent actionEvent) {
    }
}

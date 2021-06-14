package Controllers;

import DBAccess.DBAppointments;
import Models.Appointment;
import Models.Contact;
import DBAccess.DBContacts;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentScreenController implements Initializable {

    @FXML public Button saveButton;
    @FXML public Button backButton;
    @FXML public TextField appointmentIdTextField;
    @FXML public TextField customerIDTextField;
    @FXML public TextField titleTextField;
    @FXML public TextField descriptionTextField;
    @FXML public TextField locationTextField;
    @FXML public TextField typeTextField;
    @FXML public ComboBox contactComboBox;
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

    public String loggedInUser;
    public ObservableList<Contact> allContacts = DBContacts.getAllContacts();
    public ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
    public int numberOfAppointments;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        populateContactList();
        populateHourMinuteComboBox();
    }

    public void saveButtonAction(ActionEvent actionEvent) {
        // Set up an alert for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm New Appointment");
        alert.setHeaderText("Are you sure you want to add this appointment?");
        alert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = alert.showAndWait();

        Appointment newAppointment = null;
        // If user accepts prompt
        if(decision.get() == ButtonType.OK){

        }
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

    public int getSizeOfAllCustomers(){
        return 0;
    }

    /**
     * // TO DO FIX FOR APPOINTMENTS
     * Generates the next Id Number for an appointment we want to add
     */
    public void getNextIdNumber(int numberOfAppointments){
        int size = numberOfAppointments; // Set the size
        if(size == 0){
            appointmentIdTextField.setText("1");
        }else{
            try{
                for(int i = 0; i <=size; i++){ // Loop through the list
                    if(i == 0){ // Skip 0 as we don't want an ID of zero
                        continue;
                    }
                    if(allAppointments.get(i).getAppointmentId() == i){ // If customers returned is null
                        continue; // Continue in loop

                    }else if(allAppointments.get(i).getAppointmentId() != i){ // If customers matches we continue in the loop
                        appointmentIdTextField.setText(String.valueOf(Appointment.getLastAppointmentId() + 1));
                    }
                    else{
                        continue;
                    }
                }
            }catch(IndexOutOfBoundsException e){
                appointmentIdTextField.setText(String.valueOf(size + 1));
            }
        }
    }

    /**
     * This method passes the logged in user between screens
     * @param loggedInUser
     */
    public void passLoggedInUser(String loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    /**
     * This method passes the number of appointments between screens
     * @param numberOfAppointments Number of appointments currently in database
     */
    public void passNumberOfAppointments(int numberOfAppointments){
        this.numberOfAppointments = numberOfAppointments;
        getNextIdNumber(numberOfAppointments);
    }

    /**
     * This method populates the contact information in the add appointment screen
     */
    public void populateContactList(){
        for(Contact contact: allContacts){
            contactComboBox.getItems().add(contact);
        }
    }

    public void populateHourMinuteComboBox(){
        for(int i = 1; i < 13; i++){
            startHourComboBox.getItems().add(i);
            endHourComboBox.getItems().add(i);
        }

        for(int i = 1; i < 61; i++){
            if(i % 5 == 0){
                if(i < 10){
                    startMinuteComboBox.getItems().add(String.format("0%s", i));
                    endMinuteComboBox.getItems().add(String.format("0%s", i));
                }
                else{
                    startMinuteComboBox.getItems().add(String.valueOf(i));
                    endMinuteComboBox.getItems().add(String.valueOf(i));
                }
            }

        }

        // Populate AM/PM combo boxes for start and end time
        startAMPMComboBox.getItems().add("AM");
        startAMPMComboBox.getItems().add("PM");

        endAMPMComboBox.getItems().add("AM");
        endAMPMComboBox.getItems().add("PM");
    }
}

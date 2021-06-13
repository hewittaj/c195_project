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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    }

    public void saveButtonAction(ActionEvent actionEvent) {

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
                        customerIDTextField.setText(String.valueOf(Appointment.getLastAppointmentId() + 1));
                    }
                    else{
                        continue;
                    }
                }
            }catch(IndexOutOfBoundsException e){
                customerIDTextField.setText(String.valueOf(size + 1));
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
}

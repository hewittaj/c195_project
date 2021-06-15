package Controllers;

import DBAccess.DBAppointments;
import Models.Appointment;
import Models.Contact;
import DBAccess.DBContacts;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentScreenController implements Initializable {

    @FXML public Button saveButton;
    @FXML public Button backButton;
    @FXML public TextField appointmentIdTextField;
    @FXML public TextField customerIDTextField;
    @FXML public TextField titleTextField;
    @FXML public TextField userIdTextField;
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

    public String loggedInUser;
    public ObservableList<Contact> allContacts = DBContacts.getAllContacts();
    public ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
    public int numberOfAppointments;
    public String startTime;
    public String endTime;
    public String dateInfo;
    public LocalDateTime combinedDateTime;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        // Populate combo boxes
        populateContactList();
        populateHourMinuteComboBox();
        populateDateComboBoxes();


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
            // TO DO check for unfilled out/improper fields filled out

            // Initialize variables
            int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
            int userId = Integer.parseInt(userIdTextField.getText());
            int customerId = Integer.valueOf(customerIDTextField.getText());
            int contactId = DBContacts.getContactIdFromName(
                    contactComboBox.getSelectionModel().getSelectedItem().toString());
            String title;
            String description;
            String location;
            String type;
            formatDateAndTimeStrings();

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
     * This method formats the time string to pass into the database
     */
    public void formatDateAndTimeStrings(){
        // Initialize values
        String startingTime = "";
        String endingTime = "";
        String thisDateInfo = "";
        LocalTime start = null;
        LocalTime end = null;
        LocalDate startDate = null;

        /*
        Convert start time
         */
        // If time is PM we need to add 12 to get military time
        if(startAMPMComboBox.getSelectionModel().getSelectedItem().equals("PM")){
            // Converted time
            int convertedStartTimeToPm = 12 + Integer.parseInt(startHourComboBox.getSelectionModel()
                    .getSelectedItem().toString());

            // Combined start time
            startingTime = convertedStartTimeToPm +  ":" + startMinuteComboBox.getSelectionModel()
                    .getSelectedItem();
        }
        // Else is AM
        else{
            startingTime = startHourComboBox.getSelectionModel().getSelectedItem() + ":"
                    + startMinuteComboBox.getSelectionModel().getSelectedItem();
        }

        // Convert start time to local time and format it, then add to array list.
        start = LocalTime.parse(startingTime);

        /*
        Convert end time
         */
        // If time is PM we need to add 12 to get military time
        if(endAMPMComboBox.getSelectionModel().getSelectedItem().equals("PM")){
            // Converted time
            int convertedEndTimeToPm = 12 + Integer.parseInt(endHourComboBox.getSelectionModel()
                    .getSelectedItem().toString());

            // Combined end time
            endingTime = convertedEndTimeToPm +  ":" + endMinuteComboBox.getSelectionModel()
                    .getSelectedItem();
        }
        // Else is AM
        else{
            endingTime = startHourComboBox.getSelectionModel().getSelectedItem() + ":"
                    + startMinuteComboBox.getSelectionModel().getSelectedItem();
        }
        end = LocalTime.parse(endingTime);

        /*
        Convert date
         */
        thisDateInfo =  startMonthComboBox.getSelectionModel().getSelectedItem().toString() + "/" +
                        startDayComboBox.getSelectionModel().getSelectedItem().toString() + "/" +
                        startYearComboBox.getSelectionModel().getSelectedItem().toString();


        System.out.println("Date: " + thisDateInfo + " Start time: " + start + " End Time: " + end);
    }

    /**
     * This method detects if the month box was selected and repopulates the days based on selected month
     * @param actionEvent Event detected in case of a mouse click
     */
    public void monthBoxSelected(ActionEvent actionEvent) {
        String selectedMonth = ""; // 04, 06, 09, 11 30 days, 02 has 28
        if(startMonthComboBox.getSelectionModel().getSelectedItem().toString().matches("04|06|09|11")){
            // Clear days prepopulated by populateDateTimeBoxes
            startDayComboBox.getItems().clear();

            for(int i = 1; i < 31; i++){
                if(i < 10){
                    startDayComboBox.getItems().add(String.format("0%s", i));
                }
                else{
                    startDayComboBox.getItems().add(String.valueOf(i));
                }
            }
        }
        else if(startMonthComboBox.getSelectionModel().getSelectedItem().toString().matches("02")){
            // Clear days prepopulated by populateDateTimeBoxes
            startDayComboBox.getItems().clear();

            for(int i = 1; i < 29; i++){
                if(i < 10){
                    startDayComboBox.getItems().add(String.format("0%s", i));
                }
                else{
                    startDayComboBox.getItems().add(String.valueOf(i));
                }
            }
        }
        else{
            return;
        }
    }

    /**
     * This method passes the logged in user between screens
     * @param loggedInUser Parameter that is passed from screen to screen with the currently logged in user
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

    /**
     * This method populates the information for the date combo boxes
     */
    public void populateDateComboBoxes(){
        // Populate month boxes
        for(int i = 1; i < 13; i++){
            if(i < 10){
                startMonthComboBox.getItems().add(String.format("0%s", i));
            }
            else{
                startMonthComboBox.getItems().add(String.valueOf(i));
            }
        }

        // Populate day boxes generically to 31 of them
        for(int i = 1; i < 32; i++){
            if(i < 10){
                startDayComboBox.getItems().add(String.format("0%s", i));
            }
            else{
                startDayComboBox.getItems().add(String.valueOf(i));
            }
        }

        // Populate a few years
        for(int i = 2021; i < 2030; i++){
            startYearComboBox.getItems().add(String.valueOf(i));
        }
    }

    /**
     * This method populates the hours/minutes/am or pm combo boxes
     */
    public void populateHourMinuteComboBox(){
        for(int i = 1; i < 13; i++){
            if(i < 10){
                startHourComboBox.getItems().add(String.format("0%s", i));
                endHourComboBox.getItems().add(String.format("0%s", i));
            }
            else{
                startHourComboBox.getItems().add(i);
                endHourComboBox.getItems().add(i);
            }
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

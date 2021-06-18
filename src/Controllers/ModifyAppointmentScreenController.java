package Controllers;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import Models.Appointment;
import Models.Contact;

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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
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

    public LocalTime startTime;
    public LocalTime endTime;
    public LocalDate dateInfo;
    public LocalDateTime combinedStartDateTime;
    public LocalDateTime combinedEndDateTime;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public ObservableList<Contact> allContacts = DBContacts.getAllContacts();

    public String loggedInUser;

    public static Appointment newAppointment;

    // Initialize array lists for error checking
    ArrayList<TextField> idTextFieldsOnly = new ArrayList<>();
    ArrayList<TextField> textFields = new ArrayList<>();
    ArrayList<ComboBox> dateFields = new ArrayList<>();
    ArrayList<ComboBox> startTimeFields = new ArrayList<>();
    ArrayList<ComboBox> endTimeFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        // Populate array list of all controls on screen for error checking
        populateControlArrayLists();

        // Populate date and hour combo boxes
        populateDateComboBoxes();
        populateHourMinuteComboBox();


    }

    public void saveButtonAction(ActionEvent actionEvent) throws IOException {
        Timestamp lastUpdatedTime = Timestamp.valueOf(LocalDateTime.now());

        // Check for errors in the add appointment screen
        int errorNumber = ErrorChecker.validateAppointmentFields(textFields, idTextFieldsOnly, contactComboBox,
                dateFields, startTimeFields, endTimeFields);

        // If error number is not -1 an error was found, otherwise continue code
        if(errorNumber != -1){
            ShowAlerts.showAlert(Integer.valueOf(errorNumber));
            return;
        }

        // Set up an alert for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Modified Appointment");
        alert.setHeaderText("Are you sure you want to update this appointment?");
        alert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = alert.showAndWait();

        Appointment modifiedAppointment = null;

        // If user accepts prompt
        if(decision.get() == ButtonType.OK){
            // Initialize variables
            int appointmentId = Integer.parseInt(appointmentIDTextField.getText());
            int userId = Integer.parseInt(userIDTextField.getText());
            int customerId = Integer.valueOf(customerIDTextField.getText());
            int contactId = DBContacts.getContactIdFromName(
                    contactComboBox.getSelectionModel().getSelectedItem().toString());
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            formatDateAndTimeStrings();
            modifiedAppointment = new Appointment(appointmentId, userId, customerId, contactId, title, description, location,
                    type, combinedStartDateTime, combinedEndDateTime, loggedInUser);
            DBAppointments.updateAppointment(modifiedAppointment);

            // Load main screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));

            // Set parent and scene
            Parent mainScreenParent = loader.load();

            // Instantiate controller and call functions to pass info between screens
            MainScreenController controller = loader.getController();
            controller.passLoggedInUser(loggedInUser);
            Scene mainScreenScene = new Scene(mainScreenParent);

            // This line gets the Stage information
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
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

    //TODO check to make sure an appointment is selected and passed
    //TODO change method to fit for appointment, not customer
    /**
     * This method passes the selected appointment
     * @param appointment
     */
    public void passAppointment(Appointment appointment){
        // Initialize important variables for retrieving data
        this.newAppointment = appointment;

        // Initialize text fields to currently selected customer values
        appointmentIDTextField.setText(String.valueOf(newAppointment.getAppointmentId()));
        userIDTextField.setText(String.valueOf(newAppointment.getUserId()));
        customerIDTextField.setText(String.valueOf(newAppointment.getCustomerId()));
        titleTextField.setText(newAppointment.getTitle());
        descriptionTextField.setText(newAppointment.getDescription());
        locationTextField.setText(newAppointment.getLocation());
        typeTextField.setText(newAppointment.getType());

        // Fill contact combo box and set predefined value
        populateContactList();
        contactComboBox.setValue(DBContacts.getSpecificContact(appointment.getContactId()));

        // Fill date combo boxes starting with the month
        int appointmentMonth = appointment.getDate().getMonthValue(); // Integer value of the month
        String appointmentMonthString = "";                           // String to store the appointment month

        if(appointmentMonth < 10){
            appointmentMonthString = String.format("0%s", appointmentMonth);
        }
        else{
            appointmentMonthString = String.valueOf(appointmentMonth);
        }

        // Format day
        int appointmentDay = appointment.getDate().getDayOfMonth();
        String appointmentDayString = "";
        if(appointmentDay < 10){
            appointmentDayString = String.format("0%s", appointmentDay);
        }
        else{
            appointmentDayString = String.valueOf(appointmentDay);
        }

        // Format year
        String appointmentYear = String.format("%s", appointment.getDate().getYear());

        // Set appointment date values
        startMonthComboBox.setValue(appointmentMonthString);
        startDayComboBox.setValue(appointmentDayString);
        startYearComboBox.setValue(appointmentYear);

        // Set starting time info
        int startingHour = appointment.getStartingTime().getHour();
        if(startingHour < 10){
            startHourComboBox.setValue(String.format("0%s", startingHour));
        }
        else if(startingHour > 12){
            startHourComboBox.setValue(String.valueOf(startingHour - 12));
        }
        else{
            startHourComboBox.setValue(String.valueOf(startingHour));
        }

        int startingMinute = appointment.getStartingTime().getMinute();
        if(startingMinute < 10){
            startMinuteComboBox.setValue(String.format("0%s", startingMinute));
        }
        else{
            startMinuteComboBox.setValue(String.valueOf(startingMinute));
        }

        if(appointment.getStartingTime().isBefore(LocalTime.NOON)){
            startAMPMComboBox.setValue("AM");
        }
        else{
            startAMPMComboBox.setValue("PM");
        }

        // Set ending time info
        int endingHour = appointment.getEndingTime().getHour();
        if(endingHour < 12){
            endHourComboBox.setValue(String.format("0%s", endingHour));
        }
        else if(endingHour > 12){
            endHourComboBox.setValue(String.valueOf(endingHour - 12));
        }
        else{
            endHourComboBox.setValue(String.valueOf(endingHour));
        }

        int endingMinute = appointment.getEndingTime().getMinute();
        if(endingMinute < 10){
            endMinuteComboBox.setValue(String.format("0%s", endingMinute));
        }
        else{
            endMinuteComboBox.setValue(String.valueOf(endingMinute));
        }

        if(appointment.getEndingTime().isBefore(LocalTime.NOON)){
            endAMPMComboBox.setValue("AM");
        }
        else{
            endAMPMComboBox.setValue("PM");
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
     * This method populates the array lists containing all of the text boxes, combo boxes, etc.
     * Used in order to do error checks
     */
    public void populateControlArrayLists(){

        // Add text fields to associated array list
        textFields.add(appointmentIDTextField);
        textFields.add(customerIDTextField);
        textFields.add(titleTextField);
        textFields.add(userIDTextField);
        textFields.add(descriptionTextField);
        textFields.add(locationTextField);
        textFields.add(typeTextField);

        idTextFieldsOnly.add(customerIDTextField);
        idTextFieldsOnly.add(userIDTextField);

        // Add combo boxes to associated array lists starting with date fields
        dateFields.add(startMonthComboBox);
        dateFields.add(startDayComboBox);
        dateFields.add(startYearComboBox);

        // Starting appointment time fields
        startTimeFields.add(startHourComboBox);
        startTimeFields.add(startMinuteComboBox);
        startTimeFields.add(startAMPMComboBox);

        //Ending appointment time fields
        endTimeFields.add(endHourComboBox);
        endTimeFields.add(endMinuteComboBox);
        endTimeFields.add(endAMPMComboBox);
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

        // Initialize lists of user interface controls to error check proper information/not null

        /*
        Convert start time
         */
        int convertedStartTimeToPm = 0;
        // If time is AM we need to add 12 to get military time
        if(startAMPMComboBox.getSelectionModel().getSelectedItem().equals("AM")){
            // Converted time
            String selectedHour = startHourComboBox.getSelectionModel().getSelectedItem().toString();
            if(String.valueOf(selectedHour).equals("12")){
                // Combined start time
                startingTime = "00:" + startMinuteComboBox.getSelectionModel()
                        .getSelectedItem();
            }
            else{
                // Combined start time
                startingTime = startHourComboBox.getSelectionModel().getSelectedItem() +
                        ":" + startMinuteComboBox.getSelectionModel().getSelectedItem();
            }
        }

        // Else is PM
        else{
            convertedStartTimeToPm = 12 + Integer.parseInt(startHourComboBox.getSelectionModel()
                    .getSelectedItem().toString());
            startingTime = convertedStartTimeToPm + ":"
                    + startMinuteComboBox.getSelectionModel().getSelectedItem();
        }

        // Convert start time to local time and format it, then add to array list.
        start = LocalTime.parse(startingTime);

        /*
        Convert end time
         */
        // If time is AM we need to add 12 to get military time
        if(endAMPMComboBox.getSelectionModel().getSelectedItem().equals("AM")){
            String selectedEndHour = endHourComboBox.getSelectionModel().getSelectedItem().toString();
            if(String.valueOf(selectedEndHour).equals("12")){
                endingTime = "00:" + endMinuteComboBox.getSelectionModel().getSelectedItem();
            }
            else{
                // Combined end time
                endingTime = endHourComboBox.getSelectionModel().getSelectedItem()
                        +  ":" + endMinuteComboBox.getSelectionModel().getSelectedItem();
            }
        }
        // Else is PM
        else{
            int convertedEndTimeToPm = 12 + Integer.parseInt(endHourComboBox.getSelectionModel()
                    .getSelectedItem().toString());
            endingTime = convertedEndTimeToPm + ":"
                    + endMinuteComboBox.getSelectionModel().getSelectedItem();
        }
        end = LocalTime.parse(endingTime);

        /*
        Convert date yyyy-MM-dd
         */
        thisDateInfo =  startYearComboBox.getSelectionModel().getSelectedItem().toString() + "-" +
                startMonthComboBox.getSelectionModel().getSelectedItem().toString() + "-" +
                startDayComboBox.getSelectionModel().getSelectedItem().toString();

        startDate = LocalDate.parse(thisDateInfo);
        startTime = start;
        endTime = end;
        dateInfo = startDate;
        combinedStartDateTime = LocalDateTime.parse(dateInfo + " " + startTime, formatter);
        combinedEndDateTime = LocalDateTime.parse(dateInfo + " " + endTime, formatter);
//        System.out.println(combinedStartDateTime); TODO DELETE
//
//        System.out.println("Date: " + startDate + " Start time: " + start + " End Time: " + end);
    }

    /**
     * This method detects if the month box was selected and repopulates the days based on selected month
     * @param actionEvent Event detected in case of a mouse click
     */
    public void monthBoxSelected(ActionEvent actionEvent) {
        String selectedMonth = ""; // 04, 06, 09, 11 30 days, 02 has 28
        if(startMonthComboBox.getSelectionModel().getSelectedItem().toString().matches("04|06|09|11")){
            // Clear days pre-populated by populateDateTimeBoxes
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
            // Clear days pre-populated by populateDateTimeBoxes
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
        // Special case for 0 in the minutes box
        startMinuteComboBox.getItems().add("00");
        endMinuteComboBox.getItems().add("00");

        for(int i = 1; i < 56; i++){
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

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
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentScreenController implements Initializable {

    @FXML
    public Button saveButton;
    @FXML
    public Button backButton;
    @FXML
    public TextField appointmentIdTextField;
    @FXML
    public TextField customerIDTextField;
    @FXML
    public TextField titleTextField;
    @FXML
    public TextField userIdTextField;
    @FXML
    public TextField descriptionTextField;
    @FXML
    public TextField locationTextField;
    @FXML
    public TextField typeTextField;
    @FXML
    public ComboBox contactComboBox;
    @FXML
    public ComboBox startHourComboBox;
    @FXML
    public ComboBox startMinuteComboBox;
    @FXML
    public ComboBox startAMPMComboBox;
    @FXML
    public ComboBox endHourComboBox;
    @FXML
    public ComboBox endMinuteComboBox;
    @FXML
    public ComboBox endAMPMComboBox;
    @FXML
    public ComboBox startMonthComboBox;
    @FXML
    public ComboBox startDayComboBox;
    @FXML
    public ComboBox startYearComboBox;

    // Initialize variables
    public String loggedInUser;
    public ObservableList<Contact> allContacts = DBContacts.getAllContacts();
    public ObservableList<Appointment> allAppointments = DBAppointments.getAllAppointments();
    public int numberOfAppointments;
    public LocalTime startTime;
    public LocalTime endTime;
    public LocalDate dateInfo;
    public LocalDateTime combinedStartDateTime;
    public LocalDateTime combinedEndDateTime;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Array list of controls on screen for error checking
    ArrayList<TextField> idTextFieldsOnly = new ArrayList<>();
    ArrayList<TextField> textFields = new ArrayList<>();
    ArrayList<ComboBox> dateFields = new ArrayList<>();
    ArrayList<ComboBox> startTimeFields = new ArrayList<>();
    ArrayList<ComboBox> endTimeFields = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate array list of controls on screen
        populateControlArrayLists();

        // Populate combo boxes
        populateContactList();
        populateHourMinuteComboBox();
        populateDateComboBoxes();

    }

    public void saveButtonAction(ActionEvent actionEvent) throws IOException {

        // Check for errors in the add appointment screen
        int errorNumber = ErrorChecker.validateAppointmentFields(textFields, idTextFieldsOnly, contactComboBox,
                dateFields, startTimeFields, endTimeFields);

        // If error number is not -1 an error was found, otherwise continue code
        if (errorNumber != -1) {
            ShowAlerts.showAlert(Integer.valueOf(errorNumber));
            return;
        }

        // Set up an alert for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm New Appointment");
        alert.setHeaderText("Are you sure you want to add this appointment?");
        alert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = alert.showAndWait();

        Appointment newAppointment = null;

        // If user accepts prompt
        if (decision.get() == ButtonType.OK) {
            // Initialize variables
            int appointmentId = Integer.parseInt(appointmentIdTextField.getText());
            int userId = Integer.parseInt(userIdTextField.getText());
            int customerId = Integer.valueOf(customerIDTextField.getText());
            int contactId = DBContacts.getContactIdFromName(
                    contactComboBox.getSelectionModel().getSelectedItem().toString());
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            formatDateAndTimeStrings();
            newAppointment = new Appointment(appointmentId, userId, customerId, contactId, title, description, location,
                    type, combinedStartDateTime, combinedEndDateTime, loggedInUser);
            DBAppointments.addAppointment(newAppointment);

            // Load main screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));

            // Set parent and scene
            Parent mainScreenParent = loader.load();

            // Instantiate controller and call functions to pass info between screens
            MainScreenController controller = loader.getController();
            controller.passLoggedInUser(loggedInUser);
            Scene mainScreenScene = new Scene(mainScreenParent);

            // This line gets the Stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        }
    }

    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        //TODO test and delete
        formatDateAndTimeStrings();
        validateDateTimeInput();
        // Load main screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));

        // Set parent and scene
        Parent mainScreenParent = loader.load();

        // Instantiate controller and call functions to pass info between screens
        MainScreenController controller = loader.getController();
        controller.passLoggedInUser(loggedInUser);
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
    }

    public int getSizeOfAllCustomers() {
        return 0;
    }

    /**
     * This method generates next id number for the new appointment
     *
     * @param numberOfAppointments Parameter passed that tells us how many appointments there currently are in the db
     */
    public void getNextIdNumber(int numberOfAppointments) {
        int size = numberOfAppointments; // Set the size
        int i = 1; // Iterator

        // If no appointments in database
        if (size == 0) {
            appointmentIdTextField.setText("1");
        } else {
            for (Appointment appointment : allAppointments) {
                if (appointment.getAppointmentId() == i) {
                    if (i == size) {
                        appointmentIdTextField.setText(String.valueOf(i + 1));
                    }
                    i += 1;
                    continue;
                } else {
                    appointmentIdTextField.setText(String.valueOf(i));
                    break;
                }
            }
        }
    }

    /**
     * This method formats the time string to pass into the database
     */
    public void formatDateAndTimeStrings() {
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
        if (startAMPMComboBox.getSelectionModel().getSelectedItem().equals("AM")) {
            // Converted time
            String selectedHour = startHourComboBox.getSelectionModel().getSelectedItem().toString();
            if (String.valueOf(selectedHour).equals("12")) {
                // Combined start time
                startingTime = "00:" + startMinuteComboBox.getSelectionModel()
                        .getSelectedItem();
            } else {
                // Combined start time
                startingTime = startHourComboBox.getSelectionModel().getSelectedItem() +
                        ":" + startMinuteComboBox.getSelectionModel().getSelectedItem();
            }
        }

        // Else is PM
        else {
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
        if (endAMPMComboBox.getSelectionModel().getSelectedItem().equals("AM")) {
            String selectedEndHour = endHourComboBox.getSelectionModel().getSelectedItem().toString();
            if (String.valueOf(selectedEndHour).equals("12")) {
                endingTime = "00:" + endMinuteComboBox.getSelectionModel().getSelectedItem();
            } else {
                // Combined end time
                endingTime = endHourComboBox.getSelectionModel().getSelectedItem()
                        + ":" + endMinuteComboBox.getSelectionModel().getSelectedItem();
            }
        }
        // Else is PM
        else {
            int convertedEndTimeToPm = 12 + Integer.parseInt(endHourComboBox.getSelectionModel()
                    .getSelectedItem().toString());
            endingTime = convertedEndTimeToPm + ":"
                    + endMinuteComboBox.getSelectionModel().getSelectedItem();
        }
        end = LocalTime.parse(endingTime);

        /*
        Convert date yyyy-MM-dd
         */
        thisDateInfo = startYearComboBox.getSelectionModel().getSelectedItem().toString() + "-" +
                startMonthComboBox.getSelectionModel().getSelectedItem().toString() + "-" +
                startDayComboBox.getSelectionModel().getSelectedItem().toString();

        startDate = LocalDate.parse(thisDateInfo);
        startTime = start;
        endTime = end;
        dateInfo = startDate;
        combinedStartDateTime = LocalDateTime.parse(dateInfo + " " + startTime, formatter);
        combinedEndDateTime = LocalDateTime.parse(dateInfo + " " + endTime, formatter);
    }

    /**
     * This method detects if the month box was selected and repopulates the days based on selected month
     *
     * @param actionEvent Event detected in case of a mouse click
     */
    public void monthBoxSelected(ActionEvent actionEvent) {
        String selectedMonth = ""; // 04, 06, 09, 11 30 days, 02 has 28
        if (startMonthComboBox.getSelectionModel().getSelectedItem().toString().matches("04|06|09|11")) {
            // Clear days pre-populated by populateDateTimeBoxes
            startDayComboBox.getItems().clear();

            for (int i = 1; i < 31; i++) {
                if (i < 10) {
                    startDayComboBox.getItems().add(String.format("0%s", i));
                } else {
                    startDayComboBox.getItems().add(String.valueOf(i));
                }
            }
        } else if (startMonthComboBox.getSelectionModel().getSelectedItem().toString().matches("02")) {
            // Clear days prepopulated by populateDateTimeBoxes
            startDayComboBox.getItems().clear();

            for (int i = 1; i < 29; i++) {
                if (i < 10) {
                    startDayComboBox.getItems().add(String.format("0%s", i));
                } else {
                    startDayComboBox.getItems().add(String.valueOf(i));
                }
            }
        } else {
            return;
        }
    }

    /**
     * This method passes the logged in user between screens
     *
     * @param loggedInUser Parameter that is passed from screen to screen with the currently logged in user
     */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * This method passes the number of appointments between screens
     *
     * @param numberOfAppointments Number of appointments currently in database
     */
    public void passNumberOfAppointments(int numberOfAppointments) {
        this.numberOfAppointments = numberOfAppointments;
        getNextIdNumber(numberOfAppointments);
    }

    /**
     * This method populates the array lists containing all of the text boxes, combo boxes, etc.
     * Used in order to do error checks
     */
    public void populateControlArrayLists() {

        // Add text fields to associated array list
        textFields.add(appointmentIdTextField);
        textFields.add(customerIDTextField);
        textFields.add(titleTextField);
        textFields.add(userIdTextField);
        textFields.add(descriptionTextField);
        textFields.add(locationTextField);
        textFields.add(typeTextField);

        idTextFieldsOnly.add(customerIDTextField);
        idTextFieldsOnly.add(userIdTextField);

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
    public void populateContactList() {
        for (Contact contact : allContacts) {
            contactComboBox.getItems().add(contact);
        }
    }

    /**
     * This method populates the information for the date combo boxes
     */
    public void populateDateComboBoxes() {
        // Populate month boxes
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                startMonthComboBox.getItems().add(String.format("0%s", i));
            } else {
                startMonthComboBox.getItems().add(String.valueOf(i));
            }
        }

        // Populate day boxes generically to 31 of them
        for (int i = 1; i < 32; i++) {
            if (i < 10) {
                startDayComboBox.getItems().add(String.format("0%s", i));
            } else {
                startDayComboBox.getItems().add(String.valueOf(i));
            }
        }

        // Populate a few years
        for (int i = 2021; i < 2030; i++) {
            startYearComboBox.getItems().add(String.valueOf(i));
        }
    }

    /**
     * This method populates the hours/minutes/am or pm combo boxes
     */
    public void populateHourMinuteComboBox() {
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                startHourComboBox.getItems().add(String.format("0%s", i));
                endHourComboBox.getItems().add(String.format("0%s", i));
            } else {
                startHourComboBox.getItems().add(i);
                endHourComboBox.getItems().add(i);
            }
        }
        // Special case for 0 in the minutes box
        startMinuteComboBox.getItems().add("00");
        endMinuteComboBox.getItems().add("00");

        for (int i = 1; i < 56; i++) {
            if (i % 5 == 0) {
                if (i < 10) {
                    startMinuteComboBox.getItems().add(String.format("0%s", i));
                    endMinuteComboBox.getItems().add(String.format("0%s", i));
                } else {
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

    /**
     * This method validates the date and time inputs for any errors
     */
    public void validateDateTimeInput(){
        // Initialize date times for error checking
        // Start initializing business hours
        LocalDateTime startBusinessHours = LocalDateTime.of(2021, 6, 21, 9, 00);
        LocalDateTime endBusinessHours = LocalDateTime.of(2021, 6, 21, 22, 00);
        ZonedDateTime zonedStart = startBusinessHours.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedEnd = endBusinessHours.atZone(ZoneId.systemDefault());
        ZonedDateTime convertedZonedStart = zonedStart.withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime convertedZonedEnd = zonedEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime startBizHours = convertedZonedStart.toLocalTime();
        LocalTime endBizHours = convertedZonedEnd.toLocalTime();
        // End initializing business hours

        System.out.println("Start biz: " + startBizHours + " end biz: " + endBizHours);


        LocalDateTime originStartDateTime = combinedStartDateTime; // Origin start date time from combo boxes
        LocalDateTime targetZonedStartDateTime; // Once fully converted this is the new start zoned date time
        ZonedDateTime zonedStartDateTime; // Zoned start date time at zone id
        ZonedDateTime convertedZonedStartDateTime; // Converted zoned date time to EST

        LocalDateTime originEndDateTime = combinedEndDateTime;// Origin end date time from combo boxes
        LocalDateTime targetZonedEndDateTime; // Once fully converted this is the new end zoned date time
        ZonedDateTime zonedEndDateTime; // Zoned end date time at zone id
        ZonedDateTime convertedZonedEndDateTime; // Converted zoned date time to EST

        // Convert to zoned date time at origin id
        zonedStartDateTime = originStartDateTime.atZone(ZoneId.systemDefault());
        zonedEndDateTime = originEndDateTime.atZone(ZoneId.systemDefault());

        // Convert zoned origin date time to destination date time which is EST
        convertedZonedStartDateTime = zonedStartDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        convertedZonedEndDateTime = zonedEndDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        targetZonedStartDateTime = convertedZonedStartDateTime.toLocalDateTime();
        targetZonedEndDateTime = convertedZonedEndDateTime.toLocalDateTime();

        // Check for errors on start/end time being before one another
        if(targetZonedStartDateTime.isAfter(targetZonedEndDateTime)){
            // show error
        }
        else if(targetZonedEndDateTime.isBefore(targetZonedStartDateTime)){
            // show error
        }
        else{
            // is fine
        }

        // Local times of the converted zoned date times to check business hours
        LocalTime zonedStartTimeOnly = targetZonedStartDateTime.toLocalTime();
        LocalTime zonedEndTimeOnly = targetZonedEndDateTime.toLocalTime();

        // Check that appointment meets EST meeting time needs
        if(zonedStartTimeOnly.isBefore(startBizHours)){
            System.out.println("Before 9 am est");
        }

        if(zonedEndTimeOnly.isBefore(endBizHours)){
            System.out.println("After 10 pm EST");
        }

        /*
        System.out.println("Origin Start: " + originStartDateTime + " Origin End: " + originEndDateTime);
        System.out.println("Zoned Start: " + convertedZonedStartDateTime +
                " Zoned End: " + convertedZonedEndDateTime);
         */
    }
}

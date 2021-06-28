package Controllers;

import DBAccess.DBAppointments;
import Models.Appointment;
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class controls the login screen
 */
public class LoginScreenController implements Initializable {
    @FXML
    public TextField userIdTextField;
    @FXML
    public Label userLocationLabel;
    @FXML
    public Button loginButton;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Label userIdLabel; // Label that is before user id text field
    @FXML
    public Label passwordLabel; // Label that is before password text field
    @FXML
    public Label loginScreenTitleLabel; // Label that identifies the login screen title
    @FXML
    public Label locationLabel;  // Label that is before the location identifier label

    public ObservableList<Appointment> appointments = DBAppointments.getAllAppointments();
    public String username = "";

    /**
     * This method initializes the login screen
     *
     * @param url Not used
     * @param rb  Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Test for french by using Locale.setDefault(new Locale("fr")); rather than rebooting
        //Locale.setDefault(new Locale("fr", "FR"));

        if (Locale.getDefault().equals(Locale.FRANCE)) {
            // Initialize fields to french language
            userIdLabel.setText("Identifiant d'utilisateur");
            userIdTextField.setPromptText("Identifiant d'utilisateur");
            loginButton.setText("connexion");
            passwordLabel.setText("Le mot de passe\n");
            passwordField.setPromptText("Le mot de passe\n");
            loginScreenTitleLabel.setText("Planificateur de rendez-vous WGU\n");
            locationLabel.setText("Emplacement");
            userIdLabel.setLayoutX(80);
            passwordLabel.setLayoutX(80);
        }
        // Set zoneID
        userLocationLabel.setText(ZoneId.systemDefault().toString());
    }

    /**
     * This method detects if the login button has been pressed and moves to the main screen if valid credentials are
     * supplied
     *
     * @param actionEvent Event that is caught to detect if the login button was pressed
     * @throws IOException Exception that is caught to detect IO exception
     */
    public void loginButtonAction(ActionEvent actionEvent) throws IOException {
        /*
        Use google translate in java
        https://stackoverflow.com/questions/8147284/how-to-use-google-translate-api-in-my-java-application
         */
        // Get the text from the login text fields
        String username = userIdTextField.getText();
        String password = passwordField.getText();

        passUsername(username);
        logLoginAttempt();

        // Boolean created to detect if the username/password combo is valid or not
        boolean validLogin = ErrorChecker.validateLogin(username, password);

        // If a valid login we load the main screen
        if (validLogin == true) {
            // Check if there is an appointment within 15 minutes of local users time
            appointmentInFifteenMinutesAlert();

            // Load next screen and set controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));

            // Set parent and scene
            Parent mainScreenParent = loader.load();

            // Instantiate new controller and call function to pass logged in user
            MainScreenController controller = loader.getController();
            controller.passLoggedInUser(username);

            // Instantiate Screen
            Scene mainScreenScene = new Scene(mainScreenParent);

            // This line gets the Stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainScreenScene);
            window.show();
        }
        // Else we alert the user it was an invalid login and return to the same login screen
        else {
            if (Locale.getDefault().equals(Locale.FRANCE)) {
                Alert invalidLogin = new Alert(Alert.AlertType.ERROR);
                invalidLogin.setTitle("Erreur!");
                invalidLogin.setHeaderText("Nom d'utilisateur / mot de passe invalide!");
                invalidLogin.setContentText("Informations d'identification incorrectes!");
                invalidLogin.showAndWait();
                return;
            } else {
                Alert invalidLogin = new Alert(Alert.AlertType.ERROR);
                invalidLogin.setTitle("ERROR!");
                invalidLogin.setHeaderText("Invalid Username/Password!");
                invalidLogin.setContentText("Incorrect credentials!");
                invalidLogin.showAndWait();
                return;
            }
        }
    }

    /**
     * Passes username of user trying to logged in
     *
     * @param username Username that we pass from what the user supplied, to other parts of the program that otherwise
     *                 wouldn't have access to it
     */
    public void passUsername(String username) {
        this.username = username;
    }

    /**
     * This method displays an alert to the user if there is an appointment within 15 minutes after logging in
     */
    public void appointmentInFifteenMinutesAlert() {
        boolean appointmentSoon = false;
        for (Appointment appointment : appointments) {
            LocalDateTime start = appointment.getStartDateTime();
            // If appointment is within 15 minutes.
            if (start.isAfter(LocalDateTime.now()) && start.isBefore(LocalDateTime.now().plusMinutes(15))
                    && appointment.getLoggedInUser().equals(username)) {
                // Set up an alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Appointment Soon!");
                alert.setHeaderText("Appointment id: " + appointment.getAppointmentId() +
                        "\nDate and time: " + start +
                        "\nAppointment occurring in 15 minutes or less.");
                alert.setContentText("Click 'OK' to confirm.");
                alert.showAndWait();
                appointmentSoon = true;
                break;
            }
        }
        if (appointmentSoon != true) {
            ShowAlerts.showAlert(22);
        }
    }

    /**
     * This method logs a login attempt of a user with the attempted username and login date
     * Path "filePath" is relative to a macbook operating system, but should work for Windows as well
     */
    public void logLoginAttempt() throws IOException {
        // Get timestamp of current date and time
        LocalDateTime timestamp = LocalDateTime.now();

        // Get current path up to the idea src file
        Path currentPath = Paths.get("src");
        Path filePath = Path.of(currentPath + "/login_activity.txt");

        // Boolean value representing if file exists, true = yes, false = no
        boolean fileExists = Files.exists(filePath);

        // If the file exists
        if (fileExists) {
            Writer output = new BufferedWriter(new FileWriter(String.valueOf(filePath), true));
            String loginAttemptInfo = "\nUsername: " + username + "\tTimestamp: " + timestamp;
            output.append(loginAttemptInfo);
            output.close();
        } else {
            // Otherwise file doesn't exist
            String loginAttemptInfo = "Username: " + username + "\tTimestamp: " + timestamp;

            // Create reference to new file
            Path newFile = Files.createFile(currentPath.resolve("login_activity.txt"));
            Files.writeString(newFile, loginAttemptInfo);
        }
    }
}

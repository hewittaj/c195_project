package Controllers;

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
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

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

    // public TableColumn idCol; TO DO Delete
    // public TableColumn nameCol;
    // public TableView dataTable;


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

    public void loginButtonAction(ActionEvent actionEvent) throws IOException {
        /*
        Use google translate in java
        https://stackoverflow.com/questions/8147284/how-to-use-google-translate-api-in-my-java-application
         */
        // Get the text from the login text fields
        String username = userIdTextField.getText();
        String password = passwordField.getText();

        // Boolean created to detect if the username/password combo is valid or not
        boolean validLogin = ErrorChecker.validateLogin(username, password);

        // If a valid login we load the main screen
        if (validLogin == true) {
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
}

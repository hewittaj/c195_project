package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

import Controllers.ErrorChecker;

public class LoginScreenController implements Initializable{
    @FXML public TextField userIdTextField;
    @FXML public Label userLocationLabel;
    @FXML public Button loginButton;
    @FXML public PasswordField passwordField;

    // public TableColumn idCol; TO DO Delete
    // public TableColumn nameCol;
    // public TableView dataTable;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        // Test for french by using Locale.setDefault(new Locale("fr")); rather than rebooting

        // Set zoneID
        userLocationLabel.setText(ZoneId.systemDefault().toString());
    }

    public void loginButtonAction(ActionEvent actionEvent) throws IOException {
        // Get the text from the login text fields
        String username = userIdTextField.getText();
        String password = passwordField.getText();

        // Boolean created to detect if the username/password combo is valid or not
        boolean validLogin = ErrorChecker.validateLogin(username, password);

        // If a valid login we load the main screen
        if(validLogin == true){
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
        // Else we alert the user it was an invalid login and return to the same login screen
        else{
            Alert invalidLogin = new Alert(Alert.AlertType.ERROR);
            invalidLogin.setTitle("ERROR!");
            invalidLogin.setHeaderText("Invalid Username/Password!");
            invalidLogin.setContentText("Incorrect credentials!");
            invalidLogin.showAndWait();
            return;
        }

    }
}

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


    }

    public void loginButtonAction(ActionEvent actionEvent) throws IOException {
        String username = userIdTextField.getText();
        String password = passwordField.getText();
        boolean validLogin = ErrorChecker.validateLogin(username, password);
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

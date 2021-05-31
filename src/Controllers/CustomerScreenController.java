package Controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CustomerScreenController implements Initializable {

    @FXML public RadioButton addCustomerRadioButton;
    @FXML public RadioButton updateCustomerRadioButton;
    @FXML public TextField customerNameTextField;
    @FXML public TextField zipTextField;
    @FXML public TextField addressTextField;
    @FXML public TextField phoneTextField;
    @FXML public TableView customerTableView;
    @FXML public Button deleteCustomerButton;
    @FXML public Button confirmCustomerButton;
    @FXML public ComboBox countryComboBox;
    @FXML public ComboBox divisionComboBox;

    @Override
    public void initialize(URL url, ResourceBundle rb){


    }


    public void confirmButtonAction(ActionEvent actionEvent) {
        // Set up an alert
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("TEST");
        cancelAlert.setHeaderText("Are you sure you want to TEST?");
        cancelAlert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = cancelAlert.showAndWait();
    }
}

package Controllers;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLevelDivisions;
import Models.Country;
import Models.Customer;
import Models.Division;
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

public class ModifyCustomerScreenController implements Initializable {

    public static Customer customer; // Customer info of customer to modify
    private final ObservableList<Country> countries = DBCountries.getAllCountries();
    @FXML
    public TextField customerIDTextField;
    @FXML
    public TextField customerNameTextField;
    @FXML
    public TextField zipTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField phoneTextField;
    @FXML
    public Button confirmCustomerButton;
    @FXML
    public ComboBox countryComboBox;
    @FXML
    public ComboBox divisionComboBox;
    @FXML
    public Button backButton;
    public String loggedInUser;
    private ObservableList<Division> divisions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    /**
     * This method confirms the user modification and updates the database
     *
     * @param actionEvent Event that is caught to detect button press
     * @throws IOException Exception that is caught in case of IOException
     */
    public void confirmButtonAction(ActionEvent actionEvent) throws IOException {
        // Set up an alert for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm New Customer");
        alert.setHeaderText("Are you sure you want to modify this customer?");
        alert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = alert.showAndWait();

        // Initialize an empty customer to pull data into
        Customer updatedCustomer = null;

        // If user accepts the prompt
        if (decision.get() == ButtonType.OK) {
            int customerId = Integer.parseInt(customerIDTextField.getText());
            String customerName = customerNameTextField.getText();
            String zipCode = zipTextField.getText();
            String customerAddress = addressTextField.getText();
            String phoneNumber = phoneTextField.getText();
            String customerCountry = countryComboBox.getSelectionModel().getSelectedItem().toString();
            String customerDivision = divisionComboBox.getSelectionModel().getSelectedItem().toString();
            int customerDivisionId = 0;

            // Get customer division id so we can add proper info to database
            for (Division division : divisions) {
                if (division.getDivisionName().equals(customerDivision)) {
                    customerDivisionId = division.getDivisionId();
                    break;
                }
            }

            // Initialize new customer and call database operations
            updatedCustomer = new Customer(customerId, customerName, customerAddress, zipCode, phoneNumber, customerCountry,
                    customerDivisionId, loggedInUser);
            DBCustomers.updateCustomer(updatedCustomer);
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
        } else {
            return;
        }


    }

    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        // Set up an alert for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Are you sure you want to go back?");
        alert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = alert.showAndWait();
        if (decision.get() == ButtonType.OK) {
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
        } else {
            return;
        }

    }

    /**
     * This method fills the Country Combo box for the add customer controller
     */
    public void fillCountryComboBox() {
        for (Country country : countries) {
            countryComboBox.getItems().add(country);
        }
    }

    /**
     * This method fills the division combo box based off what country is selected
     */
    public void fillDivisionComboBox() {
        divisionComboBox.getItems().clear();
        Country selectedCountry = (Country) countryComboBox.getSelectionModel().getSelectedItem();
        int countryId = selectedCountry.getId();
        divisions = DBFirstLevelDivisions.getFirstLevelDivisionInfo(countryId);
        for (Division division : divisions) {
            divisionComboBox.getItems().add(division.toString());
        }

    }

    public void passCustomer(Customer customer) {
        // Initialize important variables for retrieving data
        ModifyCustomerScreenController.customer = customer;
        Country preAssociatedCountry = DBCountries.getSpecificCountry(ModifyCustomerScreenController.customer.getDivisionId());
        int divisionId = ModifyCustomerScreenController.customer.getDivisionId();

        // Initialize text fields to currently selected customer values
        customerIDTextField.setText(String.valueOf(ModifyCustomerScreenController.customer.getCustomerId()));
        customerNameTextField.setText(ModifyCustomerScreenController.customer.getCustomerName());
        addressTextField.setText(ModifyCustomerScreenController.customer.getCustomerAddress());
        zipTextField.setText(ModifyCustomerScreenController.customer.getZipCode());
        phoneTextField.setText(ModifyCustomerScreenController.customer.getPhoneNumber());

        // Fill country combo box and set predefined value
        fillCountryComboBox();
        countryComboBox.setValue(preAssociatedCountry);

        // Fill division combo box and set predefined value
        fillDivisionComboBox();
        divisionComboBox.setValue(DBFirstLevelDivisions.specifiedDivision(divisionId));
    }

    /**
     * This method passes the logged in user between screens
     *
     * @param loggedInUser
     */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * This method detects the country combo box was selected.
     *
     * @param actionEvent Event that is caught to detect selection of combo box
     */
    public void countryComboBoxSelected(ActionEvent actionEvent) {
        fillDivisionComboBox();
    }
}

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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is used for the controller of the add customer screen
 */
public class AddCustomerScreenController implements Initializable {

    private final ObservableList<Customer> customers = DBCustomers.getMainScreenCustomerInfo();
    private final ObservableList<Country> countries = DBCountries.getAllCountries();
    private final ArrayList<TextField> textFields = new ArrayList<>();
    @FXML
    public TextField customerNameTextField;
    @FXML
    public TextField zipTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField phoneTextField;
    @FXML
    public TextField customerIDTextField;
    @FXML
    public Button confirmCustomerButton;
    @FXML
    public Button backButton;
    @FXML
    public ComboBox countryComboBox;
    @FXML
    public ComboBox divisionComboBox;
    public int numberOfCustomers;
    public String loggedInUser;
    private ObservableList<Division> divisions;

    /**
     * Initializes the screen for adding a customer
     *
     * @param url Not used
     * @param rb  Not used
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCountryComboBox();
        populateTextFieldList();
    }

    /**
     * This method passes the number of customers between screens
     *
     * @param numberOfCustomers Number of customers passed from main screen to this one
     */
    public void passNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
        getNextIdNumber(numberOfCustomers);
    }

    /**
     * This method passes the logged in user between screens
     *
     * @param loggedInUser Logged in user that is passed from main screen to this one
     */
    public void passLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * This method adds a user after the confirm button has been pressed
     *
     * @param actionEvent Event that is caught to detect button press
     * @throws IOException Exception that is caught in case of IO errors
     */
    public void confirmButtonAction(ActionEvent actionEvent) throws IOException {
        // Check that there are no empty text fields and display an alert if necessary
        boolean emptyTextField = ErrorChecker.validateAddCustomerTextFields(textFields);
        if (emptyTextField == true) {
            ShowAlerts.showAlert(1);
            return;
        }

        // Set up an alert for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm New Customer");
        alert.setHeaderText("Are you sure you want to add this customer?");
        alert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = alert.showAndWait();

        // If user accepts the prompt
        if (decision.get() == ButtonType.OK) {
            Customer newCustomer;
            int customerId = Integer.parseInt(customerIDTextField.getText());
            String customerName = customerNameTextField.getText();
            String customerAddress = addressTextField.getText();
            String zipCode = zipTextField.getText();
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
            newCustomer = new Customer(customerId, customerName, customerAddress, zipCode, phoneNumber, customerCountry,
                    customerDivisionId, loggedInUser);
            DBCustomers.addNewCustomer(newCustomer);

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

    /**
     * Method that goes back to the main screen
     *
     * @param actionEvent Event that is caught to detect if the back button has been pressed
     * @throws IOException Exception that is caught to detect IO exception
     */
    public void backButtonAction(ActionEvent actionEvent) throws IOException {

        // Load next screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/main_screen.fxml"));

        // Set parent and scene
        Parent mainScreenParent = loader.load();
        Scene mainScreenScene = new Scene(mainScreenParent);

        // This line gets the Stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScreenScene);
        window.show();
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
            divisionComboBox.getItems().add(division);
        }

    }

    /**
     * This method generates next id number for the new customer
     *
     * @param numberOfCustomers Parameter passed that tells us how many customers there currently are in the db
     */
    public void getNextIdNumber(int numberOfCustomers) {
        int size = numberOfCustomers; // Set the size
        int i = 1;  // Iterator

        // If no customers in database
        if (size == 0) {
            customerIDTextField.setText("1");
        } else {
            for (Customer customer : customers) {
                if (customer.getCustomerId() == i) {
                    if (i == size) {
                        customerIDTextField.setText(String.valueOf(i + 1));
                    }
                    i += 1;
                    continue;
                } else {
                    customerIDTextField.setText(String.valueOf(i));
                    break;
                }
            }
        }
    }

    /**
     * This method detects the country combo box was selected.
     *
     * @param actionEvent Event that is caught to detect selection of combo box
     */
    public void countryComboBoxSelected(ActionEvent actionEvent) {
        divisionComboBox.setDisable(false);
        fillDivisionComboBox();
    }

    /**
     * This method detects the division info combo box was selected.
     *
     * @param actionEvent Event that is caught to detect selection of combo box
     */
    public void divisionInfoComboBoxSelected(ActionEvent actionEvent) {
    }

    /**
     * This method populates our textFieldList in order to run error checks against it.
     * For example if a field is empty we will return to the user an error saying so when trying to save.
     */
    public void populateTextFieldList() {
        textFields.add(customerNameTextField);
        textFields.add(zipTextField);
        textFields.add(addressTextField);
        textFields.add(phoneTextField);
        textFields.add(customerIDTextField);
    }
}

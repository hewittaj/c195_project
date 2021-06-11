package Controllers;

import DBAccess.DBCountries;
import DBAccess.DBCustomers;
import DBAccess.DBFirstLevelDivisions;
import Models.Country;
import Models.Customer;
import Models.Division;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddCustomerScreenController implements Initializable {

    @FXML public TextField customerNameTextField;
    @FXML public TextField zipTextField;
    @FXML public TextField addressTextField;
    @FXML public TextField phoneTextField;
    @FXML public TextField customerIDTextField;
    @FXML public Button deleteCustomerButton;
    @FXML public Button confirmCustomerButton;
    @FXML public Button backButton;
    @FXML public ComboBox countryComboBox;
    @FXML public ComboBox divisionComboBox;

    public int numberOfCustomers;
    public String loggedInUser;
    private ObservableList<Customer> customers = DBCustomers.getMainScreenCustomerInfo();
    private ObservableList<Country> countries = DBCountries.getAllCountries();
    private ObservableList<Division> divisions;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        fillCountryComboBox();
    }

    /**
     * This method passes the number of customers between screens
     * @param numberOfCustomers
     */
    public void passNumberOfCustomers(int numberOfCustomers){
        this.numberOfCustomers = numberOfCustomers;
        getNextIdNumber(numberOfCustomers);
    }

    /**
     * This method passes the logged in user between screens
     * @param loggedInUser
     */
    public void passLoggedInUser(String loggedInUser){
        this.loggedInUser = loggedInUser;
    }

    /**
     * This method adds a user after the confirm button has been pressed
     * @param actionEvent Event that is caught to detect button press
     * @throws IOException Exception that is caught in case of IO errors
     */
    public void confirmButtonAction(ActionEvent actionEvent) throws IOException{
        // Set up an alert
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("Confirm New Customer");
        cancelAlert.setHeaderText("Are you sure you want to add this customer?");
        cancelAlert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = cancelAlert.showAndWait();

        // If user accepts the prompt
        if(decision.get() == ButtonType.OK){
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
            for (Division division: divisions){
                if (division.getDivisionName().equals(customerDivision)){
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
            Parent mainScreenParent = (Parent)loader.load();

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

    /**
     * Method that goes back to the main screen
     * @param actionEvent
     * @throws IOException
     */
    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        // TO DO ASK ARE YOU SURE



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

    /**
     * This method fills the Country Combo box for the add customer controller
     */
    public void fillCountryComboBox(){
        for(Country country: countries){
            countryComboBox.getItems().add(country);
        }
    }

    /**
     * This method fills the division combo box based off what country is selected
     */
    public void fillDivisionComboBox(){
        divisionComboBox.getItems().clear();
        Country selectedCountry = (Country) countryComboBox.getSelectionModel().getSelectedItem();
        int countryId = selectedCountry.getId();
        divisions = DBFirstLevelDivisions.getFirstLevelDivisionInfo(countryId);
        for(Division division: divisions){
            divisionComboBox.getItems().add(division);
        }

    }

    /**
     * Generates the next Id Number for a customer we want to add
     */
    public void getNextIdNumber(int numberOfCustomers){
        int size = numberOfCustomers; // Set the size
        if(size == 0){
            customerIDTextField.setText("1");
        }else{
            try{
                for(int i = 0; i <=size; i++){ // Loop through the list
                    if(i == 0){ // Skip 0 as we don't want an ID of zero
                        continue;
                    }
                    if(customers.get(i).getCustomerId() == i){ // If customers returned is null
                        continue; // Continue in loop

                    }else if(customers.get(i).getCustomerId() != i){ // If customers matches we continue in the loop
                            customerIDTextField.setText(String.valueOf(Customer.getLastCustomerId() + 1));
                    }
                    else{
                        continue;
                    }
                }
            }catch(IndexOutOfBoundsException e){
                customerIDTextField.setText(String.valueOf(size + 1));
            }

        }
    }

    /**
     * This method detects the country combo box was selected.
     * @param actionEvent Event that is caught to detect selection of combo box
     */
    public void countryComboBoxSelected(ActionEvent actionEvent) {
        divisionComboBox.setDisable(false);
        fillDivisionComboBox();
    }

    /**
     * This method detects the division info combo box was selected.
     * @param actionEvent Event that is caught to detect selection of combo box
     */
    public void divisionInfoComboBoxSelected(ActionEvent actionEvent) {
    }
}

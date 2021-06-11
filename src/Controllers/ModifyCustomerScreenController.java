package Controllers;

import DBAccess.DBCountries;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerScreenController implements Initializable {

    @FXML public TextField customerIDTextField;
    @FXML public TextField customerNameTextField;
    @FXML public TextField zipTextField;
    @FXML public TextField addressTextField;
    @FXML public TextField phoneTextField;
    @FXML public Button confirmCustomerButton;
    @FXML public ComboBox countryComboBox;
    @FXML public ComboBox divisionComboBox;
    @FXML public Button backButton;

    public static Customer customer; // Customer info of customer to modify
    private ObservableList<Country> countries = DBCountries.getAllCountries();
    private ObservableList<Division> divisions;


    @Override
    public void initialize(URL url, ResourceBundle rb){



    }

    public void deleteButtonAction(ActionEvent actionEvent) {
    }

    public void confirmButtonAction(ActionEvent actionEvent) {
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
            divisionComboBox.getItems().add(division.toString());
        }

    }

    public void passCustomer(Customer customer){
        // Initialize important variables for retrieving data
        this.customer = customer;
        Country preassociatedCountry = DBCountries.getSpecificCountry(this.customer.getDivisionId());
        int divisionId = this.customer.getDivisionId();

        // Initialize text fields to currently selected customer values
        customerIDTextField.setText(String.valueOf(this.customer.getCustomerId()));
        customerNameTextField.setText(this.customer.getCustomerName());
        addressTextField.setText(this.customer.getCustomerAddress());
        zipTextField.setText(this.customer.getZipCode());
        phoneTextField.setText(this.customer.getPhoneNumber());

        // Fill country combo box and set predefined value
        fillCountryComboBox();
        countryComboBox.setValue(preassociatedCountry);

        // Fill division combo box and set predefined value
        fillDivisionComboBox();
        divisionComboBox.setValue(DBFirstLevelDivisions.specifiedDivision(divisionId));
    }

    /**
     * This method detects the country combo box was selected.
     * @param actionEvent Event that is caught to detect selection of combo box
     */
    public void countryComboBoxSelected(ActionEvent actionEvent) {
        fillDivisionComboBox();
    }
}

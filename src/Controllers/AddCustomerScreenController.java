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

    private int numberOfCustomers;
    private ObservableList<Customer> customers = DBCustomers.getMainScreenCustomerInfo();
    private ObservableList<Country> countries = DBCountries.getAllCountries();
    private ObservableList<Division> divisions;
    /**
     * Constructor for the add customer screen controller
     * @param numberOfCustomers
     */
    public AddCustomerScreenController(int numberOfCustomers){
        this.numberOfCustomers = numberOfCustomers;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        getNextIdNumber();
        fillCountryComboBox();
    }


    public void confirmButtonAction(ActionEvent actionEvent) {
        // Set up an alert
        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("TEST");
        cancelAlert.setHeaderText("Are you sure you want to TEST?");
        cancelAlert.setContentText("Click 'OK' to confirm.");
        Optional<ButtonType> decision = cancelAlert.showAndWait();
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

    /**
     * This method gets the number of customers currently in the database
     * @return Returns the number of customers currently in the database
     */
    public int getNumberOfCustomers(){
        return numberOfCustomers;
    }

    /**
     * Generates the next Id Number for a customer we want to add
     */
    public void getNextIdNumber(){
        int size = getNumberOfCustomers(); // Set the size

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

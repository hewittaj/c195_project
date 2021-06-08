package Controllers;

import DBAccess.DBCustomers;
import Models.Customer;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.io.IOException;
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
    ObservableList<Customer> customers = DBCustomers.getMainScreenCustomerInfo();

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

    public void deleteButtonAction(ActionEvent actionEvent) {
    }

    public int getSizeOfAllCustomers(){
        return numberOfCustomers;
    }

    /**
     * Generates the next Id Number for a customer we want to add
     */
    public void getNextIdNumber(){
        int size = getSizeOfAllCustomers(); // Set the size

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
}

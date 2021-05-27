package Sample;

import DBAccess.DBCountries;
import Models.Countries;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableView dataTable;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void showMe(ActionEvent event){
        ObservableList<Countries> clist = DBCountries.getAllCountries();
        for(Countries c: clist){
            System.out.println("Country ID: " + c.getId() + " Name: " + c.getName());
        }
    }
}

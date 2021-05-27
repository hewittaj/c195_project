package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Models.Countries;
import java.sql.*;
// TO DO Delete this module
public class DBCountries {

    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries c = new Countries(countryId, countryName);

                // Add new country to our observable list
                clist.add(c);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return clist;
    }
}

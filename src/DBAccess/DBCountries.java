package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Models.Country;
import java.sql.*;

public class DBCountries {

    /**
     * This method gets all of the countries in the database
     * @return Returns an observable list of all countries
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> countries = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryId, countryName);

                // Add new country to our observable list
                countries.add(c);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return countries;
    }
}

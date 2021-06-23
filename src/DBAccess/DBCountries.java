package DBAccess;

import Database.DBConnection;
import Models.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountries {

    // TO DO POSSIBLY DELETE
    public static int getCountryId(int divisionId) {
        int countryId = 0;
        try {

            String sql = "select country_id from countries where country = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, "test");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                countryId = rs.getInt("Country_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countryId;
    }

    /**
     * This method returns the specific country based on the country id
     *
     * @param idNumber id number that is passed to obtain the country information based on that id
     * @return Returned country object of specified id number
     */
    public static Country getSpecificCountry(int idNumber) {
        Country specificCountry = null;
        try {
            String sql = "select c.country_id, c.country, fd.division_id from countries as c\n" +
                    "join first_level_divisions as fd \n" +
                    "on fd.country_id = c.country_id\n" +
                    "where division_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, idNumber);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                specificCountry = new Country(countryId, countryName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specificCountry;
    }

    /**
     * This method gets all of the countries in the database
     *
     * @return Returns an observable list of all countries
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryId, countryName);

                // Add new country to our observable list
                countries.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }
}

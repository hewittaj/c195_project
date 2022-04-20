package DBAccess;

import Database.DBConnection;
import Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class handles the db management of first level divisions
 */
public class DBFirstLevelDivisions {

    /**
     * This method gets the specified division based on the division id
     *
     * @param passedDivisionId Passed division id to help retrieve the specified division
     * @return Returns the division we want based on the division id passed to the method
     */
    public static Division specifiedDivision(int passedDivisionId) {
        Division division = null;
        try {
            String sql = "select division_id, division, country_id from first_level_divisions where division_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, passedDivisionId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                division = new Division(divisionId, divisionName, countryId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return division;
    }

    /**
     * This method returns all the first level division info
     *
     * @param countryId Country id passed to the method to retrieve the first level division info for that specified
     *                  country id
     */
    public static ObservableList<Division> getFirstLevelDivisionInfo(int countryId) {
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try {
            String sql = "select fd.division, fd.division_id, c.country, c.country_id from first_level_divisions as fd\n" +
                    "inner join countries as c on fd.country_id = c.country_id\n" +
                    "where fd.COUNTRY_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");

                Division division = new Division(divisionId, divisionName, countryId);

                // Add new user to our observable list
                divisions.add(division);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    /**
     * This method validates whether the division id that the user has updated to in the customer table view is valid
     *
     * @param divisionId Division id that we want to check
     * @return Return a boolean value whether or not the division id is valid. True if valid, false if not.
     */
    public static boolean validateDivisionId(int divisionId) {
        try {
            String division = "";
            String sql = "select division from first_level_divisions where division_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionId);

            // Need a result set to detect if a result is found or not
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                division = rs.getString("division");
            }

            // If no error is caught it will return true
            return true;
        } catch (SQLException e) {
            // Error is caught, will return false
            return false;
        }
    }
}

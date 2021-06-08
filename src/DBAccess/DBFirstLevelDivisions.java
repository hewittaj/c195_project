package DBAccess;

import Database.DBConnection;
import Models.Division;
import Models.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBFirstLevelDivisions {

    public static ObservableList<Division> getFirstLevelDivisionInfo(int countryId){
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        try{
            String sql = "select fd.division, fd.division_id, c.country, c.country_id from first_level_divisions as fd\n" +
                    "inner join countries as c on fd.country_id = c.country_id\n" +
                    "where fd.COUNTRY_ID = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");

                Division division = new Division(divisionId, divisionName, countryId);

                // Add new user to our observable list
                divisions.add(division);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return divisions;
    }
}

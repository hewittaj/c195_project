package DBAccess;

import Database.DBConnection;
import Models.Contact;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {

    /**
     * This method returns the contact id given their name
     * @param contactName Contact name we are looking to acquire the contact id for
     * @return Returns the contact id
     */
    public static int getContactIdFromName(String contactName){
        // Initialize contactId
        int contactId = 0;

        try{
            // Prepare sql command and prepared statement
            String sql = "select contact_id from contacts where contact_name = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, contactName);
            // Set up result set for query
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                contactId = rs.getInt("Contact_ID");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return contactId;
    }

    /**
     * This method returns all contacts currently in the database for creation/updating appointments
     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {

            // Create sql command and prepared statement
            String sql = "select * from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            // Execute query and acquire result set then loop through info
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                // Get contact information and add to observable list
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contact contact = new Contact(contactId, contactName, contactEmail);
                allContacts.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

}

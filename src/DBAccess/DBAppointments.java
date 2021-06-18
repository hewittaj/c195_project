package DBAccess;

import Database.DBConnection;
import Models.Appointment;
import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

import java.time.LocalDateTime;

public class DBAppointments {

    /**
     * This method adds a new appointment to the database
     * @param newAppointment Parameter passed that represents the new appointment
     */
    public static void addAppointment(Appointment newAppointment){
        try{
            String sql = "insert into appointments (appointment_id, title, description, location,\n" +
                    " type, start, end, created_by, last_updated_by, customer_id, user_id, contact_id)\n" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1,newAppointment.getAppointmentId());
            ps.setString(2, newAppointment.getTitle());
            ps.setString(3, newAppointment.getDescription());
            ps.setString(4, newAppointment.getLocation());
            ps.setString(5, newAppointment.getType());
            ps.setTimestamp(6, Timestamp.valueOf(newAppointment.getStartDateTime()));
            ps.setTimestamp(7, Timestamp.valueOf(newAppointment.getEndDateTime()));
            ps.setString(8, newAppointment.getLoggedInUser());
            ps.setString(9, newAppointment.getLoggedInUser());
            ps.setInt(10, newAppointment.getCustomerId());
            ps.setInt(11, newAppointment.getUserId());
            ps.setInt(12, newAppointment.getContactId());

            ps.execute();
        } catch(SQLIntegrityConstraintViolationException sql){
            // Set up an alert for no value selected
            Alert sqlAlert = new Alert(Alert.AlertType.CONFIRMATION);
            sqlAlert.setTitle("Integrity violation");
            sqlAlert.setHeaderText("SQL foreign key restraint most likely caused\n" +
                    "by an incorrect Customer or User ID.\n" +
                    "Taking you back to the home screen. Please try again.");
            sqlAlert.setContentText("Click 'OK' to confirm.");
            sqlAlert.showAndWait();
        } catch(SQLException e){
            e.printStackTrace();
        }


    }

    /**
     * This method gets all appointments currently in the database
     * @return Returns an observable list of appointments
     */
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try{
            // Prepare sql command and prepared statement
            String sql = "select * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            // Set up result set for query
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                int userId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");
                int contactId = rs.getInt("Contact_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime =  rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();

                // Create appointment instance and add to our observable list
                Appointment appointment = new Appointment(appointmentId, userId, customerId, contactId, title,
                        description, location, type, startTime, endTime);

                appointments.add(appointment);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    public static void updateAppointment(Appointment modifiedAppointment) {
       try{
           // Initialize sql and prepared statement
            String sql = "update appointments\n" +
                    "set appointment_id = ?, title = ?, description = ?, location = ?, type = ?, \n" +
                    "start = ?, end = ?, last_update = ?, last_updated_by = ?, customer_id = ?," +
                    " user_id = ?,\n" +
                    "contact_id = ?\n" +
                    "where appointment_id = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            // Set values in query
            ps.setInt(1, modifiedAppointment.getAppointmentId());
            ps.setString(2, modifiedAppointment.getTitle());
            ps.setString(3, modifiedAppointment.getDescription());
            ps.setString(4, modifiedAppointment.getLocation());
            ps.setString(5, modifiedAppointment.getType());
            ps.setTimestamp(6, Timestamp.valueOf(modifiedAppointment.getStartDateTime()));
            ps.setTimestamp(7, Timestamp.valueOf(modifiedAppointment.getEndDateTime()));
            ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(9, modifiedAppointment.getLoggedInUser());
            ps.setInt(10, modifiedAppointment.getCustomerId());
            ps.setInt(11, modifiedAppointment.getUserId());
            ps.setInt(12, modifiedAppointment.getContactId());
            ps.setInt(13, modifiedAppointment.getAppointmentId());

            ps.execute();
        }
       catch(SQLIntegrityConstraintViolationException sql){
           // Set up an alert for no value selected
           Alert sqlAlert = new Alert(Alert.AlertType.CONFIRMATION);
           sqlAlert.setTitle("Integrity violation");
           sqlAlert.setHeaderText("SQL foreign key restraint most likely caused\n" +
                   "by an incorrect Customer or User ID.\n" +
                   "Taking you back to the home screen. Please try again.");
           sqlAlert.setContentText("Click 'OK' to confirm.");
           sqlAlert.showAndWait();
       }
       catch(SQLException e){
           e.printStackTrace();
       }
    }
}

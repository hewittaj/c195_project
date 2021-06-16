package DBAccess;

import Database.DBConnection;
import Models.Appointment;
import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        }
        catch(SQLException e){
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
}

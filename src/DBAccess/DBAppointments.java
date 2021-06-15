package DBAccess;

import Database.DBConnection;
import Models.Appointment;
import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBAppointments {

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

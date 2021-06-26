package DBAccess;

import Database.DBConnection;
import Models.Appointment;
import Models.MonthAndTypeReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDateTime;

public class DBAppointments {

    /**
     * This method adds a new appointment to the database
     *
     * @param newAppointment Parameter passed that represents the new appointment
     */
    public static void addAppointment(Appointment newAppointment) {
        try {
            String sql = "insert into appointments (appointment_id, title, description, location,\n" +
                    " type, start, end, created_by, last_updated_by, customer_id, user_id, contact_id)\n" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, newAppointment.getAppointmentId());
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
        } catch (SQLIntegrityConstraintViolationException sql) {
            // Set up an alert for no value selected
            Alert sqlAlert = new Alert(Alert.AlertType.CONFIRMATION);
            sqlAlert.setTitle("Integrity violation");
            sqlAlert.setHeaderText("SQL foreign key restraint most likely caused\n" +
                    "by an incorrect Customer or User ID.\n" +
                    "Taking you back to the home screen. Please try again.");
            sqlAlert.setContentText("Click 'OK' to confirm.");
            sqlAlert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method deletes the selected appointment from the database
     *
     * @param appointment Parameter passed that is the appointment object the user wants to delete
     */
    public static void deleteAppointment(Appointment appointment) {
        try {
            String sql = "DELETE FROM appointments WHERE appointment_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, appointment.getAppointmentId());

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets all appointments currently in the database
     *
     * @return Returns an observable list of appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            // Prepare sql command and prepared statement
            String sql = "select * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            // Set up result set for query
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int userId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");
                int contactId = rs.getInt("Contact_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
                String createdBy = rs.getString("created_by");
                // Create appointment instance and add to our observable list
                Appointment appointment = new Appointment(appointmentId, userId, customerId, contactId, title,
                        description, location, type, startTime, endTime, createdBy);

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * This method gets all appointments currently in the database for a specific customer
     *
     * @return Returns an observable list of appointments
     */
    public static ObservableList<Appointment> getAllAppointmentsForSpecificCustomer(int id) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            // Prepare sql command and prepared statement
            String sql = "select * from appointments where customer_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);

            // Set up result set for query
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int userId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");
                int contactId = rs.getInt("Contact_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();

                // Create appointment instance and add to our observable list
                Appointment appointment = new Appointment(appointmentId, userId, customerId, contactId, title,
                        description, location, type, startTime, endTime);

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * This method gets the number of appointments that a customer has
     *
     * @param customerId Customer id passed to detect number of appointments they have
     * @return Return number of appointments found
     */
    public static int getNumberOfAppointments(int customerId) {
        int numberOfAppointments = 0;
        try {
            String sql = "Select * from appointments where customer_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                numberOfAppointments++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfAppointments;
    }

    /**
     * This method updates a selected appointment
     *
     * @param modifiedAppointment Appointment that is selected is passed in order to be modified
     */
    public static void updateAppointment(Appointment modifiedAppointment) {
        try {
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
        } catch (SQLIntegrityConstraintViolationException sql) {
            // Set up an alert for no value selected
            Alert sqlAlert = new Alert(Alert.AlertType.CONFIRMATION);
            sqlAlert.setTitle("Integrity violation");
            sqlAlert.setHeaderText("SQL foreign key restraint most likely caused\n" +
                    "by an incorrect Customer or User ID.\n" +
                    "Taking you back to the home screen. Please try again.");
            sqlAlert.setContentText("Click 'OK' to confirm.");
            sqlAlert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This gets a report of all the appointments by type and month
     *
     * @return Observable list of the report which can then be output to a table view
     */
    public static ObservableList<MonthAndTypeReport> getMonthlyAppointmentsByTypeAndMonth() {
        ObservableList<MonthAndTypeReport> reports = FXCollections.observableArrayList();

        String sql = "SELECT DATE_FORMAT(start, '%M') AS month, COUNT(start) AS count," +
                " type FROM appointments GROUP BY month, type";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String month = rs.getString("month");
                int count = rs.getInt("count");
                String type = rs.getString("type");

                MonthAndTypeReport report = new MonthAndTypeReport(month, type, count);
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    /**
     * This method gets the specified contacts appointments for a report
     *
     * @param requestedId Contact id passed that user wants to get appointments for
     * @return Observable list of appointments for specified contact
     */
    public static ObservableList<Appointment> getSpecificContactsAppointments(int requestedId) {
        // Initialize observable list
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "select * from appointments where contact_id = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, requestedId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                int userId = rs.getInt("User_ID");
                int customerId = rs.getInt("Customer_ID");
                int contactId = rs.getInt("Contact_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();

                // Create appointment instance and add to our observable list
                Appointment appointment = new Appointment(appointmentId, userId, customerId, contactId, title,
                        description, location, type, startTime, endTime);

                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
}

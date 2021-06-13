package Models;

/**
 * Injecting w/ prepared statement: p.setTimestamp(index, ts)
 * Extracting w/ result set: Timestamp ts = rs.getTimestamp("columName")
 */
import java.sql.Timestamp;
import java.time.*;


public class Appointment {
    public int appointmentId;
    public String title;
    public String description;
    public String location;
    public String contact;
    public String type;
    public String loggedInUser;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    public Appointment(int appointmentId, String title, String description, String location, String contact, String type
    , LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}

package Models;

/*
 * Injecting w/ prepared statement: p.setTimestamp(index, ts)
 * Extracting w/ result set: Timestamp ts = rs.getTimestamp("columName")
 */

import DBAccess.DBAppointments;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Appointment {
    public int appointmentId;
    public int userId;
    public int customerId;
    public int contactId;
    public String title;
    public String description;
    public String location;
    public String contact;
    public String type;
    public String loggedInUser;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    /**
     * Constructor for an appointment
     *
     * @param userId
     * @param customerId
     * @param contactId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDateTime
     * @param endDateTime
     */
    public Appointment(int appointmentId, int userId, int customerId, int contactId, String title, String description, String location, String type
            , LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.customerId = customerId;
        this.contactId = contactId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Constructor that includes logged in user for database addition/updates
     *
     * @param appointmentId
     * @param userId
     * @param customerId
     * @param contactId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @param loggedInUser
     */
    public Appointment(int appointmentId, int userId, int customerId, int contactId, String title, String description, String location, String type
            , LocalDateTime startDateTime, LocalDateTime endDateTime, String loggedInUser) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.customerId = customerId;
        this.contactId = contactId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.loggedInUser = loggedInUser;
    }

    /**
     * Gets the last appointment Id number for adding a appointment functionality
     *
     * @return The appointment ID for the last appointment in the list
     */
    public static int getLastAppointmentId() {
        int maxId = 0;
        for (Appointment appointment : DBAppointments.getAllAppointments()) {
            if (appointment.getAppointmentId() > maxId) {
                maxId = appointment.getAppointmentId();
            }
        }
        return maxId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getContactId() {
        return contactId;
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

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * This method returns just the appointment's date without any time
     *
     * @return Returns the date of the appointment
     */
    public LocalDate getDate() {
        return startDateTime.toLocalDate();
    }

    /**
     * This method returns just the appointment's starting appointment time
     *
     * @return Returns the starting time of the appointment
     */
    public LocalTime getStartingTime() {
        return startDateTime.toLocalTime();
    }

    /**
     * This method returns just the appointment's ending appointment time
     *
     * @return Returns the ending time of the appointment
     */
    public LocalTime getEndingTime() {
        return endDateTime.toLocalTime();
    }
}

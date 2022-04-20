package Models;

/*
 * Injecting w/ prepared statement: p.setTimestamp(index, ts)
 * Extracting w/ result set: Timestamp ts = rs.getTimestamp("columnName")
 */

import DBAccess.DBAppointments;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class represents an appointment object
 */
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
     * Constructor for an appointment w/o logged in user
     *
     * @param userId        User Id associated with appointment
     * @param customerId    Customer id associated with appointment
     * @param contactId     Contact id associated with appointment
     * @param title         Title of appointment
     * @param description   Description of appointment
     * @param location      Location of appointment
     * @param type          Type of appointment
     * @param startDateTime Start date and time of appointment
     * @param endDateTime   End date and time of appointment
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
     * @param userId        User Id associated with appointment
     * @param customerId    Customer id associated with appointment
     * @param contactId     Contact id associated with appointment
     * @param title         Title of appointment
     * @param description   Description of appointment
     * @param location      Location of appointment
     * @param type          Type of appointment
     * @param startDateTime Start date and time of appointment
     * @param endDateTime   End date and time of appointment
     * @param loggedInUser  Logged in user associated with appointment for db purposes
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

    /**
     * Gets the appointment id
     *
     * @return Appointment id returned
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets the user id
     *
     * @return User id returned
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the customer id
     *
     * @return Id of customer
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Gets the contact id
     *
     * @return Id of contact
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Gets the title of appointment
     *
     * @return Title of appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description of the appointment
     *
     * @return Description of appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the location of appointment
     *
     * @return Location of appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the contact of appointment
     *
     * @return Contact of appointment
     */
    public String getContact() {
        return contact;
    }

    /**
     * Gets the type of appointment
     *
     * @return Type of appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Gets logged in user of appointment
     *
     * @return Logged in user
     */
    public String getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Gets start date/time of appointment
     *
     * @return Start date/time of appointment
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Gets the end date/time of appointment
     *
     * @return End date/time of appointment
     */
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

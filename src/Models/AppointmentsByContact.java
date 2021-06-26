package Models;

import java.time.LocalDateTime;

public class AppointmentsByContact {

    // Initialize private variables
    private int appointmentId;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;

    /**
     * This method constructs a new reportable object for the appointments by contact
     * @param appointmentId Appointments id
     * @param title Title of appointment
     * @param description Description of appointment
     * @param startDateTime Starting date and time for appointment
     * @param endDateTime Ending date and time for appointment
     * @param customerId Customer id for appointment
     */
    public AppointmentsByContact(int appointmentId, String title, String description, LocalDateTime startDateTime,
                                 LocalDateTime endDateTime, int customerId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
    }

    /**
     * Gets associated appointment id
     * @return Appointments appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Sets associated appointment id
     * @param appointmentId Appointments appointment id we want to set
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}

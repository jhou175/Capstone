package Model;

import java.time.LocalDateTime;

/**
 * This is the Appointments class and it handles all the appointment's constructors and getter methods.
 */
public class Appointments {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    /**
     * The constructor method for Appointments which initializes the appointment id, title, description, location, type, startTime, endTime, customerId, userId, and contactId.
     *
     * @param appointmentId The integer value for the id value for the appointment.
     * @param title         The String name for the appointment.
     * @param description   The String name for the description.
     * @param location      The String value for the location of the appointment.
     * @param type          The String value for the type of appointment.
     * @param startTime     The StartDateTime value for the appointment start time.
     * @param endTime       The StartDateTime value for the appointment end time.
     * @param customerId    The integer value for the customerId.
     * @param userId        The integer value for the userId.
     * @param contactId     The integer value for the contactId.
     */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * The constructor overload method for Appointments which initializes everything from the main constructor and adds contactName.
     *
     * @param appointmentId The integer value for the id value for the appointment.
     * @param title         The String name for the appointment.
     * @param description   The String name for the description.
     * @param location      The String value for the location of the appointment.
     * @param type          The String value for the type of appointment.
     * @param startTime     The StartDateTime value for the appointment start time.
     * @param endTime       The StartDateTime value for the appointment end time.
     * @param customerId    The integer value for the customerId.
     * @param userId        The integer value for the userId.
     * @param contactId     The integer value for the contactId.
     * @param contactName   The string value for the contactName.
     */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, int customerId, int userId, int contactId, String contactName) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * This method retrieves the appointmentId.
     *
     * @return The appointmentId.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * This method retrieves the title.
     *
     * @return The appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method retrieves the  description.
     *
     * @return The appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method retrieves the location.
     *
     * @return The appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method retrieves the type.
     *
     * @return The appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * This method retrieves the start day and time.
     *
     * @return The appointment startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * This method retrieves the end day and time.
     *
     * @return The appointment
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * This method retrieves the customerId.
     *
     * @return The customerId associated with that appointment.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * This method retrieves the userId.
     *
     * @return The userId associated with that appointment.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * This method retrieves the contactId.
     *
     * @return The contactId associated with that appointment.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * This method retrieves the contactName.
     *
     * @return The customerName associated with that appointment.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method overload retrieves the appointment data as a string.
     *
     * @return The appointment title.
     */
    @Override
    public String toString() {
        return this.title;
    }


}

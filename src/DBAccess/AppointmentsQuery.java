package DBAccess;

import Database.JDBC;
import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * This is the AppointmentsQuery class and it handles all the SQL insert, update, delete, and select queries for the Appointments table.
 */
public abstract class AppointmentsQuery {


    /**
     * This method handles the query to insert a new appointment into the appointments table in the sql database.
     *
     * @param title       The String value for title.
     * @param description The String value for description.
     * @param location    The String value for location.
     * @param type        The String value for type.
     * @param start       The LocalDateTime value for appointment start.
     * @param end         The LocalDateTime value for appointment end.
     * @param customerId  The integer value for the customerId.
     * @param userId      The integer value for the userId.
     * @param contactId   The integer value for the contactId.
     */

    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        try {
            String sql = "INSERT INTO APPOINTMENTS(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "Values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method handles the query to update an existing appointment from the appointments table in the sql database.
     *
     * @param appointmentId The integer value for the appointment id.
     * @param title         The String value for title.
     * @param description   The String value for description.
     * @param location      The String value for location.
     * @param type          The String value for type.
     * @param start         The LocalDateTime value for appointment start.
     * @param end           The LocalDateTime value for appointment end.
     * @param customerId    The integer value for the customerId.
     * @param userId        The integer value for the userId.
     * @param contactId     The integer value for the contactId.
     */
    //Method updates the appointment info using the entered Appointment fields.
    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            ps.setInt(10, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method handles the query to cancel and delete an existing appointment from the appointments table in the sql database.
     *
     * @param appointmentId The integer value for the appointmentId.
     */
    //Method deletes the selected appointment by its appointmentId field.
    public static void deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method retrieves all appointments in the appointments table from the sql database.
     *
     * @return ObservableArrayList appointments.
     */
    public static ObservableList<Appointments> selectAllAppointments() {
        ObservableList<Appointments> appointments = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID,appointments.Title,appointments.Description, appointments.Location,appointments.Type,appointments.Start," +
                "appointments.End,appointments.Customer_ID, appointments.User_ID,appointments.Contact_ID,contacts.Contact_Name FROM Appointments JOIN " +
                "contacts ON appointments.Contact_ID = contacts.Contact_ID";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Appointments a = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId, contactName);
                appointments.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * This method retrieves all appointments in the appointments table that have a matching customerId associated to them.
     *
     * @param customerId The integer value for the selected customerId.
     * @return ObservableArrayList appointmentsByCustomer.
     */
    //Method returns all appointment fields in the Appointments table by the customerId field criteria
    public static ObservableList<Appointments> selectByCustomer(int customerId) {
        ObservableList<Appointments> appointmentsByCustomer = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID,appointments.Title,appointments.Description, appointments.Location,appointments.Type,appointments.Start," +
                "appointments.End,appointments.Customer_ID, appointments.User_ID,appointments.Contact_ID,contacts.Contact_Name FROM Appointments JOIN " +
                "contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE Customer_ID = ?";


        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();
                int customerIdFK = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointments aList = new Appointments(appointmentId, title, description, location, type, start, end, customerIdFK, userId, contactId);
                appointmentsByCustomer.add(aList);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsByCustomer;
    }

    /**
     * This method retrieves all appointments in the appointments table that have a matching contactId associated to them.
     *
     * @param contactId The integer value for the selected contactId.
     * @return ObservableArrayList selectByContact.
     */
    //Method returns all appointment fields in the Appointments table by the contactId field criteria
    public static ObservableList<Appointments> selectByContact(int contactId) {
        ObservableList<Appointments> selectByContact = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Appointments WHERE Contact_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactIdFK = rs.getInt("Contact_ID");
                Appointments aContactList = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactIdFK);
                selectByContact.add(aContactList);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectByContact;
    }

    /**
     * This method retrieves all appointments in the appointments table that have a matching appointmentId associated to them.
     *
     * @param appointmentID The integer value for appointment ID.
     * @return Appointments object named appointment.
     */
    //Method returns all appointment fields in the Appointments table
    public static Appointments selectAllAppointmentsByID(int appointmentID) {
        Appointments appointment = null;
        String sql = "SELECT appointments.Appointment_ID,appointments.Title,appointments.Description, appointments.Location,appointments.Type,appointments.Start," +
                "appointments.End,appointments.Customer_ID, appointments.User_ID,appointments.Contact_ID,contacts.Contact_Name FROM Appointments JOIN " +
                "contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId, contactName);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }


    /**
     * * This method retrieves all appointments in the appointments table that have a matching type and month associated to them.
     *
     * @param month String value for the selected month.
     * @param types String value for the appointment type.
     * @return ObservableArrayList appointMonthTypes.
     */
    public static ObservableList<Appointments> getMonthTypes(String month, String types) {
        ObservableList<Appointments> appointMonthTypes = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID,appointments.Title,appointments.Description, appointments.Location,appointments.Type,appointments.Start," +
                "appointments.End,appointments.Customer_ID, appointments.User_ID,appointments.Contact_ID,contacts.Contact_Name FROM Appointments JOIN " +
                "contacts ON appointments.Contact_ID = contacts.Contact_ID WHERE MONTHNAME(Start) = ? and Type = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, month);
            ps.setString(2, types);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp t = rs.getTimestamp("Start");
                LocalDateTime start = t.toLocalDateTime();
                Timestamp e = rs.getTimestamp("End");
                LocalDateTime end = e.toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                Appointments a = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId, contactName);
                appointMonthTypes.add(a);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointMonthTypes;
    }


//    //Method returns the next appointment due to start from the Appointments table
//    public static Appointments selectNextAppointment() {
//        Appointments a = null;
//       // String sql = "Select * from appointments where Start >= now() limit 1;";
//        String sql = "Select * from appointments where Start BETWEEN now() AND now() + INTERVAL 15 minute LIMIT 1";
//
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                int appointmentId = rs.getInt("Appointment_ID");
//                String title = rs.getString("Title");
//                String description = rs.getString("Description");
//                String location = rs.getString("Location");
//                String type = rs.getString("Type");
//                Timestamp t = rs.getTimestamp("Start");
//                LocalDateTime start = t.toLocalDateTime();
//                Timestamp e = rs.getTimestamp("End");
//                LocalDateTime end = e.toLocalDateTime();
//                int customerId = rs.getInt("Customer_ID");
//                int userIdFk = rs.getInt("User_ID");
//                int contactId = rs.getInt("Contact_ID");
//
//                a = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userIdFk, contactId);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//        return a;
//    }


}

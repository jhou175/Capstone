package DBAccess;

import Database.JDBC;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This ContactsQuery class handles all the SQL insert, update, delete, and select queries for the Contacts table.
 * Contacts do not need to be managed currently so the insert,update, and delete methods are commented out until they are needed.
 */
public abstract class ContactsQuery {

    //This code is not needed in the current state of the application but can be implemented if contacts need to be managed.
//    public static void insertContact(String contactName, String email) {
//        String sql = "INSERT INTO CONTACTS(NULL, Contact_Name,Email) VALUES(?,?)";
//        try {
//           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//           ps.setString(1, contactName);
//           ps.setString(2, email);
//           ps.executeUpdate();
//       } catch (SQLException e) {
//           e.printStackTrace();
//       }
//    }
//
//    //Method updates the contact name and email by the selected contactId field.
//    public static void updateContact(int contactId, String contactName, String email)  {
//        String sql = "UPDATE CONTACTS SET Contact = ?, email = ? WHERE Contact_ID = ?";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setString(1, contactName);
//            ps.setString(2, email);
//            ps.setInt(3, contactId);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Method deletes the selected contact by its contactId field.
//    public static void deleteContact(int contactId) {
//        String sql = "DELETE FROM CONTACTS WHERE Contact_ID = ?";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setInt(1, contactId);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This method handles the sql query to return all contacts from the Contacts table.
     *
     * @return ObservableArrayList contacts.
     */

    public static ObservableList<Contacts> selectAllContacts() {
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CONTACTS";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contacts cList = new Contacts(contactId, contactName, email);
                contacts.add(cList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}

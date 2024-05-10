package DBAccess;

import Database.JDBC;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * This CustomerQuery class handles all the SQL insert, update, delete, and select queries for the Customers table.
 */
public abstract class CustomersQuery {


    /**
     * This method handles the sql query to insert a new customer into the Customers table.
     *
     * @param customerName String value for the customerName.
     * @param address      String value for the address.
     * @param zip          String value for the postal code.
     * @param phone        String value for phone number variable.
     * @param divisionId   integer value for the divisionId.
     */
    public static int insertCustomers(String customerName, String address, String zip, String phone, int divisionId) {
        int id = 0;
        String sql = "INSERT INTO CUSTOMERS(Customer_Name, Address, Postal_Code, Phone, Division_ID) Values(?,?,?,?,?)";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, zip);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * This method handles the sql query to update an existing customer in the Customer table of the database.
     *
     * @param customerName String value for the customerName.+
     * @param address      String value for the address.
     * @param zip          String value for the postal code.
     * @param phone        String value for phone number variable.
     * @param divisionId   integer value for the divisionId.
     */
    public static void updateCustomer(int customerId, String customerName, String address, String zip, String phone, int divisionId) {

        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, zip);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            ps.setInt(6, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method handles the sql query to delete the selected existing customer in the Customer table of the database.
     *
     * @param customerId integer value for the customerId.
     */
    public static void deleteCustomer(int customerId) {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ObservableList<Customers> selectAllCustomers() {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name,customers.Address,customers.Postal_Code,customers.Phone,customers.Division_ID,first_level_divisions.Division," +
                " countries.Country, countries.Country_ID FROM customers JOIN first_level_divisions ON customers.Division_ID =" +
                "first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                String country = rs.getString("Country");
                int countryId = rs.getInt("Country_ID");

                Customers c = new Customers(customerId, customerName, address, zip, phone, divisionId, divisionName, country, countryId);
                customerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    /**
     * This method handles the sql query that retrieves all the virtual customers in the customers table.
     *
     * @return ObservableArrayList customerList.
     */

    public static ObservableList<Customers> selectAllVirtualCustomers() {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name,customers.Address,customers.Postal_Code,customers.Phone,customers.Division_ID,first_level_divisions.Division," +
                " countries.Country, countries.Country_ID, virtual_customers.Virtual_Customer_ID, virtual_customers.Zoom_Email FROM customers JOIN first_level_divisions ON customers.Division_ID =" +
                "first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID JOIN virtual_customers ON customers.Customer_ID = virtual_customers.Customer_ID";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                String country = rs.getString("Country");
                int countryId = rs.getInt("Country_ID");
                int virtualCustomerId = rs.getInt("Virtual_Customer_ID");
                String zoomEmail = rs.getString("Zoom_Email");
                Customers c = new Customers(customerId, customerName, address, zip, phone, divisionId, divisionName, country, countryId, virtualCustomerId, zoomEmail);
                customerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    /**
     * This method handles the sql query that retrieves all the customers in the customers table that have a matching divisionId.
     *
     * @param divisionId integer value for the divisionId.
     */
    //Method returns all fields that match the entered DivisionId field in the Customers table
    public static void selectCustomersDivision(int divisionId) {
        String sql = "SELECT * FROM CUSTOMERS WHERE Division_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionIdFK = rs.getInt("Division_ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method retrieves the customer by using the customerID field and adds it to a Customers variable and returns that customers variable.
     *
     * @param customerId integer value for the associated customerId.
     * @return Customers object c;
     */
    public static Customers selectCustomerById(int customerId) {

        Customers c = null;
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name,customers.Address,customers.Postal_Code,customers.Phone,customers.Division_ID,first_level_divisions.Division," +
                " countries.Country, countries.Country_ID FROM customers JOIN first_level_divisions ON customers.Division_ID =" +
                "first_level_divisions.Division_ID JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                String country = rs.getString("Country");
                int countryId = rs.getInt("Country_ID");
                c = new Customers(customerID, customerName, address, zip, phone, divisionId, divisionName, country, countryId);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static int selectLastInserted() throws SQLException {
        int ID = 0;
        String sql = "SELECT MAX(Customer_ID) from Customers ";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ID = rs.getInt("Customer_ID");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ID;
    }
}





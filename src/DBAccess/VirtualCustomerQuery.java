package DBAccess;

import Database.JDBC;
import Model.Customers;
import Model.VirtualCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class VirtualCustomerQuery {


    public static void insertVirtualCustomer(int customerId, String zoomEmail) {
        String sql = "INSERT INTO Virtual_Customers( Customer_ID,Zoom_Email) Values(?,?)";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, zoomEmail);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static VirtualCustomers selectCustomerById(int customerId) {

        VirtualCustomers v = null;
        String sql = "SELECT Virtual_Customer_ID, Zoom_Email, Customer_ID FROM virtual_customers WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int virtualCustomerId = rs.getInt("Virtual_Customer_ID");
                String zoomEmail = rs.getString("Zoom_Email");
                int customerID = rs.getInt("Customer_ID");

                v = new VirtualCustomers(virtualCustomerId, zoomEmail, customerID);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    public static void updateVirtualCustomer(int customerID, String zoom) {
        VirtualCustomers v = VirtualCustomerQuery.selectCustomerById(customerID);
        int virtualId = v.getVirtualID();
        String sql = "UPDATE virtual_customers SET Virtual_Customer_ID = ?, Zoom_Email = ? WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, virtualId);
            ps.setString(2, zoom);
            ps.setInt(3, customerID);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteVirtualCustomer(int customerId) {
        String sql = "DELETE FROM virtual_customers WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

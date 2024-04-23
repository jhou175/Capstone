package DBAccess;

//This class

import Database.JDBC;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * THis UsersQuery class handles all the SQL insert, update, delete, and select queries for the Users table.
 * Users do not need to be managed currently so the insert,update, and delete methods are commented out until they are needed.
 */
public abstract class UsersQuery {
    /**
     * This method returns all userIds and passwords in the Users table using an observable array list.
     *
     * @return ObservableArrayList users.
     */
    public static ObservableList<Users> selectAllUsers() {
        ObservableList<Users> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM USERS";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Users uList = new Users(userId, userName, password);
                users.add(uList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * This method takes in two string variables for userName and password and checks if there are any matches in the database.
     *
     * @param name     String value for name variable.
     * @param password String value for password variable.
     * @return Boolean value for result.
     */
    public static boolean checkUserPassword(String name, String password) {
        boolean result = false;
        String sql = "SELECT User_Name, Password FROM Users";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("User_Name");
                String pass = rs.getString("Password");

                if ((name.equals(username)) && (password.equals(pass))) {
                    result = true;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
//
//    //Method inserts the entered user name and password into the Users table.
//    public static void insertUser(String userName, String password){
//        String sql = "INSERT INTO USERS(NULL,User_Name, Password) VALUES(?,?)";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setString(1, userName);
//            ps.setString(2, password);
//            ps.executeUpdate();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Method updates the userName and password fields by the selected userId field.
//    public static void updateUser(int userId, String userName, String password) {
//        String sql = "UPDATE USERS SET User_Name = ?,Password = ? WHERE USER_ID = ?";
//        try {
//           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//           ps.setString(1, userName);
//           ps.setString(2, password);
//           ps.setInt(3, userId);
//           ps.executeUpdate();
//       }catch (SQLException e) {
//           e.printStackTrace();
//       }
//    }
//
//    //Method deletes the selected user by its userId field.
//    public static void deleteUser(int userId) {
//        String sql = "DELETE FROM USERS WHERE User_ID = ?";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setInt(1, userId);
//            ps.executeUpdate();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


}

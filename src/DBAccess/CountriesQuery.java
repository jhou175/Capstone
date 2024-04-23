package DBAccess;

//This class handles all the SQL insert, update, delete, and select queries for the Countries table.

import Database.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This is the AppointmentsQuery class and it handles all the SQL insert, update, delete, and select queries for the Appointments table.
 * Countries do not need to be managed currently so the insert,update, and delete methods are commented out until they are needed.
 */
public abstract class CountriesQuery {
    /**
     * This method handles the sql query to return all countries from the Countries table.
     *
     * @return ObservableArrayList countries.
     */
    //Method returns all countryId and countryNames in the Countries table using an observable list.
    public static ObservableList<Countries> selectAllCountries() {
        ObservableList<Countries> countries = FXCollections.observableArrayList();
        String sql = "SELECT * FROM COUNTRIES";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries cList = new Countries(countryId, countryName);
                countries.add(cList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countries;
    }


//    //Method inserts the entered country name into the Countries table.
//    public static void insertCountry(String countryName) {
//        String sql = "INSERT INTO COUNTRIES(Country) VALUES(?)";
//        try{
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setString(1, countryName);
//        ps.executeUpdate();
//    } catch (SQLException e) {
//           e.printStackTrace();
//       }
//    }
//
//    //Method updates the country name by the selected countryId field.
//    public static void updateCountry(int countryId, String countryName)  {
//        String sql = "UPDATE COUNTRIES SET Country = ? WHERE Country_ID = ?";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setString(1, countryName);
//            ps.setInt(2, countryId);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Method deletes the selected country by its countryId field.
//    public static void deleteCountry(int countryId) throws SQLException {
//        String sql = "DELETE FROM COUNTRIES WHERE Country_ID = ?";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setInt(1, countryId);
//            ps.executeUpdate();
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


//    public static int selectCountryIdByName(String countryName) {
//        int countryID = 0;
//        String sql = "Select Countries.Country_ID FROM Countries WHERE Country = ? VALUES(?)";
//        try{
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setString(1,countryName);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()){
//                int countryId = rs.getInt("Division_ID");
//                String country = rs.getString("Country");
//                countryID =countryId;
//                System.out.print(countryId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return countryID;
//
//    }

}

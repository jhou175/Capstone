package DBAccess;

import Database.JDBC;
import Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This FirstLevelDivisionQuery class handles all the SQL insert, update, delete, and select queries for the First_Level_Divisions table.
 * First_Level_Division do not need to be managed currently so the insert,update, and delete methods are commented out until they are needed.
 */
public abstract class FirstLevelDivisionQuery {

//    //Method inserts the entered division info into the First_Level_Division table
//    public static void insert(String divisionName, int countryId)  {
//        String sql = "INSERT INTO FIRST_LEVEL_DIVISIONS(Division , Country_ID) VALUES(?, ?)";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setString(1, divisionName);
//            ps.setInt(2, countryId);
//            ps.executeUpdate();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Method updates the division name by the selected divisionId field.
//    public static void update(int divisionId, String divisionName) {
//        String sql = "UPDATE FIRST_LEVEL_DIVISIONS SET Division = ? WHERE Division_ID = ?";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setString(1, divisionName);
//            ps.setInt(2, divisionId);
//            ps.executeUpdate();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Method deletes the selected division by its divisionId field.
//    public static void delete(int division_Id){
//        String sql = "DELETE FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
//        try {
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setInt(1, division_Id);
//            ps.executeUpdate();
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This method handles the sql query to return all divisions from the First_Level_Divisions table.
     *
     * @return ObservableArrayList divisions.
     */
    //Method returns all divisionId and divisionNames in the First_Level_Division table.
    public static ObservableList<FirstLevelDivisions> selectAllDivisions() {
        ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE first_level_division.Division_ID = customers.Division_Id ";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                FirstLevelDivisions dList = new FirstLevelDivisions(divisionId, divisionName, countryId);
                divisions.add(dList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }

    /**
     * This method handles the sql query to return all divisions from the First_Level_Divisions table.
     *
     * @param countryId Integer value for the countryId.
     * @return ObservableArrayList divisionCountry.
     */
    //Method returns all fields with the matching countryId field.
    public static ObservableList<FirstLevelDivisions> selectDivisionCountryId(int countryId) {
        ObservableList<FirstLevelDivisions> divisionCountry = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Country_ID = ?";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryIdFk = rs.getInt("Country_ID");
                FirstLevelDivisions countryIdList = new FirstLevelDivisions(divisionId, divisionName, countryIdFk);
                divisionCountry.add(countryIdList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionCountry;
    }

    /**
     * This method handles the sql query to return all First_Level_Divisions ids, names, and countryIds from the First_Level_Divisions table.
     *
     * @return ObservableArrayList divisions
     */
    public static ObservableList<FirstLevelDivisions> selectAllDivisionId() {
        ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                FirstLevelDivisions dList = new FirstLevelDivisions(divisionId, divisionName, countryId);
                divisions.add(dList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisions;
    }

//    public static int selectDivisionIDByName (String divisionName){
//        int divisionID = 0;
//        String sql = "Select FIRST_LEVEL_DIVISIONS.Division_Id FROM FIRST_LEVEL_DIVISIONS WHERE Division = ? VALUES(?)";
//        try{
//            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//            ps.setString(1,divisionName);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()){
//                int divisionId = rs.getInt("Division_ID");
//                //String divisionN = rs.getString("Division");
//                divisionID = divisionId;
//                System.out.print(divisionId);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return divisionID;
//    }
}

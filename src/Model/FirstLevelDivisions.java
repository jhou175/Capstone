package Model;

/**
 * This is the FirstLevelDivisions class that handles the constructor and getter methods.
 */
public class FirstLevelDivisions {
    private final int divisionId;
    private final String divisionName;
    private final int countryId;

    /**
     * This constructor initializes the divisionId,divisionName,and countryId.
     *
     * @param divisionId   The integer value for the divisionId.
     * @param divisionName The String name for the divisionName.
     * @param countryId    The integer value for the countryId.
     */
    public FirstLevelDivisions(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /**
     * This method retrieves the divisionId.
     *
     * @return The divisionId.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * This method retrieves the division name.
     *
     * @return The divisionName.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * This method retrieves the country id.
     *
     * @return The countryId.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * This overload method retrieves the firstLevelDivision object's name as a string.
     *
     * @return The divisionName
     */
    @Override
    public String toString() {
        return this.divisionName;
    }

}

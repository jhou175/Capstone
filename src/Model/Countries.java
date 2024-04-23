package Model;

/**
 * This is the Countries class that handles the countries constructor and getter methods.
 */
public class Countries {
    private int id;
    private String name;

    /**
     * This constrctor initilazes the countries id and name.
     *
     * @param id   The integer value for the country id.
     * @param name The String value for the country name.
     */
    public Countries(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This method retrieves the country id.
     *
     * @return The id for that country.
     */
    public int getId() {
        return id;
    }

    /**
     * This method retrieves the country name.
     *
     * @return The name for that country.
     */
    public String getName() {
        return name;
    }

    /**
     * This is an overload method to retrieve the country object's name as a String.
     *
     * @return The name of the country.
     */
    @Override
    public String toString() {
        return this.name;
    }
}

package Model;

/**
 * This is the Customers class and it handles the class constructors and all the getter methods for the class.
 */
public class Customers {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;
    private String divisionName;
    private String country;
    private int countryId;

    /**
     * This constructor overload initializes the customerId, customerName, address, postalCode, phone, divisionId, divisionName, country, and countryId.
     *
     * @param customerId   The integer value for the id of the customer.
     * @param customerName The String value of the customer.
     * @param address      The String value of the customer's address.
     * @param postalCode   The String value of the customer's postalCode.
     * @param phone        The String value of the customer's phone number.
     * @param divisionId   The integer value for the customer's divisionId.
     * @param divisionName The String value of the customer's division name.
     * @param country      The String value of the customer's country.
     * @param countryId    The integer value for the customer's countryId.
     */
    public Customers(int customerId, String customerName, String address, String postalCode, String phone, int divisionId, String divisionName, String country, int countryId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.country = country;
        this.countryId = countryId;
    }

    /**
     * This constructor initializes the customerId, customerName, address, postalCode, phone, and divisionId.
     *
     * @param customerId   The integer value for the id of the customer.
     * @param customerName The String value of the customer.
     * @param address      The String value of the customer's address.
     * @param postalCode   The String value of the customer's postalCode.
     * @param phone        The String value of the customer's phone number.
     * @param divisionId   The integer value for the customer's divisionId.
     */
    public Customers(int customerId, String customerName, String address, String postalCode, String phone, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * This constructor initializes the customerId and customerName.
     *
     * @param customerId   The customer's id.
     * @param customerName The customer's name.
     */
    public Customers(int customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }
    public Customers(int customerId){
        this.customerId = customerId;
    }


    /**
     * This method retrieves the customerId.
     *
     * @return The customerId.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * This method retrieves the customerName.
     *
     * @return The customerName.
     */
    public String getCustomerName() {
        return customerName;
    }


    /**
     * This method retrieves the customer's address.
     *
     * @return The address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method retrieves the customer's division name.
     *
     * @return The divisionName.
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * This method retrieves the customer's country.
     *
     * @return The country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method retrieves the customer's country id
     *
     * @return The countryId.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * This method retrieves the customer's postal code.
     *
     * @return The postalCode.
     */
    public String getPostalCode() {
        return postalCode;
    }


    /**
     * This method retrieves the customer's phone number.
     *
     * @return The phone.
     */
    public String getPhone() {
        return phone;
    }


    /**
     * This method retrieves the customer's division id.
     *
     * @return The divisionId.
     */
    public int getDivisionId() {
        return divisionId;
    }


    /**
     * This overloaded method retrieves and returns the Customer's id and name as a single string.
     *
     * @return The customerId and customerName
     */
    @Override
    public String toString() {
        return (customerId + " " + customerName);

    }
}

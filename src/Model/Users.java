package Model;

/**
 * This is the Users class and it handles the constructor and getter methods for the class.
 */
public class Users {
    private int userId;
    private String userName;
    private String password;

    /**
     * This constructor initializes the userId, userName, and password.
     *
     * @param userId   The integer value for the userId.
     * @param userName The String value for the userName.
     * @param password The String value for the password.
     */
    public Users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * This method retrieves the userId.
     *
     * @return The userId.
     */
    public int getUserId() {
        return userId;
    }


    /**
     * This method retrieves the userName.
     *
     * @return The userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method retrieves the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }


    /**
     * This overload method retrieves the user objects userId and name and returns a string.
     *
     * @return The string value of the userId and userName combined.
     */
    @Override
    public String toString() {
        return (userId + " " + userName);
    }

}

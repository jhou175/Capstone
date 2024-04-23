package Model;

/**
 * This is the Contacts class that handles the contact constructor and getter methods.
 */
public class Contacts {
    private int contactId;
    private String contactName;

    /**
     * The contacts constructor that initializes the contactId,contactName, and email.
     *
     * @param contactId   The integer value for the contactId.
     * @param contactName The String value for the contactName.
     */
    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * This method retrieves the contactId.
     *
     * @return The contactId.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * This method retrieves the contact name.
     *
     * @return The contactName.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * This method retrieves the contacts object data as a string.
     *
     * @return A string that consists of the contactId and contactName.
     */
    @Override
    public String toString() {
        return (contactId + " " + contactName);
    }

}

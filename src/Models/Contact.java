package Models;

/**
 * This class constructs and represents a contact
 */
public class Contact {
    private final int contactId;
    private final String contactName;
    private final String contactEmail;

    /**
     * Constructor for a contact
     * @param contactId Id of contact
     * @param contactName Name of contact
     * @param contactEmail Email of contact
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets the contact id of contact
     * @return Contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Gets the contact name of contact
     * @return Contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Gets the contact email of contact
     * @return Contact email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Overriden method for any calls of .toString()
     * @return Returns the contact name
     */
    @Override
    public String toString() {
        return contactName;
    }
}

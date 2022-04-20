package Models;

/**
 * This class represents/constructs a report object for customers with the same zip code
 */
public class CustomersWithSameZipCode {

    private String zipCode;
    private int count;

    /**
     * This is the constructor for a customer with same zip code reporting object
     *
     * @param zipCode Zip code that is reported from sql database
     * @param count   Count of that zip code from sql database
     */
    public CustomersWithSameZipCode(String zipCode, int count) {
        this.zipCode = zipCode;
        this.count = count;
    }

    /**
     * Gets the zip code
     *
     * @return Zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the zip code
     *
     * @param zipCode Zip code to be set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the count of zip code
     *
     * @return Count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count of zip code
     *
     * @param count Count to be set to
     */
    public void setCount(int count) {
        this.count = count;
    }
}

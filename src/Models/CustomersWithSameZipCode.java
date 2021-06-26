package Models;

public class CustomersWithSameZipCode {

    private String zipCode;
    private int count;

    /**
     * This is the constructor for a customer with same zip code reporting object
     * @param zipCode Zip code that is reported from sql database
     * @param count Count of that zip code from sql database
     */
    public CustomersWithSameZipCode(String zipCode, int count) {
        this.zipCode = zipCode;
        this.count = count;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

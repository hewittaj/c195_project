package Models;

public class Customer {
    private int customerId;
    private int divisionId;
    private String customerName;
    private String customerAddress;
    private String zipCode;
    private String phoneNumber;
    private String countryName;
    private String divisionName;

    /**
     * Constructor used for adding a Customer
     * @param customerId
     * @param customerName
     * @param customerAddress
     * @param zipCode
     * @param phoneNumber
     * @param countryName
     * @param divisionName
     */
    public Customer(int customerId, String customerName, String customerAddress, String zipCode, String phoneNumber, String countryName, String divisionName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.countryName = countryName;
        this.divisionName = divisionName;
    }

    /**
     * Constructor used for getting all customer information for main screen
     * @param customerId
     * @param customerName
     * @param customerAddress
     * @param zipCode
     * @param phoneNumber
     * @param divisionId
     */
    public Customer(int customerId, String customerName, String customerAddress, String zipCode, String phoneNumber, int divisionId) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getDivisionId(){
        return divisionId;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getDivisionName() {
        return divisionName;
    }


    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerAddress(){
        return customerAddress;
    }

    @Override
    public String toString() {
        return countryName;
    }
}

package Models;

/**
 * This class is used for representing and constructing a customer object
 */
public class Customer {
    private final int divisionId;
    private final String customerName;
    private final String customerAddress;
    private final String zipCode;
    private final String phoneNumber;
    private int customerId;
    private String countryName;
    private String divisionName;
    private String loggedInUser;

    /**
     * Constructor used for adding a Customer w/ logged in user info
     *
     * @param customerId      Id of customer
     * @param customerName    Name of customer
     * @param customerAddress Address of customer
     * @param zipCode         Zip code of customer
     * @param phoneNumber     Phone number of customer
     * @param countryName     Country name associated with customer
     * @param divisionId      Division id associated with customer, relates to country name
     * @param loggedInUser    Logged in user associated with customer
     */
    public Customer(int customerId, String customerName, String customerAddress, String zipCode, String phoneNumber, String countryName, int divisionId,
                    String loggedInUser) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.countryName = countryName;
        this.divisionId = divisionId;
        this.loggedInUser = loggedInUser;
    }

    /**
     * Constructor used for getting all customer information for main screen, no logged in user associated with this
     * constructor
     *
     * @param customerId      Id of customer
     * @param customerName    Name of customer
     * @param customerAddress Address of customer
     * @param zipCode         Zip code of customer
     * @param phoneNumber     Phone number of customer
     * @param divisionId      Division id associated with customer, relates to country name
     */
    public Customer(int customerId, String customerName, String customerAddress, String zipCode, String phoneNumber, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.divisionId = divisionId;
    }

    /**
     * Gets the customer id for customer
     *
     * @return Customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Gets the division id for customer
     *
     * @return Division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Gets the zip code for customer
     *
     * @return Zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Gets the phone number for customer
     *
     * @return Phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the country name associated with customer
     *
     * @return Country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Gets the division name associated with customer
     *
     * @return Division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Gets the logged in user associated with customer
     *
     * @return Logged in user
     */
    public String getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Gets the customer name
     *
     * @return Customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the customers address
     *
     * @return Custoer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Sets the id of the customer
     *
     * @param id Id to be set for customer
     */
    public void setId(int id) {
        this.customerId = id;
    }

    /**
     * Overriden .toString() method for any calls in program
     *
     * @return Customer name
     */
    @Override
    public String toString() {
        return customerName;
    }
}

package Models;

import DBAccess.DBCustomers;

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
     * Constructor used for adding a Customer
     *
     * @param customerId
     * @param customerName
     * @param customerAddress
     * @param zipCode
     * @param phoneNumber
     * @param countryName
     * @param divisionId
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
     * Constructor used for getting all customer information for main screen
     *
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

    /**
     * Gets the last customer Id number for adding a customer functionality
     *
     * @return The customer ID for the last customer in the list
     */
    public static int getLastCustomerId() {
        int maxId = 0;
        for (Customer customer : DBCustomers.getMainScreenCustomerInfo()) {
            if (customer.getCustomerId() > maxId) {
                maxId = customer.getCustomerId();
            }
        }
        return maxId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getDivisionId() {
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

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setId(int id) {
        this.customerId = id;
    }

    @Override
    public String toString() {
        return customerName;
    }
}

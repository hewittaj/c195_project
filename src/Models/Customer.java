package Models;

public class Customer {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private int zipCode;
    private String phoneNumber;
    private String countryName;
    private String divisionName;

    public Customer(int customerId, String customerName, String customerAddress, int zipCode, String phoneNumber, String countryName, String divisionName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.countryName = countryName;
        this.divisionName = divisionName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getZipCode() {
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

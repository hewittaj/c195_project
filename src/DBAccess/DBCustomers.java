package DBAccess;

import Database.DBConnection;
import Models.Customer;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DBCustomers {

    /**
     * This method adds a new customer to the database
     * @param newCustomer Parameter passed that represents the new customer
     */
    public static void addNewCustomer(Customer newCustomer){
        // Declare new customers information
        int customerId = newCustomer.getCustomerId();
        String customerName = newCustomer.getCustomerName();
        String address = newCustomer.getCustomerAddress();
        String zipCode = newCustomer.getZipCode();
        String phoneNumber = newCustomer.getPhoneNumber();
        int divisionId = newCustomer.getDivisionId();
        String loggedInUser = newCustomer.getLoggedInUser();

        try{
            // Set our sql string and prepared statement
            String sql = "insert into customers (Customer_ID, Customer_Name, Address, Postal_Code," +
                    "Phone, Division_ID, Created_By, Last_Updated_By) values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, customerName);
            ps.setString(3, address);
            ps.setString(4, zipCode);
            ps.setString(5, phoneNumber);
            ps.setInt(6, divisionId);
            ps.setString(7, loggedInUser);
            ps.setString(8, loggedInUser);

            // Execute the query
            ps.execute();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteCustomer(Customer selectedCustomer){
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, selectedCustomer.getCustomerId());

            ps.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * This method gets the populates the main screen's customer data
     * @return Returns an observable array list of customers in the database
     */
    public static ObservableList<Customer> getMainScreenCustomerInfo(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try{
            String sql = "select customer_id, customer_name, address, postal_code, phone, division_id from customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zipCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                Customer customer = new Customer(customerId, customerName, address, zipCode, phoneNumber, divisionId);

                // Add new user to our observable list
                customers.add(customer);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * This method handles the sql statements to update the selected customer
     * @param updatedCustomer Customer that is passed that includes the newly updated information
     * @return Returns the newly updated customer
     */
    public static void updateCustomer(Customer updatedCustomer){

        try{
            String sql = "UPDATE customers\n" +
                    "SET customer_name = ?, address = ?, postal_code = ?, phone = ?, \n" +
                    "last_updated_by = ?, division_id = ?, last_update = ?\n" +
                    "WHERE customer_id = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            // Set all of the question marks in the query to the related customer information
            ps.setString(1, updatedCustomer.getCustomerName());
            ps.setString(2, updatedCustomer.getCustomerAddress());
            ps.setString(3, updatedCustomer.getZipCode());
            ps.setString(4, updatedCustomer.getPhoneNumber());
            ps.setString(5, updatedCustomer.getLoggedInUser());
            ps.setInt(6, updatedCustomer.getDivisionId());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(8, updatedCustomer.getCustomerId());


            // Execute the query
            ps.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}

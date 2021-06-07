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

public class DBCustomers {

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
}

package Database;

import Password.Password;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Built from the following webinar
// https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=1be32ba5-76c6-47f3-8816-accf0002109b

/**
 * This class handles the connection to the database
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ07jtV";

    // This will build jdbc:mysql://wgudb.ucertify.com:3306/Wj07jtV
    private static final String jdbcUrl = protocol + vendorName + ipAddress + dbName;
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";

    private static final String username = "U07jtV";  // Username
    private static Connection conn = null;

    /**
     * This method starts the connection to the database
     *
     * @return Returns the connection to the database
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcUrl, username, Password.getPassword());

            System.out.println("Connection successful");
        } catch (SQLException e) {
            // System.out.print(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) { // Use PrintStackTrace for outputting exceptions
            // System.out.print(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * This method gets the current connection
     *
     * @return Returnst the connection
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * This method closes the connection to the database
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed successfully.");
        } catch (Exception e) { // Do Nothing

        }
    }
}

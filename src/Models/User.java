package Models;

/**
 * This class represents/constructs an object for a user
 */
public class User {
    private final int userId;
    private final String userName;
    private String password;

    /**
     * This method constructs a user with out their password
     *
     * @param userId   User id of user
     * @param userName Name of user
     */
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * This method constructs a user object with their password info
     *
     * @param userId   User id of user
     * @param userName Name of user
     * @param password Password of user
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Gets user id of user
     *
     * @return User id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets user name of user
     *
     * @return User name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets password of user
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Overridden method for any .toString() calls
     *
     * @return User name
     */
    @Override
    public String toString() {
        return userName;
    }

}

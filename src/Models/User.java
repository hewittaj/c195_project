package Models;

public class User {
    private final int userId;
    private final String userName;
    private String password;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return userName;
    }

}

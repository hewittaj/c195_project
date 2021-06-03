package Models;

public class User {
    private int userId;
    private String userName;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userName;
    }
}

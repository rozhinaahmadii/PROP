package Domain;

/**
 * Class that represents a user with a username and a password.
 * @author Jiahao Liu
 */
public class User {
    /** Username of the user. */
    public String username;
    /** Password of the user. */
    private String password;

    /**
     * Constructor for the User class with the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Method to return the username.
     * @return Returns the username.
     */
    public String getUser() {
        return username;
    }

    /**
     * Method to return the password.
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Compares the provided password with the user's password.
     *
     * @param password The password to compare.
     * @return true if the provided password matches the user's password, false otherwise.
     */
    public Boolean comparePassword(String password) {
        return this.password.equals(password);
    }
}

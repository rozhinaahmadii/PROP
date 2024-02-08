package DomainControllers;

import Domain.User;

import java.util.HashMap;

/**
 * Controller for managing users.
 * This controller handles the creation, deletion, login, and logout of users.
 * Users are stored in a data structure indexed by their usernames.
 * @author Jiahao Liu
 */
public class UserController {
    /** HashMap to store users by their usernames. */
    private HashMap<String, User> users;

    /**
     * Constructor for the UserController class. Initializes the user data structure.
     */
    public UserController() {
        users = new HashMap<>();
    }

    /**
     * Creates a new user and adds them to the user map if the username does not already exist.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @return 0 if the user is created successfully, 1 if the username is already in use,
     *         2 if the username or password is empty.
     */
    public int createUser(String username, String password) {
        if (users.containsKey(username)) return 1;
        else if (username.isEmpty() || password.isEmpty()) return 2;
        else {
            User u = new User(username, password);
            users.put(username, u);
        }
        return 0;
    }

    /**
     * Deletes a user from the user map.
     *
     * @param username The username of the user to delete.
     */
    public void deleteUser(String username) {
        users.remove(username);
    }

    /**
     * Logs in with a provided username and password.
     *
     * @param username The username of the user trying to log in.
     * @param password The password of the user trying to log in.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean logIn(String username, String password) {
        User u = users.get(username);
        return u != null && u.comparePassword(password);
    }

    /**
     * Sets the users for the controller.
     *
     * @param data Map with user data.
     */
    public void setUsers(HashMap<String, User> data) {
        users = new HashMap<>(data);
    }

    /**
     * Gets the users managed by the controller.
     *
     * @return Map of users.
     */
    public HashMap<String, User> getUsers() {
        return users;
    }
}

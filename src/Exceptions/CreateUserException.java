package Exceptions;

/**
 * An exception thrown when an error occurs during user creation.
 * This exception is thrown to indicate issues related to user creation, such as duplicate usernames
 * or empty fields.
 * @author Jiahao Liu
 */
public class CreateUserException extends Exception {

    /**
     * Constructs a new CreateUserException with a message indicating that the username already exists.
     *
     * @param name The username that already exists.
     */
    public CreateUserException(String name) {
        super("The username '" + name + "' already exists");
    }

    /**
     * Constructs a new CreateUserException with a message indicating that the fields are empty.
     */
    public CreateUserException() {
        super("The fields are empty");
    }
}


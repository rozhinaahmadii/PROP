package Exceptions;

/**
 * An exception thrown when a login attempt fails due to an invalid username or password.
 * This exception is thrown to indicate that the provided username or password is invalid during a login attempt.
 * @author Jiahao Liu
 */
public class LogInUserException extends Exception {

    /**
     * Constructs a new LogInUserException with a message indicating an invalid username or password.
     */
    public LogInUserException() {
        super("Invalid username or password");
    }
}

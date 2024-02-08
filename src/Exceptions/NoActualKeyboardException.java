package Exceptions;

/**
 * An exception thrown when there is no active keyboard.
 * This exception is thrown to indicate that an operation requiring an active keyboard was attempted, but no keyboard is currently open.
 * @author Esther Lozano
 */
public class NoActualKeyboardException extends Exception {

    /**
     * Constructs a new NoActualKeyboardException with a message indicating the absence of an open keyboard.
     */
    public NoActualKeyboardException() {
        super("There is no active keyboard");
    }
}

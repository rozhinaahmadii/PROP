package Exceptions;

/**
 * An exception thrown when there are no saved keyboards in the system.
 * This exception is thrown to indicate that there are no saved keyboards available in the system when attempting to access them.
 * @author Esther Lozano
 */
public class EmptyKeyboardsSavedException extends Exception {

    /**
     * Constructs a new EmptyKeyboardsSavedException with a message indicating that there are no saved keyboards.
     */
    public EmptyKeyboardsSavedException() {
        super("There are no saved keyboards");
    }
}


package Exceptions;

/**
 * An exception thrown when attempting to create a saved keyboard with a duplicate name.
 * This exception is thrown to indicate that an attempt was made to create a saved keyboard with a name that already exists.
 * @author Esther Lozano
 */
public class SavedKeyboardException extends Exception {

    /**
     * Constructs a new SavedKeyboardException with a message indicating the duplication of the keyboard name.
     *
     * @param name The name of the keyboard that already exists.
     */
    public SavedKeyboardException(String name) {
        super("The keyboard with the name '" + name + "' already exists");
    }
}


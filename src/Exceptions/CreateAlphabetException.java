package Exceptions;

/**
 * An exception thrown when attempting to create an alphabet that already exists.
 * This exception is thrown to indicate that an attempt was made to create an alphabet
 * with a name that already exists in the system.
 * @author Rozhina Ahmadi
 */
public class CreateAlphabetException extends Exception {
    /**
     * Constructs a new CreateAlphabetException with a specific alphabet name.
     *
     * @param name The name of the alphabet that already exists.
     */
    public CreateAlphabetException(String name) {
        super("The alphabet with the name '" + name + "' already exists");
    }
}


package Exceptions;

/**
 * An exception thrown when an attempt is made to create an alphabet with no characters.
 * This exception is thrown to indicate that an alphabet cannot be created because it is empty.
 * @author Rozhina Ahmadi
 */
public class EmptyAlphabetException extends Exception {

    /**
     * Constructs a new EmptyAlphabetException with a message indicating an empty alphabet.
     */
    public EmptyAlphabetException() {
        super("There is no alphabet");
    }
}

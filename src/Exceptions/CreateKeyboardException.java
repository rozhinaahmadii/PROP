package Exceptions;

/**
 * An exception thrown when attempting to create a keyboard with too many letters in the alphabet.
 * This exception is thrown to indicate that an attempt was made to create a keyboard with an alphabet
 * containing more letters than allowed based on the specified number of rows and columns.
 * @author Esther Lozano
 */
public class CreateKeyboardException extends Exception {

    /**
     * Constructs a new CreateKeyboardException with the calculated number of letters that exceed the limit.
     *
     * @param numRows The number of rows in the keyboard layout.
     * @param numCols The number of columns in the keyboard layout.
     */
    public CreateKeyboardException(int numRows, int numCols) {
        super("The keyboard's alphabet has more than " + numRows * numCols + " letters");
    }
}


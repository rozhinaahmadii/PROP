package Exceptions;

/**
 * An exception thrown when an error occurs while creating a word list from a file.
 * This exception is thrown to indicate issues related to word list creation from a file, such as
 * incorrect file format or non-numeric frequency values.
 * @author Bruno Ruano
 */
public class CreateWordListFromFile extends Exception {

    /**
     * Constructs a new CreateWordListFromFile exception with a message indicating an incorrect file format.
     *
     * @param length The length of the parameters in the file row.
     */
    public CreateWordListFromFile(int length) {
        super("The file does not contain exactly 2 parameters per row; some rows have " + length + " parameters");
    }

    /**
     * Constructs a new CreateWordListFromFile exception with a message indicating non-numeric frequency values.
     */
    public CreateWordListFromFile() {
        super("Some frequency values in the file are not numerical");
    }
}


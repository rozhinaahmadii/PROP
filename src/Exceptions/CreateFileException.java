package Exceptions;

/**
 * An exception thrown when attempting to create a file that already exists or when there are no words available.
 * This exception is thrown to indicate that an attempt was made to create a file that already exists
 * or when there are no words available for the operation.
 * @author Bruno Ruano
 */
public class CreateFileException extends Exception {

    /**
     * Constructs a new CreateFileException with a specific file name that already exists.
     *
     * @param name The name of the file that already exists.
     */
    public CreateFileException(String name) {
        super("The file with the name '" + name + "' already exists");
    }

    /**
     * Constructs a new CreateFileException when there are no words available.
     */
    public CreateFileException() {
        super("There are no words available");
    }
}


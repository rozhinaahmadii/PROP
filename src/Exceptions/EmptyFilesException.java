package Exceptions;

/**
 * An exception thrown when an attempt is made to access files that are not assigned to an alphabet.
 * This exception is thrown to indicate that a file operation is attempted on an alphabet that is not assigned to any files.
 * @author Jiahao Liu
 */
public class EmptyFilesException extends Exception {

    /**
     * Constructs a new EmptyFilesException with a message indicating that the alphabet with a specific name is not assigned to any files.
     *
     * @param name The name of the alphabet.
     */
    public EmptyFilesException(String name) {
        super("The alphabet with name '" + name + "' is not assigned to any files");
    }
}


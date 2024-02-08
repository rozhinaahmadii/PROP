package Exceptions;

/**
 * An exception thrown when there are no texts available.
 * This exception is thrown to indicate that there are no texts available in the system when attempting to access them.
 * @author Bruno Ruano
 */
public class EmptyTextsException extends Exception {

    /**
     * Constructs a new EmptyTextsException with a message indicating that there are no texts available.
     */
    public EmptyTextsException(){
        super("There are no available texts");
    }
}


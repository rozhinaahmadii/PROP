package Domain;

import java.util.ArrayList;

/**
 * Class that represents a saved keyboard with its design and associated attributes.
 * Inherits from the Keyboard class.
 * @author Esther Lozano
 */
public class KeyboardSaved extends Keyboard {
    /** Name of the saved keyboard. */
    public String name;

    /**
     * Constructor for the KeyboardSaved class.
     *
     * @param id       Unique identifier of the saved keyboard.
     * @param numRows  Number of rows in the saved keyboard.
     * @param numCols  Number of columns in the saved keyboard.
     * @param size     Size of the keys on the saved keyboard.
     * @param layout   Layout of the saved keyboard represented as a list of integers.
     * @param user     Username associated with the saved keyboard.
     * @param alphabet Alphabet associated with the saved keyboard.
     * @param name     Name of the saved keyboard.
     */
    public KeyboardSaved(int id, int numRows, int numCols, float size, ArrayList<Integer> layout, String user,
                         String alphabet, String name) {
        super(id, numRows, numCols, size, layout, user, alphabet);
        this.name = name;
    }
}

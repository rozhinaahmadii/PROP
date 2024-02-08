package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Class that represents a keyboard with its design and associated attributes.
 * @author Esther Lozano
 */
public class Keyboard {
    /** Unique identifier of the keyboard for user.  */
    public int id;
    /** Number of rows in the keyboard. */
    public int numRows;
    /** Number of columns in the keyboard. */
    public int numCols;
    /** Size of the keys on the keyboard. */
    public float sizeKey;
    /** Layout of the keyboard represented as a list of integers. */
    public ArrayList<Integer> layout;
    /** Username associated with the keyboard. */
    private String username;
    /** Alphabet associated with the keyboard. */
    private String alphabet;

    /**
     * Constructor for the Keyboard class for a keyboard without a specified design.
     *
     * @param id       Unique identifier of the keyboard.
     * @param numRows  Number of rows in the keyboard.
     * @param numCols  Number of columns in the keyboard.
     * @param size     Size of the keys on the keyboard.
     * @param user     Username associated with the keyboard.
     * @param alphabet Alphabet associated with the keyboard.
     */
    public Keyboard(int id, int numRows, int numCols, float size, String user, String alphabet) {
        this.id = id;
        this.numRows = numRows;
        this.numCols = numCols;
        this.sizeKey = size;
        this.username = user;
        this.alphabet = alphabet;
        this.layout = new ArrayList<>();
    }

    /**
     * Constructor for the Keyboard class for a keyboard with a specified design.
     *
     * @param id       Unique identifier of the keyboard.
     * @param numRows  Number of rows in the keyboard.
     * @param numCols  Number of columns in the keyboard.
     * @param size     Size of the keys on the keyboard.
     * @param layout   Layout of the keyboard represented as a list of integers.
     * @param user     Username associated with the keyboard.
     * @param alphabet Alphabet associated with the keyboard.
     */
    public Keyboard(int id, int numRows, int numCols, float size, ArrayList<Integer> layout,
                    String user, String alphabet) {
        this.id = id;
        this.numRows = numRows;
        this.numCols = numCols;
        this.sizeKey = size;
        this.layout = layout;
        this.username = user;
        this.alphabet = alphabet;
    }

    /**
     * Gets the number of rows associated with the keyboard.
     *
     * @return Number of rows of the keyboard.
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Gets the number of columns of the keyboard.
     *
     * @return Number of columns of the keyboard.
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Gets the size of the keys on the keyboard.
     *
     * @return Size of the keys on the keyboard.
     */
    public float getSizeKey() {
        return sizeKey;
    }

    /**
     * Gets the layout of the keyboard.
     *
     * @return Layout of the keyboard.
     */
    public ArrayList<Integer> getLayout() {
        return new ArrayList<>(layout);
    }

    /**
     * Gets the username associated with the keyboard.
     *
     * @return Username associated with the keyboard.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the alphabet associated with the keyboard.
     *
     * @return Alphabet associated with the keyboard.
     */
    public String getAlphabet() {
        return alphabet;
    }

    /**
     * Edits the keyboard layout by swapping the position of two keys.
     *
     * @param x1 X coordinate of the first key.
     * @param y1 Y coordinate of the first key.
     * @param x2 X coordinate of the second key.
     * @param y2 Y coordinate of the second key.
     */
    public void editLayout(int x1, int y1, int x2, int y2) {
        int t1 = y1 + x1 * this.numCols;
        int t2 = y2 + x2 * this.numCols;
        Integer a = this.layout.get(t1);
        Integer b = this.layout.get(t2);

        this.layout.set(t1, b);
        this.layout.set(t2, a);
    }

    /**
     * Assigns a random position to the letters of the associated alphabet.
     *
     * @param sizeAlphabet Size of the alphabet.
     */
    public void generateRandomLayout(int sizeAlphabet) {
        for (int i = 0; i < sizeAlphabet; ++i) {
            this.layout.add(i);
        }
        for (int i = sizeAlphabet; i < this.numRows * this.numCols; ++i) {
            this.layout.add(-1);
        }
        Collections.shuffle(this.layout);
    }

    /**
     * Assigns letters to the keyboard layout according to the provided alphabet.
     *
     * @param alphabet Set of letters of the alphabet.
     * @return List representing the keyboard layout with assigned letters.
     */
    public ArrayList<ArrayList<String>> assignLettersLayout(TreeSet<String> alphabet) {
        ArrayList<String> a = new ArrayList<>(alphabet);
        ArrayList<ArrayList<String>> k = new ArrayList<>();
        int pos = 0;
        for (int i = 0; i < this.numRows; ++i) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < this.numCols; ++j) {
                if (this.layout.size() > 0 && !this.layout.get(pos).equals(-1))
                    row.add(a.get(this.layout.get(pos)));
                else
                    row.add("");
                ++pos;
            }
            k.add(row);
        }
        return k;
    }
}

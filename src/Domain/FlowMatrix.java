package Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Class that represents a flow matrix and provides related operations.
 * @author Bruno Ruano
 */
public class FlowMatrix {
    /** Flow matrix represented as a list of lists of integers. */
    public ArrayList<ArrayList<Integer>> matrix;

    /**
     * Constructor for the FlowMatrix class. Initializes the flow matrix as an empty list.
     */
    public FlowMatrix() {
        this.matrix = new ArrayList<>();
    }

    /**
     * Adds the current matrix with another given matrix element by element.
     *
     * @param m Matrix to be added.
     */
    public void sumMatrix(ArrayList<ArrayList<Integer>> m) {
        for (int i = 0; i < this.matrix.size(); ++i) {
            for (int j = 0; j < this.matrix.get(i).size(); ++j) {
                Integer sum = this.matrix.get(i).get(j) + m.get(i).get(j);
                this.matrix.get(i).set(j, sum);
            }
        }
    }

    /**
     * Initializes the matrix with the size of the alphabet and fills it with zeros.
     *
     * @param sizeAlphabet Size of the alphabet.
     */
    public void initializeMatrix(int sizeAlphabet) {
        this.matrix = new ArrayList<>();
        for (int i = 0; i < sizeAlphabet; ++i) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < sizeAlphabet; j++) {
                row.add(0);
            }
            this.matrix.add(row);
        }
    }

    /**
     * Calculates the transitions of the flow matrix based on a map of words and a set of characters.
     *
     * @param MapW Map associating words with their frequencies.
     * @param s    Set of characters (alphabet).
     */
    public void calculateTransitions(HashMap<String, Integer> MapW, TreeSet<String> s) {
        ArrayList<String> alphabet = new ArrayList<>(s);
        for (Map.Entry<String, Integer> entry : MapW.entrySet()) {
            String word = entry.getKey();
            for (int i = 0; i < word.length(); i++) {
                if (i == 0) {
                    int pos1 = alphabet.indexOf(" ");
                    int pos2 = alphabet.indexOf(String.valueOf(word.charAt(i)));
                    int actual_value = this.matrix.get(pos1).get(pos2);
                    this.matrix.get(pos1).set(pos2, actual_value + entry.getValue());
                } else {
                    int pos1 = alphabet.indexOf(String.valueOf(word.charAt(i - 1)));
                    int pos2 = alphabet.indexOf(String.valueOf(word.charAt(i)));
                    int actual_value = this.matrix.get(pos1).get(pos2);
                    this.matrix.get(pos1).set(pos2, actual_value + entry.getValue());
                }
                if (i == (word.length() - 1)) {
                    int pos1f = alphabet.indexOf(String.valueOf(word.charAt(i)));
                    int pos2f = alphabet.indexOf(" ");
                    int actual_value = this.matrix.get(pos1f).get(pos2f);
                    this.matrix.get(pos1f).set(pos2f, actual_value + entry.getValue());
                }
            }
        }
    }
}

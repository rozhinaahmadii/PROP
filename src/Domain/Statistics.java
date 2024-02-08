package Domain;

import java.util.ArrayList;

/**
 * Class that represents statistics related to the speed of a keyboard.
 * @author Esther Lozano
 */
public class Statistics {
    /** Speed of the keyboard. */
    public double velocityKeyboard;
    /** Name of the file associated with the statistics. */
    private String fileName;
    /** Identifier of the keyboard associated with the statistics. */
    private int keyboardId;
    /** Username associated with the statistics. */
    private String username;

    /**
     * Constructor for the Statistics class.
     *
     * @param fileName   Name of the file associated with the statistics.
     * @param keyboardId Identifier of the keyboard associated with the statistics.
     * @param username   Username associated with the statistics.
     */
    public Statistics(String fileName, int keyboardId, String username) {
        this.fileName = fileName;
        this.keyboardId = keyboardId;
        this.username = username;
    }

    /**
     * Gets the name of the file associated with the statistics.
     *
     * @return Name of the file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Gets the identifier of the keyboard associated with the statistics.
     *
     * @return Identifier of the keyboard.
     */
    public int getKeyboardId() {
        return keyboardId;
    }

    /**
     * Calculates the speed of the keyboard based on flow and distance matrices.
     *
     * @param f       Flow matrix.
     * @param d       Distance matrix.
     * @param layout  Layout of the keyboard.
     * @param sizeKey Size of the keys on the keyboard.
     */
    public void calculateVelocity(FlowMatrix f, DistanceMatrix d, ArrayList<Integer> layout, float sizeKey) {
        float cost = 0;
        for (int t1 = 0; t1 < d.matrix.size(); t1++) {
            for (int t2 = 0; t2 < d.matrix.size(); t2++) {
                int x = layout.get(t1);
                int y = layout.get(t2);
                if (x != -1 && y != -1) {
                    cost += sizeKey * d.matrix.get(t1).get(t2) * f.matrix.get(x).get(y);
                }
            }
        }
        this.velocityKeyboard = cost / f.matrix.size();
    }
}

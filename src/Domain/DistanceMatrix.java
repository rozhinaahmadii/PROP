package Domain;

import java.util.ArrayList;
import java.lang.Math;

/**
 * Class that represents a distance matrix and provides operations related to it.
 * @author Esther Lozano
 */
public class DistanceMatrix {
    /** Distance matrix represented as a list of lists of decimal numbers. */
    public ArrayList<ArrayList<Double>> matrix;

    /**
     * Constructor of the DistanceMatrix class. Initializes the distance matrix as an empty list.
     */
    public DistanceMatrix() {
        this.matrix = new ArrayList<>();
    }

    /**
     * Initializes the matrix with the size of the keyboard keys and fills it with zeros.
     *
     * @param numRows Number of rows of the keyboard.
     * @param numCols Number of columns of the keyboard.
     */
    public void initializeMatrix(int numRows, int numCols) {
        this.matrix = new ArrayList<>();
        for(int i = 0; i < numRows * numCols; ++i) {
            ArrayList<Double> row = new ArrayList<>();
            for(int j = 0; j < numRows * numCols; j++) {
                row.add(0.0);
            }
            this.matrix.add(row);
        }
    }

    /**
     * Calculates the distance matrix based on the number of rows and columns in the keyboard layout.
     *
     * @param numRows Number of rows in the keyboard layout.
     * @param numCols Number of columns in the keyboard layout.
     */
    public void calculateDistanceMatrix(int numRows, int numCols) {
        int numKeys = numRows * numCols;

        for (int i = 0; i < numKeys; ++i) {
            // Calculation of the row and column of the first key
            int actual_row_t1 = i / numCols;
            int actual_col_t1 = i % numCols;
            for (int j = 0; j < numKeys; ++j) {
                // Calculation of the row and column of the second key
                int actual_row_t2 = j / numCols;
                int actual_col_t2 = j % numCols;

                if (i == j) {
                    matrix.get(i).set(j, 0.0);
                } else if (j > i) {
                    // Calculation of the distance between key 1 and key 2 = sqrt(|t2.x-t1.x|^2+|t2.y-t1.y|^2)
                    double dis_t1_t2 = Math.sqrt(Math.pow(Math.abs(actual_row_t2 - actual_row_t1), 2) + Math.pow(Math.abs(actual_col_t2 - actual_col_t1), 2));
                    matrix.get(i).set(j, dis_t1_t2);
                    matrix.get(j).set(i, dis_t1_t2);
                }
            }
        }
    }
}

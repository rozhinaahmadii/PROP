package DomainControllers;

import Domain.*;

import java.util.*;

/**
 * Controller for executing key assignment algorithms.
 * @author Rozhina Ahmadi, Jiahao Liu, Esther Lozano, and Bruno Ruano
 */
public class AlgorithmController {
    /** HashMap to store algorithms that calculate the keyboard layout. */
    private HashMap<String, Algorithm> algorithms;
    /** Name of the algorithm that is being applied. */
    private String algorithm;

    /**
     * Default constructor for the AlgorithmController class.
     */
    public AlgorithmController() {
        algorithms = new HashMap<>();
        algorithm = "";
    }

    /**
     * Initializes the algorithm with distance and flow matrices.
     *
     * @param a         Set of characters (alphabet).
     * @param wl        List of word maps with their frequencies.
     * @param numRows   Number of rows in the matrix.
     * @param numCols   Number of columns in the matrix.
     * @param algorithm Type of algorithm (1 for Branch and Bound).
     */
    public void initializeAlgorithm(TreeSet<String> a, ArrayList<HashMap<String, Integer>> wl,
                                    int numRows, int numCols, String algorithm) {
        DistanceMatrix dm = new DistanceMatrix();
        dm.initializeMatrix(numRows, numCols);
        dm.calculateDistanceMatrix(numRows, numCols);
        FlowMatrix fm = new FlowMatrix();
        fm.initializeMatrix(a.size());
        fm.calculateTransitions(wl.get(0), a);
        for(int i = 1; i < wl.size(); ++i) {
            FlowMatrix fm2 = new FlowMatrix();
            fm2.initializeMatrix(a.size());
            fm2.calculateTransitions(wl.get(i), a);
            fm.sumMatrix(fm2.matrix);
        }
        if(Objects.equals(algorithm, "Branch and Bound")) {
            BranchAndBound branchAndBound = new BranchAndBound(dm, fm);
            algorithms.put(algorithm, branchAndBound);
        }
        else if(Objects.equals(algorithm, "Evolutive")) {
            Evolutive evolutive = new Evolutive(dm, fm);
            algorithms.put(algorithm, evolutive);
        }
        this.algorithm = algorithm;
    }

    /**
     * Solves the key assignment problem using the algorithm.
     *
     * @return List of integers representing the best assignment found.
     */
    public ArrayList<Integer> solve() {
        algorithms.get(algorithm).solve();
        return algorithms.get(algorithm).bestAssignment;
    }
}

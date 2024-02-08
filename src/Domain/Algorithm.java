package Domain;

import java.util.ArrayList;

/**
 * Parent class for key assignment algorithms on a keyboard.
 * @author Rozhina Ahmadi, Jiahao Liu, Esther Lozano, and Bruno Ruano
 */
public class Algorithm {
    /** Distance matrix used by the algorithm. */
    public DistanceMatrix distanceMatrix;
    /** Flow matrix used by the algorithm. */
    public FlowMatrix flowMatrix;
    /** Best assignment found during the execution of the algorithm. */
    public ArrayList<Integer> bestAssignment;

    /**
     * Constructor for the Algorithm class.
     *
     * @param dm Distance matrix used by the algorithm.
     * @param fm Flow matrix used by the algorithm.
     */
    public Algorithm(DistanceMatrix dm, FlowMatrix fm) {
        this.distanceMatrix = dm;
        this.flowMatrix = fm;
    }

    /**
     * Method that should be implemented by child classes to solve the assignment problem.
     */
    public void solve(){}
}

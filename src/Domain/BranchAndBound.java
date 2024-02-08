package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Class that implements the Branch and Bound algorithm for optimal key assignment on a keyboard.
 * Subclass of the {@link Algorithm} class.
 * This class uses the Branch and Bound algorithm to solve the problem of optimal key assignment on a keyboard.
 * Inherits from the {@link Algorithm} class and provides specific implementations for the Branch and Bound algorithm.
 * @author Rozhina Ahmadi, Jiahao Liu, Esther Lozano, and Bruno Ruano
 */
public class BranchAndBound extends Algorithm {
    /** Best cost found during the execution of the algorithm. */
    private double bestCost;
    /** Priority queue that stores live nodes sorted by their cost from lowest to highest. */
    private PriorityQueue<Node> queue;

    /**
     * Constructor for the BranchAndBound class.
     *
     * @param dm Distance matrix used by the algorithm.
     * @param fm Flow matrix used by the algorithm.
     */
    public BranchAndBound(DistanceMatrix dm, FlowMatrix fm) {
        super(dm, fm);
        queue = new PriorityQueue<>();
    }

    /**
     * Solves the key assignment problem using the Branch and Bound algorithm.
     */
    public void solve() {
        bestCost = Double.MAX_VALUE;
        bestAssignment = new ArrayList<>();
        ArrayList<Integer> currentAssignment = new ArrayList<>(Collections.nCopies(distanceMatrix.matrix.size(), -1));
        queue.add(new Node(0, currentAssignment, 0));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            branchAndBound(node.level, node.assignment, node.cost);
        }
    }

    /**
     * Private method that implements a backtracking algorithm.
     *
     * @param level Current level of the search tree.
     * @param currentAssignment Current key assignment.
     * @param currentCost Accumulated cost up to the current level.
     */
    private void branchAndBound(int level, ArrayList<Integer> currentAssignment, double currentCost) {
        int count = 0;
        for(int l = 0; l < flowMatrix.matrix.size(); ++l) {
            if(currentAssignment.contains(l)) ++count;
        }
        if (level == distanceMatrix.matrix.size()) {
            if (currentCost < bestCost) {
                bestCost = currentCost;
                bestAssignment = new ArrayList<>(currentAssignment);
            }
            return;
        } else if(count == flowMatrix.matrix.size()) {
            if (currentCost < bestCost) {
                bestCost = currentCost;
                bestAssignment = new ArrayList<>(currentAssignment);
            }
            return;
        }
        for (int i = 0; i < flowMatrix.matrix.size(); i++) {
            if (!currentAssignment.contains(i)) {
                currentAssignment.set(level, i);
                double lowerBound = calculateLowerBound(currentAssignment);
                if (lowerBound < bestCost) {
                    queue.add(new Node(level + 1, currentAssignment, lowerBound));
                }
                currentAssignment.set(level, -1);
            }
        }
    }

    /**
     * Calculates a lower bound of the cost of the reachable solutions from the current assignment.
     * Uses the greedy algorithm to complete the missing key assignment and returns the resulting cost.
     *
     * @param assignment Current key assignment.
     * @return Lower bound of the cost of the solutions.
     */
    private double calculateLowerBound(ArrayList<Integer> assignment) {
        ArrayList<Integer> greedyAssignment = new ArrayList<>(assignment);
        completeAssignment(greedyAssignment);
        return calculateCost(greedyAssignment);
    }

    /**
     * Completes the missing key assignments using the greedy algorithm.
     *
     * @param assignment Current key assignment.
     */
    private void completeAssignment(ArrayList<Integer> assignment) {
        for (int t1 = 0; t1 < distanceMatrix.matrix.size(); t1++) {
            if (assignment.get(t1) == -1) {
                double minCost = Double.MAX_VALUE;
                int bestKey = -1;
                for (int k = 0; k < flowMatrix.matrix.size(); k++) {
                    if (!assignment.contains(k)) {
                        double cost = 0;
                        for (int t2 = 0; t2 < distanceMatrix.matrix.size(); t2++) {
                            int x = assignment.get(t2);
                            if (x != -1) {
                                cost += distanceMatrix.matrix.get(t1).get(t2) * flowMatrix.matrix.get(k).get(x);
                            }
                        }
                        if (cost < minCost) {
                            minCost = cost;
                            bestKey = k;
                        }
                    }
                }
                assignment.set(t1, bestKey);
            }
        }
    }

    /**
     * Calculates the cost of a given assignment.
     *
     * @param assignment Current key assignment.
     * @return Cost of the assignment.
     */
    private double calculateCost(ArrayList<Integer> assignment) {
        double cost = 0;
        for (int t1 = 0; t1 < distanceMatrix.matrix.size(); t1++) {
            for (int t2 = 0; t2 < distanceMatrix.matrix.size(); t2++) {
                int x = assignment.get(t1);
                int y = assignment.get(t2);
                if (x != -1 && y != -1) {
                    cost += distanceMatrix.matrix.get(t1).get(t2) * flowMatrix.matrix.get(x).get(y);
                }
            }
        }
        return cost;
    }

    /**
     * Inner class representing a node in the search tree.
     * Implements the Comparable interface to order the nodes by their cost.
     */
    private class Node implements Comparable<Node> {
        /** Level of the node in the tree. */
        int level;

        /** Key assignment associated with the node. */
        ArrayList<Integer> assignment;

        /** Estimated cost of the node. */
        double cost;

        /**
         * Constructor for the Node class.
         *
         * @param level Level of the node in the tree.
         * @param assignment Key assignment associated with the node.
         * @param cost Estimated cost of the node.
         */
        public Node(int level, ArrayList<Integer> assignment, double cost) {
            this.level = level;
            this.assignment = new ArrayList<>(assignment);
            this.cost = cost;
        }

        /**
         * Method to compare two nodes by their cost.
         *
         * @param other The other node to compare with.
         * @return A negative value if this node's cost is less than the other's, zero if they are equal, or a positive value if it is greater.
         */
        @Override
        public int compareTo(Node other) {
            return Double.compare(this.cost, other.cost);
        }
    }
}

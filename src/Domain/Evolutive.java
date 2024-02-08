package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class that implements an evolutionary algorithm for optimal key assignment on a keyboard.
 * It is a subclass of {@link Algorithm}.
 * Uses the approach of evolutionary algorithms to solve the problem of optimal key assignment.
 * Inherits from {@link Algorithm} and provides specific implementations for the evolutionary algorithm.
 * @author Rozhina Ahmadi, Jiahao Liu, Esther Lozano, and Bruno Ruano
 */
public class Evolutive extends Algorithm {
    /** Crossover probability in the genetic algorithm. */
    private static final double CROSSOVER_PROBABILITY = 0.5;
    /** Probability of crossover followed by mutation. */
    private static final double CROSSOVER_THEN_MUTATION_PROBABILITY = 0.7;
    /** Probability of permutation in the genetic algorithm. */
    private static final double PERMUTATION_PROBABILITY = 0.4;
    /** Probability of inversion in the genetic algorithm. */
    private static final double INVERSION_PROBABILITY = 0.2;
    /** Maximum number of individuals in the population. */
    private static final int MAXIMUM_POPULATION = 100;
    /** Maximum number of generations to run the algorithm. */
    private static final int MAXIMUM_GENERATION = 300;
    /** Random number generator for stochastic operations. */
    private static Random random;
    /** Current population of individuals in the evolutionary algorithm. */
    private ArrayList<Individual> population;

    /**
     * Constructor for the evolutionary algorithm.
     *
     * @param dm Distance matrix.
     * @param fm Flow matrix.
     */
    public Evolutive(DistanceMatrix dm, FlowMatrix fm) {
        super(dm, fm);
        random = new Random();
    }

    /**
     * Constructor for the evolutionary algorithm with a seed for random number generation.
     *
     * @param dm   Distance matrix.
     * @param fm   Flow matrix.
     * @param seed Seed for random number generation.
     */
    public Evolutive(DistanceMatrix dm, FlowMatrix fm, long seed) {
        super(dm, fm);
        random = new Random(seed);
    }

    /**
     * Implements the solve method of the base class to execute the evolutionary algorithm.
     */
    @Override
    public void solve() {
        iniPopulation();
        int h = 0;
        while(h <= MAXIMUM_GENERATION) {
            evolutePopulation();
            ++h;
        }
        this.bestAssignment = this.population.get(0).layout;
    }

    /**
     * Initializes the population for the evolutionary algorithm.
     */
    private void iniPopulation() {
        this.population = new ArrayList<>();
        for (int i = 0; i < MAXIMUM_POPULATION; i++) {
            ArrayList<Integer> layout = generateLayout(distanceMatrix.matrix.size());
            Individual individual = new Individual(layout, flowMatrix.matrix.size(), distanceMatrix.matrix.size());
            this.population.add(individual);
        }
    }

    /**
     * Generates a random keyboard layout.
     *
     * @param size The size of the flow matrix.
     * @return An ArrayList of integers representing the keyboard layout.
     */
    private ArrayList<Integer> generateLayout(int size) {
        ArrayList<Integer> layout = new ArrayList<>();
        for (int i = 0; i < flowMatrix.matrix.size(); i++) {
            layout.add(i);
        }
        for (int i = flowMatrix.matrix.size(); i < size; i++) {
            layout.add(-1);
        }
        Collections.shuffle(layout, random);
        return layout;
    }

    /**
     * Evolves the population through crossover, mutation, permutation, and inversion operations.
     */
    private void evolutePopulation() {
        ArrayList<Individual> newPopulation = new ArrayList<>();
        while (newPopulation.size() <= MAXIMUM_POPULATION) {
            for (int i = 0; i < this.population.size(); ++i) {
                int n = random.nextInt(MAXIMUM_POPULATION);
                while (n == i) n = random.nextInt(MAXIMUM_POPULATION);
                Individual son;
                if (random.nextDouble() <= CROSSOVER_THEN_MUTATION_PROBABILITY) {
                    son = this.population.get(i).crossover(this.population.get(n));
                    if (random.nextDouble() <= PERMUTATION_PROBABILITY) son.swapPositions();
                    if (random.nextDouble() <= INVERSION_PROBABILITY) son.inversionLayout();
                    son.calculateFitness(distanceMatrix, flowMatrix);
                    newPopulation.add(son);
                }
            }
        }
        ArrayList<Individual> allPopulation = new ArrayList<>(newPopulation);
        allPopulation.addAll(population);
        allPopulation.sort(Individual::compareTo);
        this.population = new ArrayList<>(allPopulation.subList(0, MAXIMUM_POPULATION));
    }

    /**
     * Inner class representing an individual in the population of the evolutionary algorithm.
     */
    private static class Individual {
        /**
         * Keyboard layout represented as a list of integers.
         */
        public ArrayList<Integer> layout;

        /**
         * Total number of keys on the keyboard.
         */
        public int numKeys;

        /**
         * Total number of letters assigned on the keyboard.
         */
        public int numLetters;

        /**
         * Fitness or cost associated with this keyboard layout.
         */
        public double fitness;

        /**
         * Constructor to create a new individual.
         * Initializes the individual with a specific keyboard layout and sets the number of letters and keys.
         *
         * @param l    List of integers representing the keyboard layout. Each integer represents a specific key.
         * @param numL Total number of letters to be assigned on the keyboard.
         * @param numK Total number of keys available on the keyboard.
         */
        Individual(ArrayList<Integer> l, int numL, int numK) {
            this.layout = l;
            this.numLetters = numL;
            this.numKeys = numK;
        }

        /**
         * Calculates the fitness of the individual based on the distance and flow matrices.
         *
         * @param distanceMatrix Distance matrix.
         * @param flowMatrix     Flow matrix.
         */
        public void calculateFitness(DistanceMatrix distanceMatrix, FlowMatrix flowMatrix) {
            double cost = 0;
            for (int t1 = 0; t1 < numKeys; t1++) {
                for (int t2 = 0; t2 < numKeys; t2++) {
                    int x = this.layout.get(t1);
                    int y = this.layout.get(t2);
                    if (x != -1 && y != -1) {
                        cost += distanceMatrix.matrix.get(t1).get(t2) * flowMatrix.matrix.get(x).get(y);
                    }
                }
            }
            this.fitness = cost;
        }

        /**
         * Performs genetic crossover between this individual and another, creating an offspring.
         *
         * @param partner Another individual to cross with.
         * @return A new individual resulting from the crossover.
         */
        public Individual crossover(Individual partner) {
            ArrayList<Integer> child = new ArrayList<>(Collections.nCopies(layout.size(), -1));
            for (int i = 0; i < numLetters; i++) {
                if (random.nextDouble() <= CROSSOVER_PROBABILITY) {
                    if (child.get(layout.indexOf(i)).equals(-1)) {
                        child.set(layout.indexOf(i), i);
                    } else {
                        int j = layout.indexOf(i);
                        while (j < child.size() - 1 && !child.get(j).equals(-1)) {
                            ++j;
                        }
                        if (child.get(j).equals(-1)) {
                            child.set(j, i);
                        }
                    }
                } else {
                    if (child.get(partner.layout.indexOf(i)).equals(-1)) {
                        child.set(partner.layout.indexOf(i), i);
                    } else {
                        int j = partner.layout.indexOf(i);
                        while (j < child.size() - 1 && !child.get(j).equals(-1)) {
                            ++j;
                        }
                        if (child.get(j).equals(-1)) {
                            child.set(j, i);
                        }
                    }
                }
            }
            return new Individual(child, numLetters, numKeys);
        }

        /**
         * Performs a swap position operation on this individual's keyboard layout.
         */
        public void swapPositions() {
            int size = layout.size();
            int position1 = random.nextInt(size);
            int position2 = random.nextInt(size);
            while (position2 == position1) {
                position2 = random.nextInt(size);
            }
            int x = layout.get(position1);
            int y = layout.get(position2);
            this.layout.set(position1, y);
            this.layout.set(position2, x);
        }

        /**
         * Inverts this individual's keyboard layout.
         */
        public void inversionLayout() {
            Collections.reverse(this.layout);
        }

        /**
         * Compares this individual to another in terms of fitness.
         *
         * @param other Another individual to compare.
         * @return An integer value indicating the comparison.
         */
        public int compareTo(Individual other) {
            return Double.compare(this.fitness, other.fitness);
        }
    }
}

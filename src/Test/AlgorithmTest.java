package Test;

import Domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AlgorithmTest {

    private DistanceMatrix distanceMatrix;
    private FlowMatrix flowMatrix;
    private ArrayList<Integer> assigmentBranchAndBound;

    private ArrayList<Integer> assigmentEvolutive;

    @Before
    public void setUp() {

        // Creamos un alfabeto y una lista de palabras con frecuencias
        TreeSet<String> alphabet = new TreeSet<>(Arrays.asList("a", "b", "d", "e", "h", "l", "o", "u", "y", " "));
        HashMap<String, Integer> wordlist = new HashMap<String, Integer>() {{
            put("hello", 4);
            put("duel", 5);
            put("bye", 6);
            put("ball", 10);
            put("all", 9);
        }};

        // Creamos el resultado de la distribución que deberia dar el algoritmo
        assigmentBranchAndBound = new ArrayList<>(Arrays.asList(3, 4, 7, 0, 1, 8, 5, 9, 6, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1));
        assigmentEvolutive = new ArrayList<>(Arrays.asList(-1, 2, -1, 1, 7, 9, 0, -1, 6, -1, -1, -1, -1, 8, 4, -1, 5, -1, 3, -1));
        // Preparamos las matrices que ha de usar el algoritmo
        distanceMatrix = new DistanceMatrix();
        flowMatrix = new FlowMatrix();
        distanceMatrix.initializeMatrix(4, 5);
        distanceMatrix.calculateDistanceMatrix(4, 5);
        flowMatrix.initializeMatrix(alphabet.size());
        flowMatrix.calculateTransitions(wordlist, alphabet);

    }

    @Test
    public void algorithmConstructorTest() {

        // Creamos el algoritmo con sus matrices
        Algorithm algorithm = new Algorithm(distanceMatrix, flowMatrix);


        // Verificamos que se ha creado bien la estructura
        assertNotNull(algorithm);
        assertEquals(distanceMatrix, algorithm.distanceMatrix);
        assertEquals(flowMatrix, algorithm.flowMatrix);
    }

    @Test
    public void branchAndBoundSolveTest() {

        // Creamos el algoritmo que queremos aplicar
        BranchAndBound branchAndBound = new BranchAndBound(distanceMatrix, flowMatrix);

        // Hacemos que ejecute el algoritmo
        branchAndBound.solve();

        // Vereficamos que el resultado sea el correcto
        assertNotNull(branchAndBound.bestAssignment);
        assertEquals(assigmentBranchAndBound, branchAndBound.bestAssignment);
    }
    @Test
    public void evolutiveSolveTest() {
        // Fijamos la semilla para que el resultado sea reproducible
        long seed = 123456789L; // Puedes elegir cualquier número como semilla

        // Creamos la instancia de Evolutive con la semilla fija
        Evolutive evolutive = new Evolutive(distanceMatrix, flowMatrix, seed);
        evolutive.solve();

        // Ahora podemos verificar que la solución es la esperada
        assertNotNull(evolutive.bestAssignment);
        assertEquals(assigmentEvolutive, evolutive.bestAssignment);
    }
}

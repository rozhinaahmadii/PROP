package Test;

import Domain.DistanceMatrix;
import Domain.FlowMatrix;
import Domain.Statistics;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class StatisticsTest {

    private Statistics statistics;

    @Before
    public void setUp() {
        // Configuración inicial para las pruebas
        statistics = new Statistics("example.txt", 1, "user1");
    }

    @Test
    public void getFileName() {
        assertEquals("example.txt", statistics.getFileName());
    }

    @Test
    public void getKeyboardId() {
        assertEquals(1, statistics.getKeyboardId());
    }

    @Test
    public void calculateVelocity() {
        // Creamos instancias de matrices de flujo y distancia
        FlowMatrix flowMatrix = new FlowMatrix();
        DistanceMatrix distanceMatrix = new DistanceMatrix();

        // Creamos un alfabeto y una lista de palabras con frecuencias
        TreeSet<String> alphabet = new TreeSet<>(Arrays.asList("a", "b", "d", "e", "h", "l", "o", "u", "y", " "));
        HashMap<String, Integer> wordlist = new HashMap<String, Integer>() {{
            put("hello", 4);
            put("duel", 5);
            put("bye", 6);
            put("ball", 10);
            put("all", 9);
        }};

        // Inicializamos las matrices
        distanceMatrix.initializeMatrix(4,5);
        distanceMatrix.calculateDistanceMatrix(4, 5);
        flowMatrix.initializeMatrix(alphabet.size());
        flowMatrix.calculateTransitions(wordlist, alphabet);

        // Creamos un layout y un sizeKey
        ArrayList<Integer> layout = new ArrayList<>(Arrays.asList(2, 3, 5, 6, 8, 7, 9, 0, 1, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1));
        float sizeKey = 1.5f;

        // Calculamos la velocidad
        statistics.calculateVelocity(flowMatrix, distanceMatrix, layout, sizeKey);

        // Verificamos el resultado esperado (ajustar según la lógica específica)
        assertEquals(36.18279266357422, statistics.velocityKeyboard, 0.0);
    }
}

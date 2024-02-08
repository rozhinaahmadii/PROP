package Test;

import Domain.FlowMatrix;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class FlowMatrixTest {
    private FlowMatrix flowMatrix1;
    private FlowMatrix flowMatrix2;

    @Before
    public void setUp() {
        flowMatrix1 = new FlowMatrix();
        flowMatrix2 = new FlowMatrix();
    }
    @Test
    public void testInitializeMatrix() {
        // Inicializamos la matriz con un tamaño específico
        flowMatrix1.initializeMatrix(3);
        flowMatrix2.initializeMatrix(3);

        ArrayList<ArrayList<Integer>> expectedMatrix = new ArrayList<>();
        ArrayList<Integer> row1 = new ArrayList<>(List.of(0, 0, 0));
        ArrayList<Integer> row2 = new ArrayList<>(List.of(0, 0, 0));
        ArrayList<Integer> row3 = new ArrayList<>(List.of(0, 0, 0));
        expectedMatrix.add(row1);
        expectedMatrix.add(row2);
        expectedMatrix.add(row3);

        // Verificamos que la matriz tiene el tamaño correcto y está llena de ceros
        assertEquals(expectedMatrix, flowMatrix1.matrix);
        assertEquals(expectedMatrix, flowMatrix2.matrix);
    }
    @Test
    public void testCalculateTransitions() {
        HashMap<String, Integer> wordMap1 = new HashMap<>();
        wordMap1.put("a", 1);
        wordMap1.put("b", 2);

        HashMap<String, Integer> wordMap2 = new HashMap<>();
        wordMap2.put("a", 3);
        wordMap2.put("b", 4);

        TreeSet<String> alphabet = new TreeSet<>();
        alphabet.add(" ");
        alphabet.add("a");
        alphabet.add("b");

        // Calculamos las transiciones
        flowMatrix1.initializeMatrix(alphabet.size());
        flowMatrix2.initializeMatrix(alphabet.size());
        flowMatrix1.calculateTransitions(wordMap1, alphabet);
        flowMatrix2.calculateTransitions(wordMap2, alphabet);

        // Creamos las matrices de verificación
        ArrayList<ArrayList<Integer>> expectedMatrix1 = new ArrayList<>();
        ArrayList<Integer> row11 = new ArrayList<>(List.of(0, 1, 2));
        ArrayList<Integer> row12 = new ArrayList<>(List.of(1, 0, 0));
        ArrayList<Integer> row13 = new ArrayList<>(List.of(2, 0, 0));
        expectedMatrix1.add(row11);
        expectedMatrix1.add(row12);
        expectedMatrix1.add(row13);
        ArrayList<ArrayList<Integer>> expectedMatrix2 = new ArrayList<>();
        ArrayList<Integer> row21 = new ArrayList<>(List.of(0, 3, 4));
        ArrayList<Integer> row22 = new ArrayList<>(List.of(3, 0, 0));
        ArrayList<Integer> row23 = new ArrayList<>(List.of(4, 0, 0));
        expectedMatrix2.add(row21);
        expectedMatrix2.add(row22);
        expectedMatrix2.add(row23);

        // Verificamos que calcule bien las transiciones
        assertEquals(expectedMatrix1, flowMatrix1.matrix);
        assertEquals(expectedMatrix2, flowMatrix2.matrix);
    }
    @Test
    public void testSumMatrix() {
        HashMap<String, Integer> wordMap1 = new HashMap<>();
        wordMap1.put("a", 1);
        wordMap1.put("b", 2);

        HashMap<String, Integer> wordMap2 = new HashMap<>();
        wordMap2.put("a", 3);
        wordMap2.put("b", 4);

        TreeSet<String> alphabet = new TreeSet<>();
        alphabet.add(" ");
        alphabet.add("a");
        alphabet.add("b");

        // Calculamos las transiciones
        flowMatrix1.initializeMatrix(alphabet.size());
        flowMatrix2.initializeMatrix(alphabet.size());
        flowMatrix1.calculateTransitions(wordMap1, alphabet);
        flowMatrix2.calculateTransitions(wordMap2, alphabet);

        // Las sumamos
        flowMatrix1.sumMatrix(flowMatrix2.matrix);

        // Creamos la matriz de verificación
        ArrayList<ArrayList<Integer>> expectedMatrix = new ArrayList<>();
        ArrayList<Integer> row1 = new ArrayList<>(List.of(0, 4, 6));
        ArrayList<Integer> row2 = new ArrayList<>(List.of(4, 0, 0));
        ArrayList<Integer> row3 = new ArrayList<>(List.of(6, 0, 0));
        expectedMatrix.add(row1);
        expectedMatrix.add(row2);
        expectedMatrix.add(row3);

        // Verificamos que la suma se realizó correctamente
        assertEquals(expectedMatrix, flowMatrix1.matrix);
    }
}
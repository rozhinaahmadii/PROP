package Test;

import Domain.DistanceMatrix;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DistanceMatrixTest {

    private DistanceMatrix distanceMatrix1;
    private DistanceMatrix distanceMatrix2;

    @Before
    public void setUp() {
        distanceMatrix1 = new DistanceMatrix();
        distanceMatrix2 = new DistanceMatrix();
    }
    @Test
    public void testInitializeMatrix() {
        // Inicializamos la matriz con un tamaño específico
        distanceMatrix1.initializeMatrix(2, 2);
        distanceMatrix2.initializeMatrix(2, 3);

        ArrayList<ArrayList<Double>> expectedMatrix1 = new ArrayList<>();
        ArrayList<Double> row11 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> row12 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> row13 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> row14 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0));
        expectedMatrix1.add(row11);
        expectedMatrix1.add(row12);
        expectedMatrix1.add(row13);
        expectedMatrix1.add(row14);

        ArrayList<ArrayList<Double>> expectedMatrix2 = new ArrayList<>();
        ArrayList<Double> row21 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> row22 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> row23 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> row24 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> row25 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        ArrayList<Double> row26 = new ArrayList<>(List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        expectedMatrix2.add(row21);
        expectedMatrix2.add(row22);
        expectedMatrix2.add(row23);
        expectedMatrix2.add(row24);
        expectedMatrix2.add(row25);
        expectedMatrix2.add(row26);

        // Verificamos que la matriz tiene el tamaño correcto y está llena de ceros
        assertEquals(expectedMatrix1, distanceMatrix1.matrix);
        assertEquals(expectedMatrix2, distanceMatrix2.matrix);
    }
    @Test
    public void testCalculateDistanceMatrix() {
        // Caso de prueba con 2 filas y 2 columnas
        distanceMatrix1.initializeMatrix(2, 2);
        distanceMatrix1.calculateDistanceMatrix(2, 2);
        ArrayList<ArrayList<Double>> expectedMatrix1 = new ArrayList<>();
        ArrayList<Double> row11 = new ArrayList<>(List.of(0.0, 1.0, 1.0, Math.sqrt(2.0)));
        ArrayList<Double> row12 = new ArrayList<>(List.of(1.0, 0.0, Math.sqrt(2.0), 1.0));
        ArrayList<Double> row13 = new ArrayList<>(List.of(1.0, Math.sqrt(2.0), 0.0, 1.0));
        ArrayList<Double> row14 = new ArrayList<>(List.of(Math.sqrt(2.0), 1.0, 1.0, 0.0));
        expectedMatrix1.add(row11);
        expectedMatrix1.add(row12);
        expectedMatrix1.add(row13);
        expectedMatrix1.add(row14);
        assertEquals(expectedMatrix1, distanceMatrix1.matrix);

        // Caso de prueba con 2 filas y 3 columnas
        distanceMatrix2.initializeMatrix(2,3);
        distanceMatrix2.calculateDistanceMatrix(2, 3);
        ArrayList<ArrayList<Double>> expectedMatrix2 = new ArrayList<>();
        ArrayList<Double> row21 = new ArrayList<>(List.of(0.0, 1.0, 2.0, 1.0, Math.sqrt(2.0), Math.sqrt(5.0)));
        ArrayList<Double> row22 = new ArrayList<>(List.of(1.0, 0.0, 1.0, Math.sqrt(2.0), 1.0, Math.sqrt(2.0)));
        ArrayList<Double> row23 = new ArrayList<>(List.of(2.0, 1.0, 0.0, Math.sqrt(5.0), Math.sqrt(2.0), 1.0));
        ArrayList<Double> row24 = new ArrayList<>(List.of(1.0, Math.sqrt(2.0), Math.sqrt(5.0), 0.0, 1.0, 2.0));
        ArrayList<Double> row25 = new ArrayList<>(List.of(Math.sqrt(2.0), 1.0, Math.sqrt(2.0), 1.0, 0.0, 1.0));
        ArrayList<Double> row26 = new ArrayList<>(List.of(Math.sqrt(5.0), Math.sqrt(2.0), 1.0, 2.0, 1.0, 0.0));
        expectedMatrix2.add(row21);
        expectedMatrix2.add(row22);
        expectedMatrix2.add(row23);
        expectedMatrix2.add(row24);
        expectedMatrix2.add(row25);
        expectedMatrix2.add(row26);
        assertEquals(expectedMatrix2, distanceMatrix2.matrix);
    }
}

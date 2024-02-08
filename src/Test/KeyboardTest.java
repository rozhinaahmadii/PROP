package Test;

import Domain.Keyboard;
import Domain.KeyboardSaved;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class KeyboardTest {

    private Keyboard keyboard;
    private KeyboardSaved keyboardSaved;

    @Before
    public void setUp() {
        // Configuración inicial para las pruebas
        String nameAlphabet = "ABCD";
        TreeSet<String> alphabet = new TreeSet<>();
        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");
        alphabet.add("D");

        ArrayList<Integer> layout = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            layout.add(i);
        }

        keyboard = new Keyboard(1, 2, 2, 1.5f, layout, "user1", nameAlphabet);
        keyboardSaved = new KeyboardSaved(2, 3, 3, 1.5f, layout, "user2", nameAlphabet, "SavedKeyboard");
    }

    @Test
    public void getUsername() {
        assertEquals("user1", keyboard.getUsername());
        assertEquals("user2", keyboardSaved.getUsername());
    }

    @Test
    public void getAlphabet() {
        assertEquals("ABCD", keyboard.getAlphabet());
        assertEquals("ABCD", keyboardSaved.getAlphabet());
    }

    @Test
    public void createLayout() {
        // Creamos dos layouts para probar la clase
        keyboard.layout = new ArrayList<>(Arrays.asList(0,1,2,3));
        keyboardSaved.layout = new ArrayList<>(Arrays.asList(0,1,2,3,-1,-1));
    }

    @Test
    public void editLayout() {
        // Verificamos que el método editLayout realiza el intercambio correctamente
        keyboard.editLayout(0, 0, 1, 1);
        assertEquals((Integer) 3, keyboard.layout.get(0));
        assertEquals((Integer) 0, keyboard.layout.get(3));
    }

    @Test
    public void assignLettersLayout() {
        String nameAlphabet = "ABCD";
        TreeSet<String> alphabet = new TreeSet<>();
        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");

        ArrayList<Integer> layout = new ArrayList<>();
        layout.add(0);
        layout.add(1);
        layout.add(2);

        Keyboard customKeyboard = new Keyboard(3, 1, 3, 1.5f, layout, "user3", nameAlphabet);
        ArrayList<ArrayList<String>> assignedLayout = customKeyboard.assignLettersLayout(alphabet);

        ArrayList<ArrayList<String>> expectedLayout = new ArrayList<>();
        ArrayList<String> row = new ArrayList<>();
        row.add("A");
        row.add("B");
        row.add("C");
        expectedLayout.add(row);

        assertEquals(expectedLayout, assignedLayout);
    }
}

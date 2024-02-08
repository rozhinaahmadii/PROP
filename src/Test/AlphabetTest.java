package Test;

import Domain.Alphabet;
import org.junit.Test;

import java.util.HashMap;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class AlphabetTest {


    @Test
    public void testAlphabetConstructor() {
    // Arrange
        String username = "myUsername";
        String name = "MyAlphabet";

        // Act
        Alphabet alphabet = new Alphabet(username, name);

        // Assert
        assertNotNull(alphabet);
        assertEquals(username, alphabet.getUser());
        assertEquals(name, alphabet.getNameAlphabet());
        assertNotNull(alphabet.getAlphabet());
        assertTrue(alphabet.getAlphabet().isEmpty());

    }
    @Test
    public void testCreateSetAlphabetFromText() {
        // Arrange
        String username = "myUsername";
        String name = "MyAlphabet";
        String text = "Hello";

        Alphabet alphabet = new Alphabet(username, name);

        // Act
        alphabet.createSetAlphabetFromText(text);

        // Assert
        TreeSet<String> expectedAlphabet = new TreeSet<>();
        expectedAlphabet.add("H");
        expectedAlphabet.add("e");
        expectedAlphabet.add("l");
        expectedAlphabet.add("o");
        expectedAlphabet.add(" ");

        assertEquals(expectedAlphabet, alphabet.getAlphabet());
    }
    @Test
    public void testCreateSetAlphabetFromWordList() {
        // Arrange
        String username = "myUsername";
        String name = "MyAlphabet";
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("hello", 1);
        wordList.put("world", 1);

        Alphabet alphabet = new Alphabet(username, name);

        // Act
        alphabet.createSetAlphabetFromWordList(wordList);

        // Assert
        TreeSet<String> expectedAlphabet = new TreeSet<>();
        expectedAlphabet.add("h");
        expectedAlphabet.add("e");
        expectedAlphabet.add("l");
        expectedAlphabet.add("o");
        expectedAlphabet.add("w");
        expectedAlphabet.add("r");
        expectedAlphabet.add("d");
        expectedAlphabet.add(" ");

        assertEquals(expectedAlphabet, alphabet.getAlphabet());
    }

}
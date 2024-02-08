package Test;

import Domain.FileF;
import Domain.Text;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;



public class FileTextTest {


    @Test
    public void testFileConstructor() {
        // Arrange
        String title = "MyFile";
        String username = "myUsername";
        String nameAlphabet = "ABC";

        // Act
        FileF file = new FileF(title, username, nameAlphabet);

        // Assert
        assertNotNull(file);
        assertEquals(title, file.getTitle());
        assertEquals(username, file.getUser());
        assertEquals(nameAlphabet, file.getNameAlphabet());

    }

    @Test
    public void testFileConstructor2() {
        // Arrange
        String title = "MyFile";
        String username = "myUsername";
        String nameAlphabet = "ABC";
        HashMap<String, Integer> wordList = new HashMap<>();
        wordList.put("apple", 5);
        wordList.put("orange", 8);

        // Act
        FileF file = new FileF(title, username, wordList, nameAlphabet);

        // Assert
        assertNotNull(file);
        assertEquals(title, file.getTitle());
        assertEquals(username, file.getUser());
        assertEquals(nameAlphabet, file.getNameAlphabet());
        assertEquals(wordList, file.getWordList());
    }



    @Test
    public void testTextConstructor() {
        // Arrange
        String title = "MyText";
        String username = "myUsername";
        String nameAlphabet = "ABC";
        String text = "This is a sample text.";


        // Act
        Text textObj = new Text(title, text, username, nameAlphabet);


        HashMap<String,Integer> w = new HashMap<>();
        w.put("This",1);
        w.put("is",1);
        w.put("a",1);
        w.put("sample",1);
        w.put("text.",1);
        // Assert
        assertNotNull(textObj);
        assertEquals(title, textObj.getTitle());
        assertEquals(username, textObj.getUser());
        assertEquals(nameAlphabet, textObj.getNameAlphabet());
        assertEquals(text, textObj.getText());
        assertEquals(w,textObj.getWordList());
    }


}

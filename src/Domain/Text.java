package Domain;

import java.util.HashMap;

/**
 * Class that represents a text file with a title, content, associated username, and alphabet name.
 * Extends the FileF class.
 * @author Bruno Ruano
 */
public class Text extends FileF {
    /** The content of the text file. */
    public String text;

    /**
     * Constructor that creates a Text object with title, content, username, and alphabet name.
     *
     * @param title        The title of the text file.
     * @param text         The content of the text file.
     * @param username     The username associated with the text file.
     * @param nameAlphabet The alphabet name associated with the text file.
     */
    public Text(String title, String text, String username, String nameAlphabet) {
        super(title, username, nameAlphabet);
        this.text = text;
        createList();
    }

    /**
     * Method to return the text.
     * @return Returns the text.
     */
    public String getText() {
        return text;
    }

    /**
     * Private method that creates a list of words and their frequencies from the text content.
     */
    private void createList() {
        this.wordList = new HashMap<>();
        String[] words = this.text.split(" ");
        for (String word : words) {
            if (this.wordList.containsKey(word)) {
                int newFreq = this.wordList.get(word) + 1;
                this.wordList.put(word, newFreq);
            } else {
                this.wordList.put(word, 1);
            }
        }
    }

    /**
     * Public method that saves the text.
     *
     * @param text New text to save.
     */
    public void editText(String text) {
        this.text = text;
        createList();
    }
}

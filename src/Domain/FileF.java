package Domain;

import java.util.HashMap;

/**
 * Class that represents a file with a title, an associated username, a list of words with their frequency, and an alphabet name.
 * @author Bruno Ruano
 */
public class FileF {
    /** Title of the file. */
    public String title;
    /** Word list with its frequency in the file. */
    public HashMap<String, Integer> wordList;
    /** Alphabet name of the file. */
    private String alphabetName;
    /** Username associated with the file. */
    private String username;

    /**
     * Constructor that creates a file with a title, username, and alphabet name.
     *
     * @param title        The title of the file.
     * @param username     The username associated with the file.
     * @param nameAlphabet The alphabet name associated with the file.
     */
    public FileF(String title, String username, String nameAlphabet) {
        this.title = title;
        this.username = username;
        this.alphabetName = nameAlphabet;
    }

    /**
     * Constructor that creates a file with a title, username, frequency word list, and alphabet name.
     *
     * @param title        The title of the file.
     * @param username     The username associated with the file.
     * @param list         The frequency word list of the file.
     * @param nameAlphabet The alphabet name associated with the file.
     */
    public FileF(String title, String username, HashMap<String, Integer> list, String nameAlphabet) {
        this.title = title;
        this.username = username;
        this.wordList = list;
        this.alphabetName = nameAlphabet;
    }

    /**
     * Gets the title of the file.
     *
     * @return The title of the file.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the word list with its frequency in the file.
     *
     * @return The word list with its frequency.
     */
    public HashMap<String, Integer> getWordList() {
        return wordList;
    }

    /**
     * Gets the username associated with the file.
     *
     * @return The username associated with the file.
     */
    public String getUser() {
        return username;
    }

    /**
     * Gets the alphabet name associated with the file.
     *
     * @return The alphabet name associated with the file.
     */
    public String getNameAlphabet() {
        return alphabetName;
    }

    /**
     * Sets the alphabet name associated with the file.
     *
     * @param nameAlphabet The new alphabet name for the file.
     */
    public void setAlphabetName(String nameAlphabet) {
        this.alphabetName = nameAlphabet;
    }
}

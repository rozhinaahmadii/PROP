package Domain;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Class Alphabet to determine the available characters of a text.
 * @author Rozhina Ahmadi
 */
public class Alphabet {
    /** Characters that the alphabet contains. */
    public TreeSet<String> alphabet;

    /** Name of the alphabet. */
    public String name;

    /** Name of the user. */
    private String username;

    /**
     * Constructor for the Alphabet class.
     *
     * @param user User's alphabet.
     * @param name Name of the alphabet.
     */
    public Alphabet(String user, String name) {
        this.name = name;
        this.username = user;
        this.alphabet = new TreeSet<>();
    }

    /**
     * Constructor for the Alphabet class.
     *
     * @param user User's alphabet.
     * @param name Name of the alphabet.
     * @param alphabet Characters available in the alphabet.
     */
    public Alphabet(String user, String name, TreeSet<String> alphabet) {
        this.name = name;
        this.username = user;
        this.alphabet = alphabet;
    }

    /**
     * Method to return the name of an alphabet.
     *
     * @return The name of the alphabet.
     */
    public String getNameAlphabet(){
        return name;
    }

    /**
     * Method to get the characters of the alphabet.
     *
     * @return The alphabet set.
     */
    public TreeSet<String> getAlphabet() {
        return alphabet;
    }

    /**
     * Method to get the size of the alphabet.
     *
     * @return The size of the alphabet.
     */
    public int getSize() {
        return alphabet.size();
    }

    /**
     * Method to get the user of the alphabet.
     *
     * @return Username.
     */
    public String getUser() {
        return username;
    }

    /**
     * Method to create an alphabet from a given text.
     *
     * @param text Text received to create the alphabet.
     */
    public void createSetAlphabetFromText(String text) {
        for(int i = 0; i < text.length(); i++) {
            this.alphabet.add(String.valueOf(text.charAt(i)));
        }
        alphabet.add(" ");
    }

    /**
     * Method to create an alphabet from a wordlist.
     *
     * @param map Wordlist received to create the alphabet.
     */
    public void createSetAlphabetFromWordList(HashMap<String, Integer> map) {
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            for(int i = 0; i < entry.getKey().length(); i++) {
                this.alphabet.add(String.valueOf(entry.getKey().charAt(i)));
            }
        }
        alphabet.add(" ");
    }

    /**
     * Method to check if the alphabet contains the letters of the text.
     *
     * @param text Text for the alphabet.
     * @return True if the text contains the letters of the alphabet.
     */
    public boolean checkLettersIn(String text) {
        for(int i = 0; i < text.length(); i++) {
            if(!alphabet.contains(String.valueOf(text.charAt(i)))) return false;
        }
        return true;
    }
}

package DomainControllers;

import Domain.Alphabet;

import java.util.*;

/**
 * Controller for the execution of alphabets.
 * Allows the creation, management, and deletion of alphabets by user.
 * @author Rozhina Ahmadi
 */
public class AlphabetController {
    /** HashMap to store alphabets with their user and the title of the alphabets. */
    private HashMap<String, HashMap<String, Alphabet>> alphabets;

    /**
     * Default constructor for the AlphabetController.
     */
    public AlphabetController() {
        this.alphabets = new HashMap<>();
    }

    /**
     * Method to check if an alphabet exists for that user.
     * @param name the name of the alphabet.
     * @param user the user of the alphabet.
     * @return returns true if it exists.
     */
    public boolean existsAlphabet(String user, String name) {
        return alphabets.containsKey(user) && alphabets.get(user).containsKey(name);
    }

    /**
     * Method to check if there are existing alphabets.
     * @param user the user of the current session.
     */
    public boolean existsAlphabets(String user) {
        return alphabets.containsKey(user) && !alphabets.get(user).isEmpty();
    }

    /**
     * Method to create an alphabet from text.
     * @param user the user of the current session.
     * @param text text to be used to create the alphabet.
     * @param nameAlphabet The name to be given to the alphabet.
     */
    public void createAlphabetText(String user, String nameAlphabet, String text){
        Alphabet a = new Alphabet(user, nameAlphabet);
        a.createSetAlphabetFromText(text);
        if (alphabets.containsKey(user)) {
            alphabets.get(user).put(nameAlphabet, a);
        } else {
            HashMap<String, Alphabet> mapA = new HashMap<>();
            mapA.put(nameAlphabet, a);
            alphabets.put(user, mapA);
        }
    }

    /**
     * Method to create an alphabet from a file.
     * @param user the user of the current session.
     * @param map word list to create the alphabet.
     * @param nameAlphabet The name to be given to the alphabet.
     */
    public void createAlphabetFile(String user, String nameAlphabet, HashMap<String, Integer> map) {
        Alphabet a = new Alphabet(user, nameAlphabet);
        a.createSetAlphabetFromWordList(map);
        if (alphabets.containsKey(user)) {
            alphabets.get(user).put(nameAlphabet, a);
        } else {
            HashMap<String, Alphabet> mapA = new HashMap<>();
            mapA.put(nameAlphabet, a);
            alphabets.put(user, mapA);
        }
    }

    /**
     * Obtains the set of alphabets.
     * @return Map of alphabets organized by user.
     */
    public HashMap<String, HashMap<String, Alphabet>> getAlphabets() {
        return alphabets;
    }

    /**
     * Method to obtain the names of the alphabets of a user.
     * @param user the user of the current session.
     * @return returns a list of the alphabets.
     */
    public ArrayList<String> getNamesAlphabet(String user) {
        ArrayList<String> namesAlphabets = new ArrayList<>();
        if (alphabets.containsKey(user)) {
            for (Map.Entry<String, Alphabet> entry : alphabets.get(user).entrySet()) {
                namesAlphabets.add(entry.getKey());
            }
        }
        return namesAlphabets;
    }

    /**
     * Method that returns alphabets that match with that text.
     * @param user the user of the current session.
     * @param text text of the alphabet
     * @return list of alphabets that match with that text.
     */
    public ArrayList<String> getNamesAlphabetCompareText(String user, String text) {
        ArrayList<String> names = new ArrayList<>();
        for (Map.Entry<String, Alphabet> entry : alphabets.get(user).entrySet()) {
            if (entry.getValue().checkLettersIn(text)) names.add(entry.getKey());
        }
        return names;
    }

    /**
     * Method that returns alphabets that match with that file.
     * @param user the user of the current session.
     * @param map wordlist
     * @return list of alphabets that match with that file.
     */
    public ArrayList<String> getNamesAlphabetCompareFile(String user, HashMap<String, Integer> map) {
        StringBuilder unionKeys = new StringBuilder();
        for (String key : map.keySet()) {
            unionKeys.append(key);
            unionKeys.append(" ");
        }
        String text = unionKeys.toString();
        ArrayList<String> names = new ArrayList<>();
        for (Map.Entry<String, Alphabet> entry : alphabets.get(user).entrySet()) {
            if (entry.getValue().checkLettersIn(text)) names.add(entry.getKey());
        }
        return names;
    }

    /**
     * Method to get the character set of an alphabet.
     * @param user the user of the current session.
     * @param name name of the alphabet.
     * @return returns the set of characters of the alphabet.
     */
    public TreeSet<String> getAlphabetSet(String user, String name) {
        return alphabets.get(user).get(name).getAlphabet();
    }

    /**
     * Method to get the size of an alphabet.
     * @param user the user of the current session.
     * @param name name of the alphabet.
     * @return returns the size of the alphabet.
     */
    public int getSizeAlphabet(String user, String name) {
        return alphabets.get(user).get(name).getSize();
    }

    /**
     * Sets the set of alphabets.
     * @param data Map with the data of the alphabets organized by user.
     */
    public void setAlphabets(HashMap<String, HashMap<String, Alphabet>> data) {
        alphabets = new HashMap<>(data);
    }

    /**
     * Method to delete the user of an alphabet.
     * @param user the user of the current session.
     */
    public void deleteUser(String user) {
        alphabets.remove(user);
    }

    /**
     * Method to check the size of an alphabet.
     * @param user the user of the current session.
     * @param title name of the alphabet.
     * @param size of the alphabet
     * @return returns true if it is the correct size.
     */
    public boolean checkSizeAlphabet(String user, String title, int size) {
        if (alphabets.containsKey(user)) {
            if (alphabets.get(user).containsKey(title)) {
                return size >= alphabets.get(user).get(title).getSize();
            }
        }
        return false;
    }

    /**
     * Method to check if all letters are available for the text.
     * @param user the user of the current session.
     * @param nameAlphabet name of the alphabet.
     * @param text text of the alphabet
     * @return returns true if all letters are available for the text.
     */
    public boolean checkAllLettersIn(String user, String nameAlphabet, String text) {
        return alphabets.get(user).get(nameAlphabet).checkLettersIn(text);
    }
}


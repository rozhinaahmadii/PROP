package DomainControllers;

import Domain.KeyboardSaved;
import Domain.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Controller for managing keyboards and saved keyboards.
 * This controller provides methods for creating, editing, deleting, and managing keyboards and saved keyboards
 * associated with users, as well as obtaining information related to these elements, such as names, sizes, and layouts.
 * @author Esther Lozano
 */
public class KeyboardController {
    /** HashMap to store users keyboards. */
    private HashMap<String, HashMap<Integer, Keyboard>> keyboards;
    /** HashMap to store users saved keyboards. */
    private HashMap<String, HashMap<Integer, KeyboardSaved>> savedKeyboards;

    /**
     * Constructor of the KeyboardController class.
     * Initializes data structures.
     */
    public KeyboardController() {
        this.keyboards = new HashMap<>();
        this.savedKeyboards = new HashMap<>();
    }

    /**
     * Search for an available identifier for keyboards and saved keyboards of a certain user.
     *
     * @param user The user's name.
     * @return An available identifier.
     */
    private int searchId(String user) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!keyboards.containsKey(user) && !savedKeyboards.containsKey(user)) return i;
            else if (keyboards.containsKey(user) && savedKeyboards.containsKey(user)) {
                if (!keyboards.get(user).containsKey(i) && !savedKeyboards.get(user).containsKey(i)) return i;
            } else {
                if (!keyboards.containsKey(user) && !savedKeyboards.get(user).containsKey(i)) return i;
                else if (!savedKeyboards.containsKey(user) && !keyboards.get(user).containsKey(i)) return i;
            }
        }
        return -1;
    }

    /**
     * Check if there are saved keyboards for a specific user.
     *
     * @param user The user's name.
     * @return true if there are saved keyboards, false otherwise.
     */
    public boolean existSavedKeyboards(String user) {
        return savedKeyboards.containsKey(user) && !savedKeyboards.get(user).isEmpty();
    }

    /**
     * Check if there exists a saved keyboard with a specific name for a user.
     *
     * @param user         The user's name.
     * @param nameKeyboard The name of the keyboard to check.
     * @return true if a saved keyboard with the specified name exists, false otherwise.
     */
    public boolean existNameKeyboard(String user, String nameKeyboard) {
        if (savedKeyboards.containsKey(user)) {
            for (Map.Entry<Integer, KeyboardSaved> entry : savedKeyboards.get(user).entrySet()) {
                if (entry.getValue().name.equals(nameKeyboard)) return true;
            }
        }
        return false;
    }

    /**
     * Get the saved keyboards of a specific user.
     *
     * @return A data structure containing the saved keyboards.
     */
    public HashMap<String, HashMap<Integer, KeyboardSaved>> getKeyboardsSaved() {
        return savedKeyboards;
    }

    /**
     * Get a list of names of saved keyboards for a user.
     *
     * @param user The user's name.
     * @return A list of names of saved keyboards.
     */
    public ArrayList<String> getNamesKeyboards(String user) {
        TreeSet<String> listNames = new TreeSet<>();
        for (Map.Entry<Integer, KeyboardSaved> entry : savedKeyboards.get(user).entrySet()) {
            listNames.add(entry.getValue().name);
        }
        return new ArrayList<>(listNames);
    }

    /**
     * Get the name of the alphabet associated with a keyboard for a specific user and identifier.
     *
     * @param user      The user's name.
     * @param idKeyboard The identifier of the keyboard.
     * @return The name of the alphabet associated with the keyboard.
     */
    public String getNameAlphabetKeyboard(String user, int idKeyboard) {
        if (keyboards.containsKey(user) && keyboards.get(user).containsKey(idKeyboard)) {
            return keyboards.get(user).get(idKeyboard).getAlphabet();
        } else return savedKeyboards.get(user).get(idKeyboard).getAlphabet();
    }

    /**
     * Get the name of the alphabet associated with a saved keyboard for a specific user and keyboard name.
     *
     * @param user         The user's name.
     * @param nameKeyboard The name of the saved keyboard.
     * @return The name of the alphabet associated with the saved keyboard.
     */
    public String getNameAlphabetSavedKeyboard(String user, String nameKeyboard) {
        String a = "";
        for (Map.Entry<Integer, KeyboardSaved> entry : savedKeyboards.get(user).entrySet()) {
            if (entry.getValue().name.equals(nameKeyboard)) a = entry.getValue().getAlphabet();
        }
        return a;
    }

    /**
     * Get the identifier of a saved keyboard by name.
     *
     * @param user         The user's name.
     * @param nameKeyboard The name of the saved keyboard.
     * @return The identifier of the saved keyboard, or -1 if not found.
     */
    public int getIdSavedKeyboard(String user, String nameKeyboard) {
        int id = -1;
        for (Map.Entry<Integer, KeyboardSaved> entry : savedKeyboards.get(user).entrySet()) {
            if (entry.getValue().name.equals(nameKeyboard)) {
                id = entry.getValue().id;
            }
        }
        return id;
    }

    /**
     * Get the number of rows of a keyboard or saved keyboard.
     *
     * @param user      The user's name.
     * @param idKeyboard The identifier of the keyboard or saved keyboard.
     * @return The number of rows of the keyboard or saved keyboard.
     */
    public int getNumRows(String user, int idKeyboard) {
        if (keyboards.containsKey(user) && keyboards.get(user).containsKey(idKeyboard)) return keyboards.get(user).get(idKeyboard).numRows;
        else return savedKeyboards.get(user).get(idKeyboard).numRows;
    }

    /**
     * Get the number of columns of a keyboard or saved keyboard.
     *
     * @param user      The user's name.
     * @param idKeyboard The identifier of the keyboard or saved keyboard.
     * @return The number of columns of the keyboard or saved keyboard.
     */
    public int getNumCols(String user, int idKeyboard) {
        if (keyboards.containsKey(user) && keyboards.get(user).containsKey(idKeyboard)) return keyboards.get(user).get(idKeyboard).numCols;
        else return savedKeyboards.get(user).get(idKeyboard).numCols;
    }

    /**
     * Get the size of the keys of a keyboard or saved keyboard.
     *
     * @param user      The user's name.
     * @param idKeyboard The identifier of the keyboard or saved keyboard.
     * @return The size of the keys of the keyboard or saved keyboard.
     */
    public float getSizeKey(String user, int idKeyboard) {
        if (keyboards.containsKey(user) && keyboards.get(user).containsKey(idKeyboard)) return keyboards.get(user).get(idKeyboard).sizeKey;
        else return savedKeyboards.get(user).get(idKeyboard).sizeKey;
    }

    /**
     * Get the layout of a keyboard or saved keyboard.
     *
     * @param user      The user's name.
     * @param idKeyboard The identifier of the keyboard or saved keyboard.
     * @return A list of integers representing the layout of the keyboard or saved keyboard.
     */
    public ArrayList<Integer> getLayout(String user, int idKeyboard) {
        if (keyboards.containsKey(user) && keyboards.get(user).containsKey(idKeyboard)) return keyboards.get(user).get(idKeyboard).layout;
        else return savedKeyboards.get(user).get(idKeyboard).layout;
    }

    /**
     * Set a new layout for an existing keyboard.
     *
     * @param user       The user's name.
     * @param idKeyboard The identifier of the keyboard.
     * @param layout     The new layout for the keyboard.
     * @return The identifier of the modified keyboard.
     */
    public int setKeyboardLayout(String user, int idKeyboard, ArrayList<Integer> layout) {
        if (keyboards.containsKey(user) && keyboards.get(user).containsKey(idKeyboard)) {
            keyboards.get(user).get(idKeyboard).layout = layout;
            return idKeyboard;
        } else {
            int id = searchId(user);
            KeyboardSaved ks = savedKeyboards.get(user).get(idKeyboard);
            Keyboard k = new Keyboard(id, ks.getNumRows(), ks.getNumCols(), ks.getSizeKey(), layout, ks.getUsername(), ks.getAlphabet());
            if (!keyboards.containsKey(user)) {
                HashMap<Integer, Keyboard> mapK = new HashMap<>();
                mapK.put(k.id, k);
                keyboards.put(user, mapK);
            } else {
                keyboards.get(user).put(k.id, k);
            }
            return id;
        }
    }

    /**
     * Set the saved keyboards for a specific user.
     *
     * @param data A data structure containing the saved keyboards.
     */
    public void setKeyboardsSaved(HashMap<String, HashMap<Integer, KeyboardSaved>> data) {
        savedKeyboards = new HashMap<>(data);
    }

    /**
     * Create a new keyboard with alphabet letters placed randomly for a user with the provided specifications.
     *
     * @param numRows     The number of rows of the keyboard.
     * @param numCols     The number of columns of the keyboard.
     * @param size        The size of the keys of the keyboard.
     * @param user        The user's name.
     * @param alphabet    The alphabet associated with the keyboard.
     * @param sizeAlphabet The size of the alphabet.
     * @return The identifier of the new keyboard.
     */
    public int createKeyboard(int numRows, int numCols, float size, String user, String alphabet, int sizeAlphabet) {
        int id = searchId(user);
        Keyboard k = new Keyboard(id, numRows, numCols, size, user, alphabet);
        k.generateRandomLayout(sizeAlphabet);
        if (!keyboards.containsKey(user)) {
            HashMap<Integer, Keyboard> mapK = new HashMap<>();
            mapK.put(id, k);
            keyboards.put(user, mapK);
        } else {
            keyboards.get(user).put(id, k);
        }
        return id;
    }

    /**
     * Edit a keyboard by swapping two keys; if this keyboard was saved, an unsaved copy is created.
     *
     * @param user       The user's name.
     * @param idKeyboard The identifier of the keyboard.
     * @param x1         The x-coordinate of the starting point for the layout edit.
     * @param y1         The y-coordinate of the starting point for the layout edit.
     * @param x2         The x-coordinate of the ending point for the layout edit.
     * @param y2         The y-coordinate of the ending point for the layout edit.
     * @return The identifier of the new keyboard if a saved keyboard was created; otherwise, -1.
     */
    public int editKeyboard(String user, int idKeyboard, int x1, int y1, int x2, int y2) {
        int newId = -1;
        if (keyboards.containsKey(user) && keyboards.get(user).containsKey(idKeyboard)) {
            keyboards.get(user).get(idKeyboard).editLayout(x1, y1, x2, y2);
        } else if (savedKeyboards.containsKey(user) && savedKeyboards.get(user).containsKey(idKeyboard)) {
            KeyboardSaved ks = savedKeyboards.get(user).get(idKeyboard);
            newId = searchId(user);
            Keyboard k = new Keyboard(newId, ks.getNumRows(), ks.getNumCols(), ks.getSizeKey(), ks.getLayout(), ks.getUsername(), ks.getAlphabet());
            k.editLayout(x1, y1, x2, y2);
            if (!keyboards.containsKey(user)) {
                HashMap<Integer, Keyboard> mapK = new HashMap<>();
                mapK.put(k.id, k);
                keyboards.put(user, mapK);
            } else {
                keyboards.get(user).put(k.id, k);
            }
        }
        return newId;
    }

    /**
     * Save an existing keyboard as a saved keyboard with a specific name.
     *
     * @param user         The user's name.
     * @param idKeyboard   The identifier of the keyboard to be saved.
     * @param nameKeyboard The name for the saved keyboard.
     */
    public void saveKeyboard(String user, int idKeyboard, String nameKeyboard) {
        Keyboard k = keyboards.get(user).get(idKeyboard);
        KeyboardSaved ks = new KeyboardSaved(k.id, k.numRows, k.numCols, k.sizeKey, k.layout, k.getUsername(), k.getAlphabet(), nameKeyboard);
        keyboards.get(user).remove(idKeyboard);
        if (savedKeyboards.containsKey(user)) {
            savedKeyboards.get(user).put(ks.id, ks);
        } else {
            HashMap<Integer, KeyboardSaved> mapKS = new HashMap<>();
            mapKS.put(ks.id, ks);
            savedKeyboards.put(user, mapKS);
        }
    }

    /**
     * Open an existing keyboard and assign letters to the layout according to the provided alphabet.
     *
     * @param user      The user's name.
     * @param idKeyboard The identifier of the keyboard.
     * @param alphabet   The set of alphabet letters.
     * @return A two-dimensional list representing the keyboard layout with assigned letters.
     */
    public ArrayList<ArrayList<String>> openKeyboard(String user, int idKeyboard, TreeSet<String> alphabet) {
        if (keyboards.containsKey(user) && keyboards.get(user).containsKey(idKeyboard)) return keyboards.get(user).get(idKeyboard).assignLettersLayout(alphabet);
        return savedKeyboards.get(user).get(idKeyboard).assignLettersLayout(alphabet);
    }

    /**
     * Open a saved keyboard and assign letters to the layout according to the provided alphabet.
     *
     * @param user      The user's name.
     * @param idKeyboard The identifier of the saved keyboard.
     * @param alphabet   The set of alphabet letters.
     * @return A two-dimensional list representing the saved keyboard layout with assigned letters.
     */
    public ArrayList<ArrayList<String>> openKeyboardSaved(String user, int idKeyboard, TreeSet<String> alphabet) {
        return savedKeyboards.get(user).get(idKeyboard).assignLettersLayout(alphabet);
    }

    /**
     * Deletes a saved keyboard for a specific user.
     *
     * @param user      The user's name.
     * @param idKeyboard The identifier of the saved keyboard to be deleted.
     */
    public void deleteSavedKeyboard(String user, int idKeyboard) {
        savedKeyboards.get(user).remove(idKeyboard);
    }

    /**
     * Deletes keyboards for a specific user.
     *
     * @param user The user's name.
     */
    public void deleteKeyboardsUser(String user) {
        keyboards.remove(user);
        savedKeyboards.remove(user);
    }
}

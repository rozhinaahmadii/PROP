package Persistence;

import Domain.*;
import java.util.HashMap;

/**
 * Controller class responsible for managing data persistence operations.
 * @author Esther Lozano
 */
public class PersistenceController {
    private KeyboardsPersistence keyboards;
    private FileFPersistence files;
    private TextPersistence texts;
    private AlphabetPersistence alphabets;
    private UserPersistence users;

    /**
     * Constructs a PersistenceController object and initializes the various data persistence components.
     */
    public PersistenceController() {
        keyboards = new KeyboardsPersistence();
        files = new FileFPersistence();
        texts = new TextPersistence();
        alphabets = new AlphabetPersistence();
        users = new UserPersistence();
    }

    /**
     * Saves KeyboardSaved data to the persistent storage.
     *
     * @param k A map of KeyboardSaved data to be saved.
     */
    public void saveDataKeyboard(HashMap<String, HashMap<Integer, KeyboardSaved>> k) {
        if (!keyboards.existsFileKeyboard()) keyboards.createFileKeyboard();
        keyboards.writeFile(k);
    }

    /**
     * Uploads KeyboardSaved data from the persistent storage.
     *
     * @return A map of uploaded KeyboardSaved data.
     */
    public HashMap<String, HashMap<Integer, KeyboardSaved>> uploadDataKeyboard() {
        if (keyboards.existsFileKeyboard()) return keyboards.readFileKeyboard();
        return new HashMap<>();
    }

    /**
     * Saves FileF data to the persistent storage.
     *
     * @param f A map of FileF data to be saved.
     */
    public void saveDataFileF(HashMap<String, HashMap<String, FileF>> f) {
        if (!files.existsFileFileF()) files.createFileFileF();
        files.writeFile(f);
    }

    /**
     * Uploads FileF data from the persistent storage.
     *
     * @return A map of uploaded FileF data.
     */
    public HashMap<String, HashMap<String, FileF>> uploadDataFileF() {
        if (files.existsFileFileF()) return files.readFileFileF();
        return new HashMap<>();
    }

    /**
     * Saves Text data to the persistent storage.
     *
     * @param t A map of Text data to be saved.
     */
    public void saveDataText(HashMap<String, HashMap<String, Text>> t) {
        if (!texts.existsText()) texts.createText();
        texts.writeText(t);
    }

    /**
     * Uploads Text data from the persistent storage.
     *
     * @return A map of uploaded Text data.
     */
    public HashMap<String, HashMap<String, Text>> uploadDataText() {
        if (texts.existsText()) return texts.readText();
        return new HashMap<>();
    }

    /**
     * Saves Alphabet data to the persistent storage.
     *
     * @param a A map of Alphabet data to be saved.
     */
    public void saveDataAlphabet(HashMap<String, HashMap<String, Alphabet>> a) {
        if (!alphabets.existsFileAlphabet()) alphabets.createFileAlphabet();
        alphabets.writeFile(a);
    }

    /**
     * Uploads Alphabet data from the persistent storage.
     *
     * @return A map of uploaded Alphabet data.
     */
    public HashMap<String, HashMap<String, Alphabet>> uploadDataAlphabet() {
        if (alphabets.existsFileAlphabet()) return alphabets.readFileAlphabet();
        return new HashMap<>();
    }

    /**
     * Saves User data to the persistent storage.
     *
     * @param u A map of User data to be saved.
     */
    public void saveDataUser(HashMap<String, User> u) {
        if (!users.existsFileUser()) users.createFileUser();
        users.writeFile(u);
    }

    /**
     * Uploads User data from the persistent storage.
     *
     * @return A map of uploaded User data.
     */
    public HashMap<String, User> uploadDataUser() {
        if (users.existsFileUser()) return users.readFileUser();
        return new HashMap<>();
    }
}

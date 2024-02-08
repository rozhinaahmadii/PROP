package DomainControllers;

import Exceptions.*;
import Persistence.PersistenceController;

import java.util.*;

/**
 * The DomainController class manages the interaction between different components of the system.
 * Including user management, file operations, alphabet management, keyboard operations, and statistics.
 * Serves as the main control unit to coordinate actions within the application.
 * @author Rozhina Ahmadi, Jiahao Liu, Esther Lozano, and Bruno Ruano
 */
public class DomainController {
    /** Current username in use. */
    private static String actualUser;
    /** Identifier of the current keyboard in use. */
    private static int actualKeyboard;
    /** User controller. */
    private static UserController userCtrl;
    /** File controller. */
    private static FileController fileCtrl;
    /** Keyboard controller. */
    private static KeyboardController keyboardCtrl;
    /** Statistics controller. */
    private static StatisticsController statisticsCtrl;
    /** Alphabet controller. */
    private static AlphabetController alphabetCtrl;
    /** Algorithm controller. */
    private static AlgorithmController algorithmCtrl;
    /** Persistence controller. */
    private static PersistenceController persistenceCtrl;

    /**
     * Constructor for the DomainController class.
     * Initializes controllers and resets user and keyboard information.
     */
    public DomainController() {
        actualUser = "";
        actualKeyboard = -1;
        userCtrl = new UserController();
        fileCtrl = new FileController();
        keyboardCtrl = new KeyboardController();
        statisticsCtrl = new StatisticsController();
        alphabetCtrl = new AlphabetController();
        algorithmCtrl = new AlgorithmController();
        persistenceCtrl = new PersistenceController();
    }

    //FUNCTIONS PERSISTENCE
    /**
     * Loads saved data from persistence for users, keyboards, files, texts, and alphabets.
     */
    public void uploadData() {
        keyboardCtrl.setKeyboardsSaved(persistenceCtrl.uploadDataKeyboard());
        fileCtrl.setFiles(persistenceCtrl.uploadDataFileF());
        fileCtrl.setTexts(persistenceCtrl.uploadDataText());
        alphabetCtrl.setAlphabets(persistenceCtrl.uploadDataAlphabet());
        userCtrl.setUsers(persistenceCtrl.uploadDataUser());
    }

    /**
     * Saves data to persistence for users, keyboards, files, texts, and alphabets.
     */
    public void saveData() {
        persistenceCtrl.saveDataKeyboard(keyboardCtrl.getKeyboardsSaved());
        persistenceCtrl.saveDataFileF(fileCtrl.getFiles());
        persistenceCtrl.saveDataText(fileCtrl.getTexts());
        persistenceCtrl.saveDataAlphabet(alphabetCtrl.getAlphabets());
        persistenceCtrl.saveDataUser(userCtrl.getUsers());
    }


    //FUNCTIONS USER
    /**
     * Creates a new user with the username and password.
     * Throws CreateUserException in case of errors.
     * @param username The username of the new user.
     * @param password The password for the new user.
     * @throws CreateUserException If user creation fails.
     */
    public void createUser(String username, String password) throws CreateUserException {
        int error_code = userCtrl.createUser(username, password);
        if (error_code == 1) throw new CreateUserException(username);
        else if (error_code == 2) throw new CreateUserException();
        actualUser = username;
    }

    /**
     * Logs in a user with the username and password.
     * Throws LogInUserException if the login fails.
     * @param username The username of the user trying to log in.
     * @param password The password for the user trying to log in.
     * @throws LogInUserException If login fails.
     */
    public void logInUser(String username, String password) throws LogInUserException {
        boolean loggedIn = userCtrl.logIn(username, password);
        if (!loggedIn) throw new LogInUserException();
        actualUser = username;
    }

    /**
     * Closes the session of the current user, resetting the user and keyboard information.
     */
    public void closeUser() {
        actualUser = "";
        actualKeyboard = -1;
    }

    /**
     * Deletes the current user, along with their files, alphabets, and keyboards.
     */
    public void deleteUser() {
        userCtrl.deleteUser(actualUser);
        fileCtrl.deleteUser(actualUser);
        alphabetCtrl.deleteUser(actualUser);
        keyboardCtrl.deleteKeyboardsUser(actualUser);
        actualUser = "";
        actualKeyboard = -1;
    }


    //FUNCTIONS FILE
    /**
     * Creates a new file with the name, map, and alphabet.
     *
     * @param filename The name of the text to be created.
     * @param map The map representing the content of the file.
     * @param nameAlphabet The name of the alphabet associated with the file.
     * @throws CreateFileException If file creation fails due to an empty alphabet, a duplicate file name, or an empty map.
     */
    public static void createFile(String filename, HashMap<String, Integer> map, String nameAlphabet) throws CreateFileException {
        if(nameAlphabet.isEmpty()) throw new CreateFileException();
        if(fileCtrl.existsFile(actualUser, filename)) throw new CreateFileException(filename);
        if(map.isEmpty()) throw new CreateFileException();
        fileCtrl.createFile(actualUser, filename, map, nameAlphabet);
    }

    /**
     * Creates a text file with the name, text, and alphabet.
     *
     * @param filename The name of the text to be created.
     * @param text The text that the file will contain.
     * @param nameAlphabet The name of the alphabet associated with the file.
     * @throws CreateFileException If file creation fails due to an empty alphabet, a duplicate file name, or empty text.
     */
    public void createText(String filename, String text, String nameAlphabet) throws CreateFileException {
        if(nameAlphabet.isEmpty()) throw new CreateFileException();
        if(fileCtrl.existsFile(actualUser, filename)) throw new CreateFileException(filename);
        if(text.isEmpty()) throw new CreateFileException();
        fileCtrl.createText(actualUser, filename, text, nameAlphabet);
    }

    /**
     * Gets the names of the existing files for the current user.
     *
     * @return The list of names of existing files.
     * @throws EmptyFilesException If there are no available files for the current user.
     */
    public ArrayList<String> getNamesFiles() throws EmptyFilesException {
        if(!fileCtrl.existsFiles(actualUser)) throw new EmptyFilesException(actualUser);
        return fileCtrl.getNamesFiles(actualUser);
    }

    /**
     * Gets the names of the existing texts for the current user.
     *
     * @return The list of names of existing texts.
     * @throws EmptyTextsException If there are no available texts for the current user.
     */
    public static ArrayList<String> getNamesTexts() throws EmptyTextsException {
        if(!fileCtrl.existsTexts(actualUser)) throw new EmptyTextsException();
        return fileCtrl.getNamesTexts(actualUser);
    }

    /**
     * Gets the names of the files associated with the currently selected alphabet.
     *
     * @return The list of names of files associated with the current alphabet.
     * @throws EmptyFilesException If there are no files associated with the alphabet.
     * @throws NoActualKeyboardException If no keyboard is currently selected.
     */
    public static ArrayList<String> getNamesFilesOfAlphabet() throws EmptyFilesException, NoActualKeyboardException {
        if(actualKeyboard == -1) throw new NoActualKeyboardException();
        String nameAlphabet = keyboardCtrl.getNameAlphabetKeyboard(actualUser, actualKeyboard);
        if(!fileCtrl.existsFilesOfAlphabet(actualUser, nameAlphabet)) throw new EmptyFilesException(nameAlphabet);
        return fileCtrl.getNamesFilesOfAlphabet(actualUser, nameAlphabet);
    }

    /**
     * Gets the content of a specific text.
     *
     * @param filename The name of the text file.
     * @return String The content of the text file.
     */
    public static String getText(String filename) {
        return fileCtrl.getText(actualUser, filename);
    }

    /**
     * Edits the content of an existing text file.
     *
     * @param filename The name of the text file to edit.
     * @param text The new content of the text file.
     * @return boolean True if editing is successful, false if alphabet verification fails.
     */
    public static boolean editText(String filename, String text) {
        fileCtrl.editText(actualUser, filename, text);
        String nameAlphabet = fileCtrl.getNameAlphabetFile(actualUser, filename);
        return alphabetCtrl.checkAllLettersIn(actualUser, nameAlphabet, text);
    }

    /**
     * Assigns an alphabet to a text file with the specified name and title.
     *
     * @param nameAlphabet The name of the alphabet to assign.
     * @param title The title of the text file.
     */
    public void assignAlphabetEditText(String nameAlphabet, String title) {
        fileCtrl.setAlphabetTextEdit(actualUser, nameAlphabet, title);
    }

    /**
     * Deletes an existing text file.
     *
     * @param filename The name of the text file to delete.
     */
    public void deleteText(String filename) {
        fileCtrl.deleteText(actualUser, filename);
    }


    //FUNCTIONS ALPHABET
    /**
     * Creates a new text alphabet with the specified name and text.
     *
     * @param name The name of the new alphabet.
     * @param text The content of the text alphabet.
     * @throws CreateAlphabetException If alphabet creation fails due to a duplicate name.
     */
    public void createAlphabetText(String name, String text) throws CreateAlphabetException {
        if (alphabetCtrl.existsAlphabet(actualUser, name)) throw new CreateAlphabetException(name);
        alphabetCtrl.createAlphabetText(actualUser, name, text);
    }

    /**
     * Creates a new file alphabet with the specified name and map.
     *
     * @param name The name of the new alphabet.
     * @param map The map representing the content of the file alphabet.
     * @throws CreateAlphabetException If alphabet creation fails due to a duplicate name.
     */
    public void createAlphabetFile(String name, HashMap<String, Integer> map) throws CreateAlphabetException {
        if (alphabetCtrl.existsAlphabet(actualUser, name)) throw new CreateAlphabetException(name);
        alphabetCtrl.createAlphabetFile(actualUser, name, map);
    }

    /**
     * Obtains the names of all alphabets associated with the current user.
     *
     * @return The list of alphabet names.
     * @throws EmptyAlphabetException If there are no available alphabets.
     */
    public ArrayList<String> getNamesAlphabet() throws EmptyAlphabetException {
        ArrayList<String> names = alphabetCtrl.getNamesAlphabet(actualUser);
        if (names.isEmpty()) throw new EmptyAlphabetException();
        return names;
    }

    /**
     * Obtains the names of the alphabets that can be compared with the given text.
     *
     * @param text The text with which the alphabets will be compared.
     * @return The list of alphabet names that can be compared with the text.
     * @throws EmptyAlphabetException If there are no available alphabets for the current user.
     */
    public ArrayList<String> getNamesAlphabetCompareText(String text) throws EmptyAlphabetException {
        ArrayList<String> names = alphabetCtrl.getNamesAlphabetCompareText(actualUser, text);
        if (names.isEmpty()) throw new EmptyAlphabetException();
        return names;
    }

    /**
     * Obtains the names of the alphabets that can be compared with the given frequency map.
     *
     * @param map The frequency map with which the alphabets will be compared.
     * @return The list of alphabet names that can be compared with the frequency map.
     * @throws EmptyAlphabetException If there are no available alphabets for the current user.
     */
    public ArrayList<String> getNamesAlphabetCompareFile(HashMap<String, Integer> map) throws EmptyAlphabetException {
        ArrayList<String> names = alphabetCtrl.getNamesAlphabetCompareFile(actualUser, map);
        if (names.isEmpty()) throw new EmptyAlphabetException();
        return names;
    }

    /**
     * Obtains the size of the specified alphabet.
     *
     * @param name The name of the alphabet whose size is to be obtained.
     * @return The size of the alphabet.
     * @throws EmptyAlphabetException If there are no available alphabets.
     */
    public static int getSizeAlphabet(String name) throws EmptyAlphabetException {
        if (!alphabetCtrl.existsAlphabets(actualUser)) throw new EmptyAlphabetException();
        return alphabetCtrl.getSizeAlphabet(actualUser, name);
    }


    //FUNCTIONS KEYBOARD
    /**
     * Creates a new keyboard with the specified number of rows, columns, size, and alphabet.
     *
     * @param numRows The number of rows of the new keyboard.
     * @param numCols The number of columns of the new keyboard.
     * @param size The size of the new keyboard.
     * @param alphabet The name of the alphabet associated with the new keyboard.
     * @throws CreateKeyboardException If keyboard creation fails due to alphabet size.
     * @throws EmptyAlphabetException If the alphabet name is null when trying to create the keyboard.
     */
    public void createKeyboard(int numRows, int numCols, float size, String alphabet)
            throws CreateKeyboardException, EmptyAlphabetException {
        if (alphabet == null) throw new EmptyAlphabetException();
        if (!alphabetCtrl.checkSizeAlphabet(actualUser, alphabet, numRows * numCols))
            throw new CreateKeyboardException(numRows, numCols);
        int sizeAlphabet = alphabetCtrl.getSizeAlphabet(actualUser, alphabet);
        actualKeyboard = keyboardCtrl.createKeyboard(numRows, numCols, size, actualUser, alphabet, sizeAlphabet);
    }

    /**
     * Gets the names of the saved keyboards by the current user.
     *
     * @return The list of names of saved keyboards by the current user.
     * @throws EmptyKeyboardsSavedException If there are no saved keyboards by the current user.
     */
    public ArrayList<String> getNamesSavedKeyboards() throws EmptyKeyboardsSavedException {
        if (!keyboardCtrl.existSavedKeyboards(actualUser)) throw new EmptyKeyboardsSavedException();
        else return keyboardCtrl.getNamesKeyboards(actualUser);
    }

    /**
     * Gets the identifier of the currently selected keyboard.
     *
     * @return The identifier of the current keyboard.
     */
    public static int getActualKeyboard() {
        return actualKeyboard;
    }

    /**
     * Gets the identifier of a saved keyboard by its name.
     *
     * @param nameKeyboard The name of the saved keyboard.
     * @return The identifier of the saved keyboard.
     */
    public static int getIdKeyboardSaved(String nameKeyboard) {
        return keyboardCtrl.getIdSavedKeyboard(actualUser, nameKeyboard);
    }

    /**
     * Gets the size of the keys of the current keyboard.
     *
     * @return The size of the keys of the current keyboard.
     */
    public float getSizeKeyActualKeyboard() {
        return keyboardCtrl.getSizeKey(actualUser, actualKeyboard);
    }

    /**
     * Opens a saved keyboard with the specified name.
     *
     * @param nameKeyboard The name of the saved keyboard.
     * @return The layout of the saved keyboard.
     */
    public ArrayList<ArrayList<String>> openKeyboard(String nameKeyboard) {
        String nameAlphabet = keyboardCtrl.getNameAlphabetSavedKeyboard(actualUser, nameKeyboard);
        TreeSet<String> alphabet = alphabetCtrl.getAlphabetSet(actualUser, nameAlphabet);
        actualKeyboard = keyboardCtrl.getIdSavedKeyboard(actualUser, nameKeyboard);
        return keyboardCtrl.openKeyboardSaved(actualUser, actualKeyboard, alphabet);
    }

    /**
     * Returns the current keyboard.
     *
     * @return The layout of the current keyboard.
     */
    public ArrayList<ArrayList<String>> openActualKeyboard() {
        String nameAlphabet = keyboardCtrl.getNameAlphabetKeyboard(actualUser, actualKeyboard);
        TreeSet<String> alphabet = alphabetCtrl.getAlphabetSet(actualUser, nameAlphabet);
        return keyboardCtrl.openKeyboard(actualUser, actualKeyboard, alphabet);
    }

    /**
     * Calculates the layout of the current keyboard based on the selected algorithm and given file names.
     *
     * @param namesFiles The list of file names used for layout calculation.
     * @param algorithm The algorithm used for layout calculation.
     * @throws NoActualKeyboardException If no keyboard is currently selected.
     */
    public void calculateLayoutActualKeyboard(ArrayList<String> namesFiles, String algorithm)
            throws NoActualKeyboardException {
        if (actualKeyboard == -1) throw new NoActualKeyboardException();
        else {
            ArrayList<HashMap<String, Integer>> wordListArrayList = fileCtrl.getListWordListMap(actualUser, namesFiles);
            int numRows = keyboardCtrl.getNumRows(actualUser, actualKeyboard);
            int numCols = keyboardCtrl.getNumCols(actualUser, actualKeyboard);
            String nameAlphabet = keyboardCtrl.getNameAlphabetKeyboard(actualUser, actualKeyboard);
            TreeSet<String> alphabet = alphabetCtrl.getAlphabetSet(actualUser, nameAlphabet);
            algorithmCtrl.initializeAlgorithm(alphabet, wordListArrayList, numRows, numCols, algorithm);
            ArrayList<Integer> layout = algorithmCtrl.solve();
            actualKeyboard = keyboardCtrl.setKeyboardLayout(actualUser, actualKeyboard, layout);
            keyboardCtrl.openKeyboard(actualUser, actualKeyboard, alphabet);
        }
    }

    /**
     * Edits the current keyboard with the given coordinates.
     *
     * @param x1 The x coordinate of the first point.
     * @param y1 The y coordinate of the first point.
     * @param x2 The x coordinate of the second point.
     * @param y2 The y coordinate of the second point.
     */
    public void editActualKeyboard(int x1, int y1, int x2, int y2) {
        int id = keyboardCtrl.editKeyboard(actualUser, actualKeyboard, x1, y1, x2, y2);
        if (id != -1) actualKeyboard = id;
    }

    /**
     * Saves the current keyboard with the specified name.
     *
     * @param nameKeyboard The name for saving the current keyboard.
     * @throws SavedKeyboardException If a keyboard with the specified name already exists.
     * @throws NoActualKeyboardException If no keyboard is currently selected.
     */
    public void saveActualKeyboard(String nameKeyboard) throws SavedKeyboardException, NoActualKeyboardException {
        if (keyboardCtrl.existNameKeyboard(actualUser, nameKeyboard)) throw new SavedKeyboardException(nameKeyboard);
        if (actualKeyboard == -1) throw new NoActualKeyboardException();
        else {
            keyboardCtrl.saveKeyboard(actualUser, actualKeyboard, nameKeyboard);
        }
    }

    /**
     * Deletes a saved keyboard with the specified name.
     *
     * @param nameKeyboard The name of the saved keyboard to delete.
     */
    public void deleteKeyboard(String nameKeyboard) {
        int idKeyboard = keyboardCtrl.getIdSavedKeyboard(actualUser, nameKeyboard);
        keyboardCtrl.deleteSavedKeyboard(actualUser, idKeyboard);
        if (actualKeyboard == idKeyboard) actualKeyboard = -1;
    }


    //FUNCTIONS STATISTICS
    /**
     * Obtains the statistics of the current keyboard for the specified text.
     *
     * @param nameText The name of the text for which the statistics will be obtained.
     * @return double The typing speed for the current keyboard and the given text.
     * @throws NoActualKeyboardException If no keyboard is currently selected.
     */
    public static double getStatisticsActualKeyboard(String nameText) throws NoActualKeyboardException {
        if (actualKeyboard == -1) throw new NoActualKeyboardException();
        else {
            HashMap<String, Integer> mapWL = fileCtrl.getWordList(actualUser, nameText);
            String nameAlphabetFile = fileCtrl.getNameAlphabetFile(actualUser, nameText);
            TreeSet<String> alphabet = alphabetCtrl.getAlphabetSet(actualUser, nameAlphabetFile);
            ArrayList<Integer> layout = keyboardCtrl.getLayout(actualUser, actualKeyboard);
            float size = keyboardCtrl.getSizeKey(actualUser, actualKeyboard);
            int numRows = keyboardCtrl.getNumRows(actualUser, actualKeyboard);
            int numCols = keyboardCtrl.getNumCols(actualUser, actualKeyboard);
            if (!statisticsCtrl.existStatistics(actualUser, actualKeyboard, nameText)) {
                statisticsCtrl.calculateVelocityFile(actualUser, nameText, mapWL, alphabet,
                        actualKeyboard, layout, numRows, numCols, size);
            }
            return statisticsCtrl.getVelocity(actualUser, actualKeyboard, nameText);
        }
    }
}

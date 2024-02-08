package DomainControllers;

import Domain.FileF;
import Domain.Text;

import java.util.*;

/**
 * Controller for managing files.
 * This controller provides methods for creating, editing, deleting, and managing files and texts, as well as alphabet-related operations.
 * @author Bruno Ruano and Jiahao Liu
 */
public class FileController {
    /** HashMap to store users files. */
    private HashMap<String, HashMap<String, FileF>> files;
    /** HashMap to store users texts. */
    private HashMap<String, HashMap<String, Text>> texts;

    /**
     * Constructor for the FileController class.
     * Initializes data structures.
     */
    public FileController() {
        this.files = new HashMap<>();
        this.texts = new HashMap<>();
    }

    /**
     * Checks if a file exists for a given user.
     *
     * @param user     The username.
     * @param filename The file name.
     * @return true if the file exists, false otherwise.
     */
    public boolean existsFile(String user, String filename) {
        return files.containsKey(user) && files.get(user).containsKey(filename) ||
                texts.containsKey(user) && texts.get(user).containsKey(filename);
    }

    /**
     * Checks if a user has associated texts.
     *
     * @param user The username.
     * @return true if the user has texts, false otherwise.
     */
    public boolean existsTexts(String user) {
        return texts.containsKey(user) && !texts.get(user).isEmpty();
    }

    /**
     * Checks if a user has associated files or texts.
     *
     * @param user The username.
     * @return true if the user has files or texts, false otherwise.
     */
    public boolean existsFiles(String user) {
        return texts.containsKey(user) && !texts.get(user).isEmpty() ||
                files.containsKey(user) && !files.get(user).isEmpty();
    }

    /**
     * Checks if there are files or texts associated with a specific alphabet for a user.
     *
     * @param user        The username.
     * @param nameAlphabet The alphabet name.
     * @return true if there are files or texts associated with the alphabet, false otherwise.
     */
    public boolean existsFilesOfAlphabet(String user, String nameAlphabet) {
        if (texts.containsKey(user)) {
            for (Map.Entry<String, Text> entry : texts.get(user).entrySet()) {
                if (entry.getValue().getNameAlphabet().equals(nameAlphabet)) {
                    return true;
                }
            }
        }
        if (files.containsKey(user)) {
            for (Map.Entry<String, FileF> entry : files.get(user).entrySet()) {
                if (entry.getValue().getNameAlphabet().equals(nameAlphabet)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates a new text associated with a user.
     *
     * @param user         The username.
     * @param title        The text title.
     * @param text         The text content.
     * @param nameAlphabet The alphabet name.
     */
    public void createText(String user, String title, String text, String nameAlphabet) {
        Text t = new Text(title, text, user, nameAlphabet);
        if (texts.containsKey(user)) {
            texts.get(user).put(title, t);
        } else {
            HashMap<String, Text> mapT = new HashMap<>();
            mapT.put(title, t);
            texts.put(user, mapT);
        }
    }

    /**
     * Creates a new file associated with a user.
     *
     * @param user         The username.
     * @param title        The file title.
     * @param map          The list of words in the file.
     * @param nameAlphabet The alphabet name.
     */
    public void createFile(String user, String title, HashMap<String, Integer> map, String nameAlphabet) {
        FileF f = new FileF(title, user, map, nameAlphabet);
        if (files.containsKey(user)) {
            files.get(user).put(title, f);
        } else {
            HashMap<String, FileF> mapF = new HashMap<>();
            mapF.put(title, f);
            files.put(user, mapF);
        }
    }

    /**
     * Gets the names of all files and texts associated with a user.
     *
     * @param user The username.
     * @return A list of names of files and texts associated with the user.
     */
    public ArrayList<String> getNamesFiles(String user) {
        ArrayList<String> names = new ArrayList<>();
        if (texts.containsKey(user)) {
            for (Map.Entry<String, Text> entry : texts.get(user).entrySet()) {
                names.add(entry.getKey());
            }
        }
        if (files.containsKey(user)) {
            for (Map.Entry<String, FileF> entry : files.get(user).entrySet()) {
                names.add(entry.getKey());
            }
        }
        return names;
    }

    /**
     * Gets the names of all texts associated with a user.
     *
     * @param user The username.
     * @return A list of names of texts associated with the user.
     */
    public ArrayList<String> getNamesTexts(String user) {
        ArrayList<String> names = new ArrayList<>();
        for (Map.Entry<String, Text> entry : texts.get(user).entrySet()) {
            names.add(entry.getKey());
        }
        return names;
    }

    /**
     * Gets the alphabet name associated with a file or text.
     *
     * @param user     The username.
     * @param nameFile The name of the file or text.
     * @return The alphabet name associated with the file or text.
     */
    public String getNameAlphabetFile(String user, String nameFile) {
        if (texts.containsKey(user) && texts.get(user).containsKey(nameFile)) return texts.get(user).get(nameFile).getNameAlphabet();
        return files.get(user).get(nameFile).getNameAlphabet();
    }

    /**
     * Gets the names of files associated with a user and a specific alphabet.
     *
     * @param user        The username.
     * @param nameAlphabet The alphabet name.
     * @return A list of names of files associated with the user and the alphabet.
     */
    public ArrayList<String> getNamesFilesOfAlphabet(String user, String nameAlphabet) {
        ArrayList<String> namesFiles = new ArrayList<>();
        if (texts.containsKey(user)) {
            for (Map.Entry<String, Text> entry : texts.get(user).entrySet()) {
                if (entry.getValue().getNameAlphabet().equals(nameAlphabet)) {
                    namesFiles.add(entry.getKey());
                }
            }
        }
        if (files.containsKey(user)) {
            for (Map.Entry<String, FileF> entry : files.get(user).entrySet()) {
                if (entry.getValue().getNameAlphabet().equals(nameAlphabet)) {
                    namesFiles.add(entry.getKey());
                }
            }
        }
        return namesFiles;
    }

    /**
     * Gets the files of a specific user.
     *
     * @return The data structure containing the files.
     */
    public HashMap<String, HashMap<String, FileF>> getFiles() {
        return files;
    }

    /**
     * Gets the word list of a file.
     *
     * @param user     The username.
     * @param filename The filename.
     * @return The word list of the file.
     */
    public HashMap<String, Integer> getWordList(String user, String filename) {
        if (files.containsKey(user) && files.get(user).containsKey(filename)) return files.get(user).get(filename).wordList;
        return texts.get(user).get(filename).wordList;
    }

    /**
     * Gets the list of word lists of files associated with a user and a list of file names.
     *
     * @param username  The username.
     * @param nameFiles The list of file names.
     * @return A list of word lists of files associated with the user and file names.
     */
    public ArrayList<HashMap<String, Integer>> getListWordListMap(String username, ArrayList<String> nameFiles) {
        ArrayList<HashMap<String, Integer>> listWL = new ArrayList<>();
        for (String name : nameFiles) {
            if (files.containsKey(username) && files.get(username).containsKey(name)) {
                listWL.add(files.get(username).get(name).wordList);
            } else if (texts.containsKey(username) && texts.get(username).containsKey(name)) {
                listWL.add(texts.get(username).get(name).wordList);
            }
        }
        return listWL;
    }

    /**
     * Gets the content of a text.
     *
     * @param user  The username.
     * @param title The title of the text.
     * @return The content of the text.
     */
    public String getText(String user, String title) {
        return texts.get(user).get(title).text;
    }

    /**
     * Gets the texts of a specific user.
     *
     * @return The data structure containing the texts.
     */
    public HashMap<String, HashMap<String, Text>> getTexts() {
        return texts;
    }

    /**
     * Sets the texts for a specific user.
     *
     * @param data The data structure containing the texts.
     */
    public void setTexts(HashMap<String, HashMap<String, Text>> data) {
        texts = new HashMap<>(data);
    }

    /**
     * Sets the files for a specific user.
     *
     * @param data The data structure containing the files.
     */
    public void setFiles(HashMap<String, HashMap<String, FileF>> data) {
        files = new HashMap<>(data);
    }

    /**
     * Edits the content of a text.
     *
     * @param user  The username.
     * @param title The title of the text to edit.
     * @param text  The new content of the text.
     */
    public void editText(String user, String title, String text) {
        texts.get(user).get(title).editText(text);
    }

    /**
     * Assigns a new alphabet to an edited text.
     *
     * @param actualUser   The username.
     * @param nameAlphabet The alphabet name.
     * @param title        The text title.
     */
    public void setAlphabetTextEdit(String actualUser, String nameAlphabet, String title) {
        texts.get(actualUser).get(title).setAlphabetName(nameAlphabet);
    }

    /**
     * Deletes a text associated with a user.
     *
     * @param user  The username.
     * @param title The title of the text to delete.
     */
    public void deleteText(String user, String title) {
        if (texts.containsKey(user) && texts.get(user).containsKey(title)) texts.get(user).remove(title);
        else files.get(user).remove(title);
    }

    /**
     * Deletes a user and all associated files and texts.
     *
     * @param user The username to delete.
     */
    public void deleteUser(String user) {
        texts.remove(user);
        files.remove(user);
    }
}

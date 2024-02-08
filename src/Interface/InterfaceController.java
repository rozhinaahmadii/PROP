package Interface;

import DomainControllers.DomainController;
import Exceptions.*;
import Interface.Dialogs.File.*;
import Interface.Dialogs.Keyboard.*;
import Interface.Dialogs.Statistics.*;
import Interface.Menus.*;
import Interface.Panels.*;
import Interface.Panels.User.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * The main controller for the user interface that manages the application's logic.
 * @author Rozhina Ahmadi, Jiahao Liu, Esther Lozano, and Bruno Ruano
 */
public class InterfaceController {
    /** The main domain controller that manages the application's logic. */
    private static DomainController domainCtrl;

    // PANELS
    /** The welcome panel displayed at the start of the application. */
    private WelcomePanel welcomePanel;
    /** The login panel for user authentication. */
    private LogInPanel logInPanel;
    /** The panel for creating new users. */
    private CreateUserPanel createUserPanel;
    /** The main panel of the application window. */
    private PrincipalWindowPanel principalWindowPanel;

    // MENUS
    /** The main menu of the application. */
    private Menu menu;
    /** The text editing menu. */
    private MenuEditText menuEditText;
    /** The keyboard editing menu. */
    private MenuEditKeyboard menuEditKeyboard;

    // WINDOWS
    /** The main user interface window. */
    private JFrame window;

    // DIALOGS

    // Files
    /** Dialog for creating new texts. */
    private NewTextDialog newTextDialog;
    /** Dialog for creating new word lists. */
    private NewWordListDialog newWordListDialog;
    /** Dialog for editing existing texts. */
    private EditTextDialog editTextDialog;
    /** Dialog for deleting files. */
    private DeleteFileDialog deleteFileDialog;
    /** Dialog for creating new alphabets from edited texts. */
    private NewAlphabetTextEditDialog newAlphabetTextEditDialog;
    /** Dialog for displaying statistics. */
    private StatisticsDialog statisticsDialog;

    // Keyboards
    /** Dialog for creating new keyboards. */
    private CreateKeyboardDialog createKeyboardDialog;
    /** Dialog for opening existing keyboards. */
    private OpenKeyboardDialog openKeyboardDialog;
    /** Dialog for saving keyboards. */
    private SaveKeyboardDialog saveKeyboardDialog;
    /** Dialog for deleting saved keyboards. */
    private DeleteKeyboardDialog deleteKeyboardDialog;
    /** Dialog for selecting and applying algorithms to keyboards. */
    private AlgorithmDialog algorithmDialog;

    // ATTRIBUTES
    /** Message to display in the interface, typically related to the result of an action. */
    private String message;
    /** Currently edited or viewed text. */
    private String actualText;
    /** Name of the currently edited or viewed text. */
    private String nameActualText;
    /** Identifier of the currently in-use or selected keyboard. */
    private int idKeyboard;

    /**
     * Initializes the interface and loads necessary data.
     */
    public InterfaceController() {
        window = new JFrame();

        welcomePanel = new WelcomePanel(this);
        logInPanel = new LogInPanel(this);
        createUserPanel = new CreateUserPanel(this);
        principalWindowPanel = new PrincipalWindowPanel();
        menu = new Menu(this.window, this);
        domainCtrl = new DomainController();
        domainCtrl.uploadData();

        actualText = "";
        nameActualText = "";
        idKeyboard = -1;
    }

    /**
     * Gets the current message to display to the user.
     *
     * @return The current message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the currently active text in the interface.
     *
     * @return The current text.
     */
    public String getActualText() {
        return actualText;
    }

    /**
     * Creates and configures the welcome window.
     */
    public void createWelcomeWindow() {
        window.setTitle("EasyType");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(600, 600);
        window.setResizable(true);
        window.setLocationRelativeTo(null);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProgram();
            }
        });
    }

    /**
     * Initializes and displays the main application window.
     */
    public void createPrincipalWindow() {
        window.setSize(1400, 800);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        this.menu = new Menu(this.window, this);
        window.setJMenuBar(this.menu);
        window.add(principalWindowPanel);
    }

    /**
     * Initializes and displays the new text dialog.
     */
    public void createNewTextDialog() {
        newTextDialog = new NewTextDialog(this.window, this);
        newTextDialog.setVisible(true);
    }

    /**
     * Initializes and displays the new word list dialog.
     */
    public void createNewWordListDialog() {
        newWordListDialog = new NewWordListDialog(this.window, this);
    }

    /**
     * Initializes and displays the create keyboard dialog.
     */
    public void createCreateKeyboardDialog() {
        createKeyboardDialog = new CreateKeyboardDialog(this.window, this);
    }

    /**
     * Initializes and displays the open keyboard dialog.
     */
    public void createOpenKeyboardDialog() {
        openKeyboardDialog = new OpenKeyboardDialog(this.window, this);
    }

    /**
     * Creates and displays the save keyboard dialog.
     */
    public void createSaveKeyboardDialog() {
        saveKeyboardDialog = new SaveKeyboardDialog(this.window, this);
    }

    /**
     * Creates and displays the deleted keyboard dialog.
     */
    public void createDeleteKeyboardDialog() {
        deleteKeyboardDialog = new DeleteKeyboardDialog(this.window, this);
    }

    /**
     * Creates and displays the delete file dialog.
     */
    public void createDeleteFileDialog() {
        deleteFileDialog = new DeleteFileDialog(this.window, this);
    }

    /**
     * Creates and displays the edit text dialog.
     */
    public void createEditTextDialog() {
        editTextDialog = new EditTextDialog(this.window, this);
    }

    /**
     * Creates and displays the algorithm selection dialog.
     */
    public void createAlgorithmDialog(){
        algorithmDialog = new AlgorithmDialog(this.window, this);
    }

    /**
     * Creates and displays the statistics dialog.
     */
    public void createStatisticsDialog() {
        statisticsDialog = new StatisticsDialog(this.window, this);
    }

    /**
     * Initializes and displays the welcome window.
     */
    public void iniWelcomeWindow() {
        window.add(welcomePanel);
        window.setJMenuBar(null);
        window.setVisible(true);
    }

    /**
     * Updates the window to display the welcome panel.
     */
    public void paintWelcomePanel() {
        window.getContentPane().removeAll();
        window.add(welcomePanel);
        window.setJMenuBar(null);
        window.revalidate();
        window.repaint();
    }

    /**
     * Updates the window to display the main panel.
     */
    public void paintPrincipalWindowPanel() {
        this.principalWindowPanel = new PrincipalWindowPanel();
        window.getContentPane().removeAll();
        this.menu = new Menu(this.window, this);
        window.setJMenuBar(this.menu);
        window.add(principalWindowPanel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Updates the window to display the main panel after editing a text.
     */
    public void paintPrincipalWindowAfterEditText() {
        this.menu = new Menu(this.window, this);
        if(idKeyboard != -1) {
            ArrayList<ArrayList<String>> keyboard = domainCtrl.openActualKeyboard();
            float size = domainCtrl.getSizeKeyActualKeyboard();
            idKeyboard = domainCtrl.getActualKeyboard();
            paintPrincipalWindowPanelKeyboard(keyboard, size);
        } else {
            window.setJMenuBar(this.menu);
            window.revalidate();
            window.repaint();
        }
    }

    /**
     * Updates the window to display a specific keyboard.
     *
     * @param keyboard Design of the keyboard to display.
     * @param size     Size of the keyboard keys.
     */
    private void paintPrincipalWindowPanelKeyboard(ArrayList<ArrayList<String>> keyboard, float size) {
        this.principalWindowPanel = new PrincipalWindowPanel(keyboard, size);
        this.principalWindowPanel.setTextBox(actualText);
        window.getContentPane().removeAll();
        this.menu = new Menu(this.window, this);
        window.setJMenuBar(this.menu);
        window.add(principalWindowPanel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Updates the window to display the text editing panel.
     */
    public void paintPrincipalWindowEditTextPanel() {
        this.principalWindowPanel.setTextBox(actualText);
        window.getContentPane().removeAll();
        this.menuEditText = new MenuEditText(this.window, this);
        window.setJMenuBar(this.menuEditText);
        window.add(principalWindowPanel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Updates the window to display the keyboard editing panel.
     *
     * @return true if the panel was updated successfully, false otherwise.
     */
    public boolean paintEditKeyboardPanel() {
        if(idKeyboard == -1) return false;
        ArrayList<ArrayList<String>> keyboard = domainCtrl.openActualKeyboard();
        float size = domainCtrl.getSizeKeyActualKeyboard();
        this.principalWindowPanel = new PrincipalWindowPanel(keyboard, size, false);
        this.principalWindowPanel.setTextBox(actualText);
        window.getContentPane().removeAll();
        this.menuEditKeyboard = new MenuEditKeyboard(this.window, this);
        window.setJMenuBar(menuEditKeyboard);
        window.add(principalWindowPanel);
        window.revalidate();
        window.repaint();
        return true;
    }

    /**
     * Updates the content of the window with a specific panel.
     *
     * @param panel Panel to display in the window.
     */
    public void paint(JPanel panel) {
        window.getContentPane().removeAll();
        window.add(panel);
        window.revalidate();
        window.repaint();
    }

    /**
     * Updates the content of the window with the created user panel.
     */
    public void showViewCreateUser() {
        createUserPanel = new CreateUserPanel(this);
        paint(createUserPanel);
    }

    /**
     * Updates the content of the window with the log in panel.
     */
    public void showViewLogIn() {
        logInPanel = new LogInPanel(this);
        paint(logInPanel);
    }

    /**
     * Checks and performs the user creation process.
     *
     * @param user     User's name.
     * @param password User's password.
     * @return true if the user was created successfully, false otherwise.
     */
    public boolean checkCreateUser(String user, String password) {
        boolean create = false;
        try {
            domainCtrl.createUser(user, password);
            create = true;
        } catch (CreateUserException e) {
            message = e.getMessage();
        }
        return create;
    }

    /**
     * Checks and performs the login process.
     *
     * @param user     User's name.
     * @param password User's password.
     * @return true if the login was successful, false otherwise.
     */
    public boolean checkLogIn(String user, String password) {
        boolean login = false;
        try {
            domainCtrl.logInUser(user, password);
            login = true;
        } catch (LogInUserException e) {
            message = e.getMessage();
        }
        return login;
    }

    /**
     * Retrieves the names of alphabets suitable for a specific text.
     *
     * @param text The text for which to find suitable alphabets.
     * @return A list of alphabet names.
     */
    public ArrayList<String> getNamesAlphabetForText(String text) {
        try {
            return domainCtrl.getNamesAlphabetCompareText(text);
        } catch (EmptyAlphabetException e) {
            message = e.getMessage();
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves the names of alphabets suitable for a specific word list.
     *
     * @param wordList The word list for which to find suitable alphabets.
     * @return A list of alphabet names.
     */
    public ArrayList<String> getNamesAlphabetForWordList(HashMap<String, Integer> wordList) {
        try {
            return domainCtrl.getNamesAlphabetCompareFile(wordList);
        } catch (EmptyAlphabetException e) {
            message = e.getMessage();
        }
        return new ArrayList<>();
    }

    /**
     * Creates a new text.
     *
     * @param nameText      The name of the new text.
     * @param text          The content of the text.
     * @param alphabetName  The name of the alphabet associated with the text.
     */
    public void createText(String nameText, String text, String alphabetName) {
        message = "Successfully Created";
        try {
            domainCtrl.createText(nameText, text, alphabetName);
        } catch (CreateFileException e) {
            message = e.getMessage();
        }
    }

    /**
     * Creates a new word list.
     *
     * @param nameWordList  The name of the new word list.
     * @param wordList      The content of the word list.
     * @param alphabetName  The name of the alphabet associated with the word list.
     */
    public void createWordList(String nameWordList, HashMap<String, Integer> wordList, String alphabetName) {
        message = "Successfully Created";
        try {
            domainCtrl.createFile(nameWordList, wordList, alphabetName);
        } catch (CreateFileException e) {
            message = e.getMessage();
        }
    }

    /**
     * Creates a new text and alphabet.
     *
     * @param nameText      The name of the new text.
     * @param text          The content of the text.
     * @param alphabetName  The name of the alphabet associated with the text.
     */
    public void createTextAndAlphabet(String nameText, String text, String alphabetName) {
        message = "Successfully Created";
        try {
            domainCtrl.createText(nameText, text, alphabetName);
            try {
                domainCtrl.createAlphabetText(alphabetName, text);
            } catch (CreateAlphabetException e) {
                message = e.getMessage();
            }
        } catch (CreateFileException e) {
            message = e.getMessage();
        }
    }

    /**
     * Creates a new word list and alphabet.
     *
     * @param nameWordList  The name of the new word list.
     * @param wordList      The content of the word list.
     * @param alphabetName  The name of the alphabet associated with the word list.
     */
    public void createWordListAndAlphabet(String nameWordList, HashMap<String, Integer> wordList, String alphabetName) {
        message = "Successfully Created";
        try {
            domainCtrl.createFile(nameWordList, wordList, alphabetName);
            try {
                domainCtrl.createAlphabetFile(alphabetName, wordList);
            } catch (CreateAlphabetException e) {
                message = e.getMessage();
            }
        } catch (CreateFileException e) {
            message = e.getMessage();
        }
    }

    /**
     * Creates a new keyboard with the given specifications.
     *
     * @param rows          Number of rows in the keyboard.
     * @param cols          Number of columns in the keyboard.
     * @param size          Size of the keyboard keys.
     * @param alphabetName  Name of the alphabet associated with the keyboard.
     */
    public void createKeyboard(Integer rows, Integer cols, int size, String alphabetName) {
        message = "Successfully Created";
        try {
            domainCtrl.createKeyboard(rows, cols, size, alphabetName);
            idKeyboard = domainCtrl.getActualKeyboard();
        } catch (CreateKeyboardException | EmptyAlphabetException e) {
            message = e.getMessage();
        }
    }

    /**
     * Obtains the size of the alphabet with the given name.
     *
     * @param nameAlphabet The name of the alphabet.
     * @return The size of the alphabet.
     */
    public int getSizeAlphabet(String nameAlphabet) {
        message = "Successfully Created";
        int size = 0;
        try {
            size = domainCtrl.getSizeAlphabet(nameAlphabet);
        } catch (EmptyAlphabetException e) {
            message = e.getMessage();
        }
        return size;
    }

    /**
     * Obtains a list of names of saved keyboards.
     *
     * @return A list of names of saved keyboards.
     */
    public ArrayList<String> getNamesSavedKeyboards() {
        ArrayList<String> names = new ArrayList<>();
        try {
            names = domainCtrl.getNamesSavedKeyboards();
        } catch (EmptyKeyboardsSavedException e) {
            message = e.getMessage();
        }
        return names;
    }

    /**
     * Obtains the names of files available for the current user.
     *
     * @return A list with the names of available files.
     */
    public ArrayList<String> getNamesFiles() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list = domainCtrl.getNamesFiles();
        } catch (EmptyFilesException e) {
            message = e.getMessage();
        }
        return list;
    }

    /**
     * Obtains the names of texts available for the current user.
     *
     * @return A list with the names of available texts.
     */
    public ArrayList<String> getNamesTexts() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list = domainCtrl.getNamesTexts();
        } catch (EmptyTextsException e) {
            message = e.getMessage();
        }
        return list;
    }

    /**
     * Obtains the names of files that correspond to a specific alphabet.
     *
     * @return A list with the names of files.
     */
    public ArrayList<String> getNamesFilesOfAlphabet() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list = domainCtrl.getNamesFilesOfAlphabet();
        } catch (EmptyFilesException | NoActualKeyboardException e) {
            message = e.getMessage();
        }
        return list;
    }

    /**
     * Obtains the names of available alphabets.
     *
     * @return A list with the names of alphabets.
     * @throws EmptyAlphabetException If there are no available alphabets.
     */
    public ArrayList<String> getNamesAlphabet() throws EmptyAlphabetException {
        ArrayList<String> alphabets = domainCtrl.getNamesAlphabet();
        if (alphabets == null || alphabets.isEmpty()) throw new EmptyAlphabetException();
        return alphabets;
    }

    /**
     * Calculates the layout of the current keyboard using a specific algorithm and a list of file names.
     *
     * @param nameFiles  List of file names to use in the calculation.
     * @param algorithm  Algorithm to use for the calculation.
     */
    public void calculateLayoutActualKeyboard(ArrayList<String> nameFiles, String algorithm) {
        message = "Successfully Calculated";
        try {
            domainCtrl.calculateLayoutActualKeyboard(nameFiles, algorithm);
        } catch (NoActualKeyboardException e) {
            message = e.getMessage();
        }
    }

    /**
     * Saves the current keyboard with the given name.
     *
     * @param nameKeyboard The name under which to save the keyboard.
     */
    public void saveKeyboard(String nameKeyboard) {
        message = "Successfully Saved";
        try {
            domainCtrl.saveActualKeyboard(nameKeyboard);
        } catch (SavedKeyboardException | NoActualKeyboardException e) {
            message = e.getMessage();
        }
    }

    /**
     * Saves the currently edited text.
     */
    public void saveText() {
        actualText = principalWindowPanel.getTextBox();
    }

    /**
     * Opens a specific text for editing.
     *
     * @param nameText The name of the text to open.
     */
    public void openText(String nameText) {
        message = "Edit the text on the editor and press Save Text";
        actualText = domainCtrl.getText(nameText);
        nameActualText = nameText;
    }
    /**
     * Returns true if any Keyboard is open.
     */
    public boolean isOpenKeyboard() {
        return idKeyboard != -1;
    }

    /**
     * Opens the currently in-use keyboard.
     */
    public void openActualKeyboard() {
        ArrayList<ArrayList<String>> keyboard = domainCtrl.openActualKeyboard();
        float size = domainCtrl.getSizeKeyActualKeyboard();
        paintPrincipalWindowPanelKeyboard(keyboard, size);
    }

    /**
     * Opens a keyboard with the given name.
     *
     * @param nameKeyboard The name of the keyboard to open.
     */
    public void openKeyboard(String nameKeyboard) {
        ArrayList<ArrayList<String>> keyboard = domainCtrl.openKeyboard(nameKeyboard);
        float size = domainCtrl.getSizeKeyActualKeyboard();
        idKeyboard = domainCtrl.getActualKeyboard();
        paintPrincipalWindowPanelKeyboard(keyboard, size);
    }

    /**
     * Displays statistics for a specific text.
     *
     * @param nameText The name of the text for which to display statistics.
     */
    public void showStatistics(String nameText) {
        message = "The punctuation of keyboard is ";
        try {
            double vel = domainCtrl.getStatisticsActualKeyboard(nameText);
            message = message + String.format("%.2f", vel);
        } catch (NoActualKeyboardException e) {
            message = e.getMessage();
        }
    }

    /**
     * Edits the configuration of the currently active keyboard.
     *
     * @return true if the editing was successful, false otherwise.
     */
    public boolean editKeyboard() {
        int x1 = principalWindowPanel.getX1();
        int y1 = principalWindowPanel.getY1();
        int x2 = principalWindowPanel.getX2();
        int y2 = principalWindowPanel.getY2();
        if (x1 == -1 || y1 == -1 || x2 == -1 || y2 == 1) return false;
        domainCtrl.editActualKeyboard(x1, y1, x2, y2);
        return true;
    }

    /**
     * Edits the currently selected text.
     */
    public void editText() {
        actualText = this.principalWindowPanel.getTextBox();
        boolean checkAlphabet = domainCtrl.editText(nameActualText, actualText);
        if (!checkAlphabet) {
            this.newAlphabetTextEditDialog = new NewAlphabetTextEditDialog(this.window, this);
        }
    }

    /**
     * Creates an alphabet and associates it with an edited text.
     *
     * @param nameAlphabet The name of the new alphabet to create.
     * @return true if the creation was successful, false otherwise.
     */
    public boolean createAlphabetEditText(String nameAlphabet) {
        message = "Successfully Created";
        boolean created = true;
        try {
            domainCtrl.createAlphabetText(nameAlphabet, actualText);
            domainCtrl.assignAlphabetEditText(nameAlphabet, nameActualText);
        } catch (CreateAlphabetException e) {
            message = e.getMessage();
            created = false;
        }
        return created;
    }

    /**
     * Deletes a saved keyboard by name.
     *
     * @param nameKeyboard The name of the saved keyboard to delete.
     */
    public void deleteSavedKeyboard(String nameKeyboard) {
        message = "Successfully Deleted";
        int idDeletedKeyboard = domainCtrl.getIdKeyboardSaved(nameKeyboard);
        domainCtrl.deleteKeyboard(nameKeyboard);
        if (idDeletedKeyboard == idKeyboard) paintPrincipalWindowPanel();
        idKeyboard = -1;
    }

    /**
     * Deletes a specific file by name.
     *
     * @param nameFile The name of the file to delete.
     */
    public void deleteFile(String nameFile) {
        message = "Successfully Deleted";
        domainCtrl.deleteText(nameFile);
    }

    /**
     * Deletes the current user from the application.
     */
    public void deleteUser() {
        domainCtrl.deleteUser();
    }

    /**
     * Closes the session of the current user.
     */
    public void closeUser() {
        domainCtrl.closeUser();
    }

    /**
     * Closes the application and saves any necessary data.
     */
    private void exitProgram() {
        domainCtrl.saveData();
    }


}
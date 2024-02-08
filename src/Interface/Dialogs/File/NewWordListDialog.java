package Interface.Dialogs.File;

import Exceptions.CreateWordListFromFile;
import Interface.InterfaceController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * The NewWordListDialog class represents a dialog for creating a new word list.
 * @author Bruno Ruano
 */
public class NewWordListDialog extends JDialog {

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private Stack<Integer> prevPanel = new Stack<>();
    private String path = "";
    private String succeed = "";
    private HashMap<String, Integer> wordList;
    private JLabel successLabel = new JLabel();
    private JList<String> alphabetsList = new JList<>();

    /**
     * Constructs a NewWordListDialog.
     *
     * @param parent       The parent frame of the dialog.
     * @param interfaceCtrl The interface controller for handling actions.
     */
    public NewWordListDialog(JFrame parent, InterfaceController interfaceCtrl) {
        super(parent, "New Word List", true);
        setSize(300, 300);
        setLocationRelativeTo(parent);

        initializePanels(interfaceCtrl);
        add(cardPanel);
        setVisible(true);
    }

    /**
     * Initializes the components of the dialog.
     *
     * @param interfaceCtrl The interface controller managing the actions of the dialog components.
     */
    private void initializePanels(InterfaceController interfaceCtrl) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Panel 1: Name File
        JPanel panel1 = new JPanel(new GridBagLayout());
        JLabel nameWordList = new JLabel("Name of word list:");
        JTextField nameField = new JTextField(10);
        JRadioButton manualWordListOption = new JRadioButton("Enter word list manually");
        JRadioButton fileWordListOption = new JRadioButton("Word list from a file");
        ButtonGroup group1 = new ButtonGroup();
        group1.add(manualWordListOption);
        group1.add(fileWordListOption);
        JButton next1 = new JButton("Next");
        next1.addActionListener(e -> {
            if (!nameField.getText().isEmpty() && (manualWordListOption.isSelected() || fileWordListOption.isSelected())) {
                prevPanel.add(1);
                if (manualWordListOption.isSelected()) {
                    wordList = new HashMap<>();
                }
                cardLayout.show(cardPanel, manualWordListOption.isSelected() ? "Panel 2" : "Panel 3");
            } else {
                JOptionPane.showMessageDialog(this, "You must provide a name and select an option");
            }
        });
        panel1.add(nameWordList, gbc);
        gbc.gridy++;
        panel1.add(nameField, gbc);
        gbc.gridy++;
        panel1.add(manualWordListOption, gbc);
        gbc.gridy++;
        panel1.add(fileWordListOption, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(next1, gbc);

        // Panel 2: Input Word List
        JPanel panel2 = new JPanel(new GridBagLayout());
        JLabel wordLabel = new JLabel("Word:");
        JTextField wordField = new JTextField(15);
        JLabel frequencyLabel = new JLabel("Frequency:");
        JSpinner frequencyField = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        JButton addWordButton = new JButton("Add Word");
        addWordButton.addActionListener(e -> {
            wordList.put(wordField.getText(), (Integer) frequencyField.getValue());
            frequencyField.setValue(1);
            wordField.setText("");
        });
        JButton next2 = new JButton("Next");
        next2.addActionListener(e -> {
            prevPanel.add(2);
            cardLayout.show(cardPanel, "Panel 4");
        });
        JButton back2 = new JButton("Back");
        back2.addActionListener(e -> cardLayout.show(cardPanel, "Panel " + prevPanel.pop()));
        panel2.add(wordLabel, gbc);
        gbc.gridx++;
        panel2.add(wordField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel2.add(frequencyLabel, gbc);
        gbc.gridx++;
        panel2.add(frequencyField, gbc);
        gbc.gridy++;
        panel2.add(addWordButton, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel2.add(back2, gbc);
        gbc.gridx++;
        panel2.add(next2, gbc);

        // Panel 3: File selection
        JPanel panel3 = new JPanel(new GridBagLayout());
        JButton selectFileButton = new JButton("Select a file:");
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                path = selectedFile.getAbsolutePath();
                try {
                    Scanner scannerFile = new Scanner(new File(path));
                    StringBuilder fileContent = new StringBuilder();
                    while (scannerFile.hasNextLine()) {
                        fileContent.append(scannerFile.nextLine()).append("\n");
                    }
                    String text = fileContent.toString();
                    try {
                        wordList = convertFileToWordList(text);

                    } catch (CreateWordListFromFile c) {
                        JOptionPane.showMessageDialog(this, c.getMessage());
                    }

                } catch (FileNotFoundException f) {
                    JOptionPane.showMessageDialog(this, "File not found");
                }
            }
        });
        JButton next3 = new JButton("Next");
        next3.addActionListener(e -> {
            prevPanel.add(3);
            cardLayout.show(cardPanel, "Panel 4");
        });
        JButton back3 = new JButton("Back");
        back3.addActionListener(e -> cardLayout.show(cardPanel, "Panel " + prevPanel.pop()));
        JPanel buttons3 = new JPanel(new FlowLayout());
        buttons3.add(back3);
        buttons3.add(next3);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panel3.add(selectFileButton, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel3.add(buttons3, gbc);

        // Panel 4: Alphabet name
        JPanel panel4 = new JPanel(new GridBagLayout());
        JLabel alphabetName = new JLabel("Name of alphabet:");
        JTextField alphabetNameField = new JTextField(10);
        JRadioButton autoAlphabetOption = new JRadioButton("Automatically create alphabet");
        JRadioButton existingAlphabetOption = new JRadioButton("Choose an existing alphabet");
        ButtonGroup group4 = new ButtonGroup();
        group4.add(autoAlphabetOption);
        group4.add(existingAlphabetOption);
        JButton next4 = new JButton("Next");
        next4.addActionListener(e -> {
            boolean isAlphabetNameValid = existingAlphabetOption.isSelected() || !alphabetNameField.getText().isEmpty();
            if (isAlphabetNameValid && (autoAlphabetOption.isSelected() || existingAlphabetOption.isSelected())) {
                prevPanel.add(3);
                if (autoAlphabetOption.isSelected()) {
                    interfaceCtrl.createWordListAndAlphabet(nameField.getText(), wordList, alphabetNameField.getText());
                    succeed = interfaceCtrl.getMessage();
                    successLabel.setText(succeed);
                    cardLayout.show(cardPanel, "Panel 6");
                } else {
                    ArrayList<String> listAlphabets = new ArrayList<>();
                    if (existingAlphabetOption.isSelected()) {
                        listAlphabets = interfaceCtrl.getNamesAlphabetForWordList(wordList);
                    }
                    if (!listAlphabets.isEmpty()) {
                        String[] alphabetsNames = new String[listAlphabets.size()];
                        listAlphabets.toArray(alphabetsNames);
                        alphabetsList.setListData(alphabetsNames);
                        cardLayout.show(cardPanel, "Panel 5");
                    } else {
                        JOptionPane.showMessageDialog(this, "There's no alphabet that matches with this file");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a name and select an option");
            }
        });
        JButton back4 = new JButton("Back");
        back4.addActionListener(e -> cardLayout.show(cardPanel, "Panel " + prevPanel.pop()));
        JPanel buttons4 = new JPanel(new FlowLayout());
        buttons4.add(back4);
        buttons4.add(next4);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panel4.add(alphabetName, gbc);
        gbc.gridy++;
        panel4.add(alphabetNameField, gbc);
        gbc.gridy++;
        panel4.add(autoAlphabetOption, gbc);
        gbc.gridy++;
        panel4.add(existingAlphabetOption, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel4.add(buttons4, gbc);

        // Panel 5: Alphabet list selection
        JPanel panel5 = new JPanel(new GridBagLayout());
        JLabel selectAlphabet = new JLabel("Select an alphabet: ");
        JButton next5 = new JButton("Next");
        next5.addActionListener(e -> {
            if (!alphabetsList.isSelectionEmpty()) {
                interfaceCtrl.createWordList(nameField.getText(), wordList, alphabetsList.getSelectedValue());
                succeed = interfaceCtrl.getMessage();
                successLabel.setText(succeed);
                cardLayout.show(cardPanel, "Panel 6");
            } else {
                JOptionPane.showMessageDialog(this, "No alphabet selected");
            }
        });
        JButton back5 = new JButton("Back");
        back5.addActionListener(e -> cardLayout.show(cardPanel, "Panel " + prevPanel.pop()));
        JPanel buttons5 = new JPanel(new FlowLayout());
        buttons5.add(back5);
        buttons5.add(next5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        JScrollPane alphabetsListScroll = new JScrollPane(alphabetsList);
        alphabetsListScroll.setPreferredSize(new Dimension(200, 100));
        panel5.add(selectAlphabet);
        gbc.gridy++;
        panel5.add(alphabetsListScroll, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel5.add(buttons5, gbc);

        // Panel 6: Success message and close button
        JPanel panel6 = new JPanel(new GridBagLayout());
        successLabel = new JLabel(succeed);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panel6.add(successLabel, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel6.add(closeButton, gbc);

        // Add panels to cardPanel
        cardPanel.add(panel1, "Panel 1");
        cardPanel.add(panel2, "Panel 2");
        cardPanel.add(panel3, "Panel 3");
        cardPanel.add(panel4, "Panel 4");
        cardPanel.add(panel5, "Panel 5");
        cardPanel.add(panel6, "Panel 6");
    }

    /**
     * Checks if a string represents a positive integer.
     *
     * @param str The string to check.
     * @return True if the string is a positive integer, false otherwise.
     */
    public static boolean isPositiveInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("\\d+");
    }

    /**
     * Creates the word list of a text.
     *
     * @param text The text to convert.
     * @return The word list of the text.
     */
    private HashMap<String, Integer> convertFileToWordList(String text) throws CreateWordListFromFile {
        String[] rows = text.split("\\n");
        wordList = new HashMap<>();

        for (String row : rows) {
            String[] wf = row.split(" ");
            if (wf.length != 2) throw new CreateWordListFromFile(wf.length);
            String word = wf[0];
            if (!isPositiveInteger(wf[1])) throw new CreateWordListFromFile();
            int freq = Integer.parseInt(wf[1]);
            wordList.put(word, freq);
        }
        return wordList;
    }
}

package Interface.Dialogs.File;

import Interface.InterfaceController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The NewTextDialog class represents a dialog for creating a new text.
 * @author Esther Lozano
 */
public class NewTextDialog extends JDialog {

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private Stack<Integer> prevPanel = new Stack<>();
    private String path = "";
    private String text = "";
    private String succeed = "";
    private JLabel successLabel = new JLabel();
    private JList<String> alphabetsList = new JList<>();

    /**
     * Constructs a NewTextDialog.
     *
     * @param parent       The parent frame of the dialog.
     * @param interfaceCtrl The interface controller for handling actions.
     */
    public NewTextDialog(JFrame parent, InterfaceController interfaceCtrl) {
        super(parent, "New Text", true);
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

        // Panel 1: Name Text
        JPanel panel1 = new JPanel(new GridBagLayout());
        JLabel nameText = new JLabel("Name of text:");
        JTextField nameField = new JTextField(10);
        JRadioButton manualTextOption = new JRadioButton("Text written on terminal App");
        JRadioButton fileTextOption = new JRadioButton("Text of a file");
        ButtonGroup group1 = new ButtonGroup();
        group1.add(manualTextOption);
        group1.add(fileTextOption);
        JButton next1 = new JButton("Next");
        next1.addActionListener(e -> {
            if (!nameField.getText().isEmpty() && (manualTextOption.isSelected() || fileTextOption.isSelected())) {
                prevPanel.add(1);
                if (manualTextOption.isSelected()) {
                    text = interfaceCtrl.getActualText();
                }
                cardLayout.show(cardPanel, manualTextOption.isSelected() ? "Panel 3" : "Panel 2");
            } else {
                JOptionPane.showMessageDialog(this, "Should put a name and select an option");
            }
        });
        panel1.add(nameText, gbc);
        gbc.gridy++;
        panel1.add(nameField, gbc);
        gbc.gridy++;
        panel1.add(manualTextOption, gbc);
        gbc.gridy++;
        panel1.add(fileTextOption, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(next1, gbc);

        // Panel 2: Input from file.txt
        JPanel panel2 = new JPanel(new GridBagLayout());
        JButton selectFileButton = new JButton("Select an archive:");
        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Files of Text (*.txt)", "txt");
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
                    text = fileContent.toString();
                } catch (FileNotFoundException f) {
                    JOptionPane.showMessageDialog(this, "File not found");
                }
            }
        });
        JButton next2 = new JButton("Next");
        next2.addActionListener(e -> {
            prevPanel.add(2);
            cardLayout.show(cardPanel, "Panel 3");
        });
        JButton back2 = new JButton("Back");
        back2.addActionListener(e -> cardLayout.show(cardPanel, "Panel " + prevPanel.pop()));
        JPanel buttons2 = new JPanel(new FlowLayout());
        buttons2.add(back2);
        buttons2.add(next2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(selectFileButton, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel2.add(buttons2, gbc);

        // Panel 3: Alphabet name
        JPanel panel3 = new JPanel(new GridBagLayout());
        JLabel alphabetName = new JLabel("Name of alphabet: ");
        JTextField alphabetNameField = new JTextField(10);
        JRadioButton autoAlphabetOption = new JRadioButton("Create automatically alphabet");
        JRadioButton existingAlphabetOption = new JRadioButton("Choose existing alphabet");
        ButtonGroup group3 = new ButtonGroup();
        group3.add(autoAlphabetOption);
        group3.add(existingAlphabetOption);
        JButton next3 = new JButton("Next");
        next3.addActionListener(e -> {
            boolean isAlphabetNameValid = existingAlphabetOption.isSelected() || !alphabetNameField.getText().isEmpty();
            if (isAlphabetNameValid && (autoAlphabetOption.isSelected() || existingAlphabetOption.isSelected())) {
                prevPanel.add(3);
                if (autoAlphabetOption.isSelected()) {
                    interfaceCtrl.createTextAndAlphabet(nameField.getText(), text, alphabetNameField.getText());
                    succeed = interfaceCtrl.getMessage();
                    successLabel.setText(succeed);
                    cardLayout.show(cardPanel, "Panel 5");
                } else {
                    ArrayList<String> listAlphabets = new ArrayList<>();
                    if (existingAlphabetOption.isSelected()) {
                        listAlphabets = interfaceCtrl.getNamesAlphabetForText(text);
                    }
                    if (!listAlphabets.isEmpty()) {
                        String[] alphabetsNames = new String[listAlphabets.size()];
                        listAlphabets.toArray(alphabetsNames);
                        alphabetsList.setListData(alphabetsNames);
                        cardLayout.show(cardPanel, "Panel 4");
                    } else {
                        JOptionPane.showMessageDialog(this, "There's no alphabet that match with this file");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a name and select an option");
            }
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
        panel3.add(alphabetName, gbc);
        gbc.gridy++;
        panel3.add(alphabetNameField, gbc);
        gbc.gridy++;
        panel3.add(autoAlphabetOption, gbc);
        gbc.gridy++;
        panel3.add(existingAlphabetOption, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel3.add(buttons3, gbc);

        // Panel 4: Alphabet list selection
        JPanel panel4 = new JPanel(new GridBagLayout());
        JLabel selectAlphabet = new JLabel("Select an alphabet: ");
        JButton next4 = new JButton("Next");
        next4.addActionListener(e -> {
            if (!alphabetsList.isSelectionEmpty()) {
                interfaceCtrl.createText(nameField.getText(), text, alphabetsList.getSelectedValue());
                succeed = interfaceCtrl.getMessage();
                successLabel.setText(succeed);
                cardLayout.show(cardPanel, "Panel 5");
            } else {
                JOptionPane.showMessageDialog(this, "No selected alphabet");
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
        JScrollPane alphabetsListScroll = new JScrollPane(alphabetsList);
        alphabetsListScroll.setPreferredSize(new Dimension(200, 100));
        panel4.add(selectAlphabet, gbc);
        gbc.gridy++;
        panel4.add(alphabetsListScroll, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panel4.add(buttons4, gbc);

        // Panel 5: Success message and close button
        JPanel panel5 = new JPanel(new GridBagLayout());
        successLabel = new JLabel(succeed);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        panel5.add(successLabel, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel5.add(closeButton, gbc);

        cardPanel.add(panel1, "Panel 1");
        cardPanel.add(panel2, "Panel 2");
        cardPanel.add(panel3, "Panel 3");
        cardPanel.add(panel4, "Panel 4");
        cardPanel.add(panel5, "Panel 5");
    }
}

package Interface.Dialogs.Keyboard;

import Exceptions.EmptyAlphabetException;
import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The CreateKeyboardDialog class represents a dialog for creating a new keyboard configuration.
 * @author Esther Lozano
 */
public class CreateKeyboardDialog extends JDialog {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private String succeed = "";
    private JLabel successLabel = new JLabel();

    /**
     * Constructs a CreateKeyboardDialog.
     *
     * @param parent       The parent frame of the dialog.
     * @param interfaceCtrl The interface controller for handling actions.
     */
    public CreateKeyboardDialog(Frame parent, InterfaceController interfaceCtrl) {
        super(parent, "Create Keyboard", true);
        setSize(300, 300);
        setLocationRelativeTo(parent);
        boolean completed = initComponents(interfaceCtrl);
        if (completed) setVisible(true);
    }

    /**
     * Initializes the components of the dialog.
     *
     * @param interfaceCtrl The interface controller managing the actions of the dialog components.
     * @return True if initialization is completed successfully, false otherwise.
     */
    private boolean initComponents(InterfaceController interfaceCtrl) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 1, 5, 1);
        gbc.anchor = GridBagConstraints.WEST;

        // First screen panel
        JPanel listPanel = new JPanel(new GridBagLayout());
        JLabel selectAlphabet = new JLabel("Select an alphabet: ");
        ArrayList<String> listAlphabets;
        try {
            listAlphabets = interfaceCtrl.getNamesAlphabet();
        } catch (EmptyAlphabetException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return false;
        }
        String[] alphabetsNames = new String[listAlphabets.size()];
        listAlphabets.toArray(alphabetsNames);
        JList<String> alphabetsList = new JList<>(alphabetsNames);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if (!alphabetsList.isSelectionEmpty())
                cardLayout.show(cardPanel, "ConfigPanel");
            else JOptionPane.showMessageDialog(this, "Should select an option");
        });
        JScrollPane alphabetsListScroll = new JScrollPane(alphabetsList);
        alphabetsListScroll.setPreferredSize(new Dimension(200, 100));
        listPanel.add(selectAlphabet, gbc);
        gbc.gridy++;
        listPanel.add(alphabetsListScroll, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        listPanel.add(nextButton, gbc);

        // Second screen panel
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new GridBagLayout());
        JSpinner spinnerRows = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
        spinnerRows.setPreferredSize(new Dimension(40,20));
        JSpinner spinnerCols = new JSpinner(new SpinnerNumberModel(1, 1, 24, 1));
        spinnerCols.setPreferredSize(new Dimension(40, 20));
        String[] size = {"Small", "Medium", "Big"};
        JComboBox<String> comboBoxSize = new JComboBox<>(size);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "ListPanel"));
        JButton finishButton = new JButton("Create");
        finishButton.addActionListener(e -> {
            String nameAlphabet = alphabetsList.getSelectedValue();
            int numKeys = interfaceCtrl.getSizeAlphabet(nameAlphabet);
            int rows = (Integer) spinnerRows.getValue();
            int cols = (Integer) spinnerCols.getValue();
            if (rows * cols >= numKeys) {
                int sizeKey = 50;
                if (comboBoxSize.getSelectedIndex() == 0) sizeKey = 50;
                else if (comboBoxSize.getSelectedIndex() == 1) sizeKey = 55;
                else if (comboBoxSize.getSelectedIndex() == 2) sizeKey = 60;
                interfaceCtrl.createKeyboard(rows, cols, sizeKey, nameAlphabet);
                succeed = interfaceCtrl.getMessage();
                successLabel.setText(succeed);
                cardLayout.show(cardPanel, "MessagePanel");
            } else JOptionPane.showMessageDialog(this, "The number of letters in the alphabet is " + numKeys
                    + ", you should put more rows or columns for all the letters");
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JPanel rowPanel = new JPanel(new FlowLayout());
        rowPanel.add(new JLabel("Rows:     "));
        rowPanel.add(spinnerRows);
        configPanel.add(rowPanel, gbc);
        gbc.gridy++;
        JPanel colsPanel = new JPanel(new FlowLayout());
        colsPanel.add(new JLabel("Columns:"));
        colsPanel.add(spinnerCols);
        configPanel.add(colsPanel, gbc);
        gbc.gridy++;
        JPanel sizePanel = new JPanel(new FlowLayout());
        sizePanel.add(new JLabel("Size of Keys:"));
        sizePanel.add(comboBoxSize);
        configPanel.add(sizePanel, gbc);
        JPanel buttons4 = new JPanel(new FlowLayout());
        buttons4.add(backButton);
        buttons4.add(finishButton);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        configPanel.add(buttons4, gbc);

        // Third screen panel
        JPanel messagePanel = new JPanel(new GridBagLayout());
        successLabel = new JLabel(succeed);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            interfaceCtrl.openActualKeyboard();
            dispose();
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        messagePanel.add(successLabel, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        messagePanel.add(closeButton, gbc);

        // Add panels to the CardLayout
        cardPanel.add(listPanel, "ListPanel");
        cardPanel.add(configPanel, "ConfigPanel");
        cardPanel.add(messagePanel, "MessagePanel");
        add(cardPanel);

        // Show the first screen initially
        cardLayout.show(cardPanel, "ListPanel");
        return true;
    }
}

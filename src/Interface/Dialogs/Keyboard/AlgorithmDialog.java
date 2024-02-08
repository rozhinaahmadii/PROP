package Interface.Dialogs.Keyboard;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The AlgorithmDialog class represents a dialog for selecting and configuring an optimization algorithm.
 * @author Rozhina Ahmadi
 */
public class AlgorithmDialog extends JDialog {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private String succeed = "";
    private JLabel successLabel = new JLabel();
    private String algorithmSelected = "";

    /**
     * Constructs an AlgorithmDialog.
     *
     * @param parent       The parent frame of the dialog.
     * @param interfaceCtrl The interface controller for handling actions.
     */
    public AlgorithmDialog(Frame parent, InterfaceController interfaceCtrl) {
        super(parent, "Algorithm", true);
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
        JLabel selectFile = new JLabel("Select one or more files: ");
        ArrayList<String> listNamesFiles;
        listNamesFiles = interfaceCtrl.getNamesFilesOfAlphabet();
        if (listNamesFiles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There are no files.");
            return false;
        }

        String[] filesNames = new String[listNamesFiles.size()];
        listNamesFiles.toArray(filesNames);
        JList<String> filesList = new JList<>(filesNames);
        filesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if (!filesList.isSelectionEmpty())
                cardLayout.show(cardPanel, "ConfigPanel");
            else JOptionPane.showMessageDialog(this, "Should select an option");
        });
        JScrollPane filesListScroll = new JScrollPane(filesList);
        filesListScroll.setPreferredSize(new Dimension(200, 100));
        listPanel.add(selectFile, gbc);
        gbc.gridy++;
        listPanel.add(filesListScroll, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        ArrayList<String> selectedFiles = new ArrayList<>();
        filesList.addListSelectionListener(e -> {
            Object[] selectedValues = filesList.getSelectedValues();
            for (Object value : selectedValues) {
                selectedFiles.add((String) value);
            }

            // Create an ArrayList to store selected items
            ArrayList<String> selectedList = new ArrayList<>();
            for (Object selectedValue : selectedValues) {
                selectedList.add((String) selectedValue);
            }

            System.out.println("Selected elements:");
            for (String selected : selectedList) {
                System.out.println(selected);
            }
        });
        listPanel.add(nextButton, gbc);

        // Second screen panel
        JPanel configPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        String[] options = {"Choose Algorithm", "Branch and Bound", "Evolutionary"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        comboBox.addActionListener(e -> {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String selectedOption = (String) combo.getSelectedItem();
            assert selectedOption != null;
            if (selectedOption.equals("Branch and Bound")) {
                algorithmSelected = "Branch and Bound";
            } else if (selectedOption.equals("Evolutionary")) {
                algorithmSelected = "Evolutionary";
            }
            System.out.println("Selected option: " + selectedOption);
        });

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        configPanel.add(comboBox, c);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "ListPanel"));
        JButton finishButton = new JButton("Calculate");
        finishButton.addActionListener(e -> {
            System.out.println(algorithmSelected);
            if (algorithmSelected.equals("")) {
                JOptionPane.showMessageDialog(this, "Select an algorithm to optimize the keyboard.");
            } else {
                interfaceCtrl.calculateLayoutActualKeyboard(selectedFiles, algorithmSelected);
                interfaceCtrl.openActualKeyboard();
            }
        });
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        configPanel.add(backButton, c);
        c.gridx = 1;
        c.gridy = 4;
        configPanel.add(finishButton, c);

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

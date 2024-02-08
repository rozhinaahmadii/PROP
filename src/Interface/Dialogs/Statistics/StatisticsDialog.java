package Interface.Dialogs.Statistics;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The StatisticsDialog class extends JDialog to provide a dialog for displaying statistics.
 * @author Bruno Ruano
 */
public class StatisticsDialog extends JDialog {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private String succeed = "";
    private JLabel successLabel = new JLabel();

    /**
     * Constructor for creating the StatisticsDialog.
     *
     * @param parent       The parent frame in which this dialog is displayed.
     * @param interfaceCtrl The interface controller managing the actions of the dialog components.
     */
    public StatisticsDialog(Frame parent, InterfaceController interfaceCtrl) {
        super(parent, "Statistics", true);
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

        if (!interfaceCtrl.isOpenKeyboard()) {
            JOptionPane.showMessageDialog(this, "There is no keyboard open");
            return false;
        }

        // First screen panel
        JPanel listPanel = new JPanel(new GridBagLayout());
        JLabel selectFiles = new JLabel("Select a file: ");
        ArrayList<String> listNamesFiles = interfaceCtrl.getNamesFilesOfAlphabet();
        if (listNamesFiles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There are no files compatible with the keyboard");
            return false;
        }

        String[] filesNames = new String[listNamesFiles.size()];
        listNamesFiles.toArray(filesNames);
        JList<String> filesList = new JList<>(filesNames);
        filesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton nextButton = new JButton("Show");
        nextButton.addActionListener(e -> {
            if (!filesList.isSelectionEmpty()) {
                interfaceCtrl.showStatistics(filesList.getSelectedValue());
                succeed = interfaceCtrl.getMessage();
                successLabel.setText(succeed);
                cardLayout.show(cardPanel, "Statistics");
            } else {
                JOptionPane.showMessageDialog(this, "Should select an option");
            }
        });

        JScrollPane filesListScroll = new JScrollPane(filesList);
        filesListScroll.setPreferredSize(new Dimension(200, 100));
        listPanel.add(selectFiles);
        gbc.gridy++;
        listPanel.add(filesListScroll, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        listPanel.add(nextButton, gbc);

        // Second screen panel
        JPanel messagePanel = new JPanel(new GridBagLayout());
        successLabel = new JLabel(succeed);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
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
        cardPanel.add(messagePanel, "Statistics");
        add(cardPanel);

        // Show the first screen initially
        cardLayout.show(cardPanel, "ListPanel");
        return true;
    }
}

package Interface.Dialogs.File;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The DeleteFileDialog class represents a dialog for deleting existing text files.
 * @author Bruno Ruano
 */
public class DeleteFileDialog extends JDialog {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private String succeed = "";
    private JLabel successLabel = new JLabel();

    /**
     * Constructs a DeleteFileDialog.
     *
     * @param parent        The parent frame of the dialog.
     * @param interfaceCtrl The interface controller for handling actions.
     */
    public DeleteFileDialog(Frame parent, InterfaceController interfaceCtrl) {
        super(parent, "Delete File", true);
        setSize(300, 300);
        setLocationRelativeTo(parent);
        boolean completed = initComponents(interfaceCtrl);
        if (completed) setVisible(true);
    }

    /**
     * Initializes the components of the dialog.
     *
     * @param interfaceCtrl The interface controller managing the actions of the dialog components.
     */
    private boolean initComponents(InterfaceController interfaceCtrl) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 1, 5, 1);
        gbc.anchor = GridBagConstraints.WEST;

        // Panel of the first screen
        JPanel listPanel = new JPanel(new GridBagLayout());
        JLabel selectFile = new JLabel("Select a file: ");
        ArrayList<String> listNamesFiles = interfaceCtrl.getNamesFiles();
        if (listNamesFiles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No files saved to delete");
            return false;
        }
        String[] filesNames = new String[listNamesFiles.size()];
        listNamesFiles.toArray(filesNames);
        JList<String> filesList = new JList<>(filesNames);
        JButton nextButton = new JButton("Delete");
        nextButton.addActionListener(e -> {
            if (!filesList.isSelectionEmpty()) {
                interfaceCtrl.deleteFile(filesList.getSelectedValue());
                succeed = interfaceCtrl.getMessage();
                successLabel.setText(succeed);
                cardLayout.show(cardPanel, "MessagePanel");
            } else {
                JOptionPane.showMessageDialog(this, "Should select an option");
            }
        });
        JScrollPane filesListScroll = new JScrollPane(filesList);
        filesListScroll.setPreferredSize(new Dimension(200, 100));
        listPanel.add(selectFile, gbc);
        gbc.gridy++;
        listPanel.add(filesListScroll, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        listPanel.add(nextButton, gbc);

        // Panel of the second screen
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
        cardPanel.add(messagePanel, "MessagePanel");
        add(cardPanel);

        // Show the first screen initially
        cardLayout.show(cardPanel, "ListPanel");
        return true;
    }
}

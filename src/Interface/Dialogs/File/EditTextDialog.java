package Interface.Dialogs.File;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The EditTextDialog class represents a dialog for editing existing text files.
 * @author Jiahao Liu
 */
public class EditTextDialog extends JDialog {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private String succeed = "";
    private JLabel successLabel = new JLabel();

    /**
     * Constructs an EditTextDialog.
     *
     * @param parent        The parent frame of the dialog.
     * @param interfaceCtrl The interface controller for handling actions.
     */
    public EditTextDialog(Frame parent, InterfaceController interfaceCtrl) {
        super(parent, "Edit Text", true);
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
        JLabel selectAlphabet = new JLabel("Select alphabet: ");
        ArrayList<String> listNamesFiles = interfaceCtrl.getNamesTexts();
        if (listNamesFiles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No text saved to edit");
            return false;
        }
        String[] filesNames = new String[listNamesFiles.size()];
        listNamesFiles.toArray(filesNames);
        JList<String> filesList = new JList<>(filesNames);
        JButton nextButton = new JButton("Edit");
        nextButton.addActionListener(e -> {
            if (!filesList.isSelectionEmpty()) {
                interfaceCtrl.openText(filesList.getSelectedValue());
                succeed = interfaceCtrl.getMessage();
                successLabel.setText(succeed);
                cardLayout.show(cardPanel, "MessagePanel");
            } else {
                JOptionPane.showMessageDialog(this, "Should select an option");
            }
        });
        JScrollPane filesListScroll = new JScrollPane(filesList);
        filesListScroll.setPreferredSize(new Dimension(200, 100));
        listPanel.add(selectAlphabet, gbc);
        gbc.gridy++;
        listPanel.add(filesListScroll, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        listPanel.add(nextButton, gbc);

        // Panel of the second screen
        JPanel messagePanel = new JPanel(new GridBagLayout());
        successLabel = new JLabel(succeed);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            interfaceCtrl.paintPrincipalWindowEditTextPanel();
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
        cardPanel.add(messagePanel, "MessagePanel");
        add(cardPanel);

        // Show the first screen initially
        cardLayout.show(cardPanel, "ListPanel");
        return true;
    }
}

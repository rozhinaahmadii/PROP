package Interface.Dialogs.Keyboard;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The OpenKeyboardDialog class represents a dialog for opening a saved keyboard configuration.
 * @author Esther Lozano
 */
public class OpenKeyboardDialog extends JDialog {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private String succeed = "";
    private JLabel successLabel = new JLabel();

    /**
     * Constructs an OpenKeyboardDialog.
     *
     * @param parent       The parent frame of the dialog.
     * @param interfaceCtrl The interface controller for handling actions.
     */
    public OpenKeyboardDialog(Frame parent, InterfaceController interfaceCtrl) {
        super(parent, "Open Keyboard", true);
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
        JLabel selectKeyboard = new JLabel("Select the saved keyboard to open:");
        ArrayList<String> listNamesKeyboards = interfaceCtrl.getNamesSavedKeyboards();
        if (listNamesKeyboards.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No Keyboards saved to open");
            return false;
        }
        String[] savedKeyboardsNames = new String[listNamesKeyboards.size()];
        listNamesKeyboards.toArray(savedKeyboardsNames);
        JList<String> keyboardsList = new JList<>(savedKeyboardsNames);
        JButton nextButton = new JButton("Open");
        nextButton.addActionListener(e -> {
            if (!keyboardsList.isSelectionEmpty()) {
                interfaceCtrl.openKeyboard(keyboardsList.getSelectedValue());
                succeed = "Successfully Open";
                successLabel.setText(succeed);
                cardLayout.show(cardPanel, "MessagePanel");
            } else {
                JOptionPane.showMessageDialog(this, "Should select an option");
            }
        });
        JScrollPane alphabetsListScroll = new JScrollPane(keyboardsList);
        alphabetsListScroll.setPreferredSize(new Dimension(200, 100));
        listPanel.add(selectKeyboard, gbc);
        gbc.gridy++;
        listPanel.add(alphabetsListScroll, gbc);
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
        cardPanel.add(messagePanel, "MessagePanel");
        add(cardPanel);

        // Show the first screen initially
        cardLayout.show(cardPanel, "ListPanel");
        return true;
    }
}

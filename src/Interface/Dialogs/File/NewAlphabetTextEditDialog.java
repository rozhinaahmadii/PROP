package Interface.Dialogs.File;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;

/**
 * The NewAlphabetTextEditDialog class represents a dialog for creating a new alphabet or editing an existing one.
 * @author Rozhina Ahmadi
 */
public class NewAlphabetTextEditDialog extends JDialog {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private String succeed = "";
    private JLabel successLabel = new JLabel();

    /**
     * Constructs a NewAlphabetTextEditDialog.
     *
     * @param parent        The parent frame of the dialog.
     * @param interfaceCtrl The interface controller for handling actions.
     */
    public NewAlphabetTextEditDialog(Frame parent, InterfaceController interfaceCtrl) {
        super(parent, "Create New Alphabet", true);
        setSize(300, 300);
        setLocationRelativeTo(parent);
        initComponents(interfaceCtrl);
        setVisible(true);
    }

    /**
     * Initializes the components of the dialog.
     *
     * @param interfaceCtrl The interface controller managing the actions of the dialog components.
     */
    private void initComponents(InterfaceController interfaceCtrl) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 1, 5, 1);
        gbc.anchor = GridBagConstraints.CENTER;

        // Panel of the first screen
        JPanel namePanel = new JPanel(new GridBagLayout());
        JLabel nameLabel = new JLabel("Name for new alphabet: ");
        JTextField nameTextField = new JTextField(15);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            boolean created = interfaceCtrl.createAlphabetEditText(nameTextField.getText());
            if (created) {
                succeed = interfaceCtrl.getMessage();
                successLabel.setText(succeed);
                cardLayout.show(cardPanel, "ClosePanel");
            } else {
                JOptionPane.showMessageDialog(this, interfaceCtrl.getMessage());
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        namePanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        namePanel.add(nameTextField, gbc);

        gbc.gridy++;
        gbc.fill = 0;
        namePanel.add(saveButton, gbc);

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
        cardPanel.add(namePanel, "NamePanel");
        cardPanel.add(messagePanel, "ClosePanel");
        add(cardPanel);

        // Show the first screen initially
        cardLayout.show(cardPanel, "NamePanel");
    }
}

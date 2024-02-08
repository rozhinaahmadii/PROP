package Interface.Panels;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * WelcomePanel displays the welcome message and provides options to create a user or log in.
 * @author Jiahao Liu
 */
public class WelcomePanel extends JPanel {

    /**
     * Constructs the WelcomePanel with the specified InterfaceController.
     *
     * @param interfaceCtrl The InterfaceController to handle user interactions.
     */
    public WelcomePanel(InterfaceController interfaceCtrl) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 20, 50);

        // Title
        JLabel titleLabel = new JLabel("Welcome to EasyType");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        gbc.gridy++; // Move to the next row
        add(titleLabel, gbc);

        // Buttons
        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
        JButton createUser = new JButton("Create User");
        JButton loginUser = new JButton("Log In");

        // Button size
        Dimension buttonSize = new Dimension(110, 25);
        createUser.setMinimumSize(buttonSize);
        createUser.setPreferredSize(buttonSize);
        createUser.setMaximumSize(buttonSize);

        loginUser.setMinimumSize(buttonSize);
        loginUser.setPreferredSize(buttonSize);
        loginUser.setMaximumSize(buttonSize);

        panelButton.add(Box.createVerticalStrut(10));
        panelButton.add(createUser);
        panelButton.add(Box.createVerticalStrut(10));
        panelButton.add(loginUser);

        gbc.gridy++;
        add(panelButton, gbc);

        // Button listeners
        createUser.addActionListener(e -> interfaceCtrl.showViewCreateUser());

        loginUser.addActionListener(e -> interfaceCtrl.showViewLogIn());

        // Add KeyListener to capture "Enter" key press
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JButton sourceButton = (JButton) e.getSource();
                    sourceButton.doClick();
                }
            }
        };
        createUser.addKeyListener(keyAdapter);
        loginUser.addKeyListener(keyAdapter);
    }
}

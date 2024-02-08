package Interface.Panels.User;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * LogInPanel displays a user login interface.
 * @author Jiahao Liu
 */
public class LogInPanel extends JPanel {
    /**
     * Constructs a LogInPanel with a user login interface.
     *
     * @param interfaceCtrl The interface controller for managing user interactions.
     */
    public LogInPanel(InterfaceController interfaceCtrl) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 20, 50);

        JLabel titleLabel = new JLabel("Log In");
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        add(titleLabel, gbc);

        JPanel panelAttributes = new JPanel();
        panelAttributes.setLayout(new BoxLayout(panelAttributes, BoxLayout.Y_AXIS));

        Dimension boxSize = new Dimension(110, 25);

        // Username
        JPanel panelUser = new JPanel();
        panelUser.setLayout(new BoxLayout(panelUser, BoxLayout.X_AXIS));

        JLabel userLabel = new JLabel("Username");
        JTextField userBox = new JTextField();

        userBox.setMinimumSize(boxSize);
        userBox.setPreferredSize(boxSize);
        userBox.setMaximumSize(boxSize);

        panelUser.add(userLabel);
        panelUser.add(Box.createHorizontalStrut(10));
        panelUser.add(userBox);

        panelAttributes.add(panelUser);

        // Password
        JPanel panelPass = new JPanel();
        panelPass.setLayout(new BoxLayout(panelPass, BoxLayout.X_AXIS));

        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordBox = new JPasswordField();

        passwordBox.setMinimumSize(boxSize);
        passwordBox.setPreferredSize(boxSize);
        passwordBox.setMaximumSize(boxSize);

        panelPass.add(passwordLabel);
        panelPass.add(Box.createHorizontalStrut(10));
        panelPass.add(passwordBox);

        panelAttributes.add(Box.createVerticalStrut(10));
        panelAttributes.add(panelPass);

        // Buttons
        Dimension buttonSize = new Dimension(110, 25);
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        JButton back = new JButton("Back");
        JButton accept = new JButton("Log In");

        back.setMinimumSize(buttonSize);
        back.setPreferredSize(buttonSize);
        back.setMaximumSize(buttonSize);

        accept.setMinimumSize(buttonSize);
        accept.setPreferredSize(buttonSize);
        accept.setMaximumSize(buttonSize);

        panelButtons.add(back);
        panelButtons.add(Box.createHorizontalStrut(10));
        panelButtons.add(accept);

        panelAttributes.add(Box.createVerticalStrut(10));
        panelAttributes.add(panelButtons);

        gbc.gridy++;
        add(panelAttributes, gbc);

        // Error messages
        JLabel msgErrorLabel = new JLabel();
        msgErrorLabel.setForeground(Color.RED);
        msgErrorLabel.setVisible(false);
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(msgErrorLabel, gbc);

        accept.addActionListener(e -> {
            boolean loginUser = interfaceCtrl.checkLogIn(userBox.getText(), passwordBox.getText());
            if (loginUser) {
                interfaceCtrl.createPrincipalWindow();
                interfaceCtrl.paintPrincipalWindowPanel();
            } else {
                String error = interfaceCtrl.getMessage();
                msgErrorLabel.setText(error);
                msgErrorLabel.setVisible(true);
            }
        });

        passwordBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Not commonly used for Enter key detection
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_ENTER) {
                    boolean loginUser = interfaceCtrl.checkLogIn(userBox.getText(), passwordBox.getText());
                    if (loginUser) {
                        interfaceCtrl.createPrincipalWindow();
                        interfaceCtrl.paintPrincipalWindowPanel();
                    } else {
                        String error = interfaceCtrl.getMessage();
                        msgErrorLabel.setText(error);
                        msgErrorLabel.setVisible(true);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Not commonly used for Enter key detection
            }
        });

        back.addActionListener(e -> interfaceCtrl.paintWelcomePanel());

        // Add KeyListener to capture the "Enter" key
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JButton sourceButton = (JButton) e.getSource();
                    sourceButton.doClick();
                }
            }
        };
        back.addKeyListener(keyAdapter);
        accept.addKeyListener(keyAdapter);
    }
}

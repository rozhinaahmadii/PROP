package Interface.Menus;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The MenuEditText class that extends JMenuBar to provide a menu bar specific for text editing.
 * @author Bruno Ruano
 */
public class MenuEditText extends JMenuBar {
    /**
     * Constructor that creates the menu bar with options for saving changes in edited texts.
     *
     * @param parentFrame    The main frame of the application where this menu is displayed.
     * @param interfaceCtrl  The interface controller that manages actions of menu items.
     */
    public MenuEditText(JFrame parentFrame, InterfaceController interfaceCtrl) {
        // Save Text
        JMenu saveText = new JMenu("Save Text");

        saveText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                interfaceCtrl.editText();
                interfaceCtrl.paintPrincipalWindowAfterEditText();
            }
        });
        add(saveText);
        Action profileMenuAction = new AbstractAction("", resizeImageIcon(new ImageIcon("./icon/user_setting.png"), 25, 25)) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        // Profile Menu
        JMenu profileMenu = new JMenu(profileMenuAction);

        JMenuItem closeUser = new JMenuItem("Close User");
        JMenuItem deleteUser = new JMenuItem("Delete User");

        closeUser.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Are you sure you want to close the account?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                interfaceCtrl.closeUser();

                JOptionPane.showMessageDialog(
                        parentFrame,
                        "User successfully exited",
                        "Exit Completed",
                        JOptionPane.INFORMATION_MESSAGE
                );
                interfaceCtrl.paintWelcomePanel();
            }
        });

        deleteUser.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    parentFrame,
                    "Are you sure you want to delete the user?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                interfaceCtrl.deleteUser();

                JOptionPane.showMessageDialog(
                        parentFrame,
                        "User successfully deleted",
                        "Deletion Completed",
                        JOptionPane.INFORMATION_MESSAGE
                );
                interfaceCtrl.paintWelcomePanel();
            }
        });

        profileMenu.add(closeUser);
        profileMenu.add(deleteUser);

        profileMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        add(Box.createHorizontalGlue());
        add(profileMenu);

        profileMenu.add(closeUser);
        profileMenu.add(deleteUser);
        add(profileMenu);
    }

    /**
     * Helper method to resize an image icon.
     *
     * @param icon   The original icon to resize.
     * @param width  The desired width for the new icon.
     * @param height The desired height for the new icon.
     * @return ImageIcon resized according to the specified parameters.
     */
    private static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
}


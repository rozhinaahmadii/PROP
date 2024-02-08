package Interface.Menus;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The MenuEditKeyboard class extends JMenuBar to provide a menu bar specific for keyboard editing.
 * @author Esther Lozano
 */
public class MenuEditKeyboard extends JMenuBar {
    /**
     * Constructor that creates the menu bar with options to save and go back in keyboard editing.
     *
     * @param parentFrame   The main frame of the application where this menu is displayed.
     * @param interfaceCtrl The interface controller that manages actions of menu items.
     */
    public MenuEditKeyboard(JFrame parentFrame, InterfaceController interfaceCtrl) {
        // Edit Keyboard
        JMenu saveKeyboard = new JMenu("Change Key's");
        JMenu backKeyboard = new JMenu("Back");

        backKeyboard.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                interfaceCtrl.openActualKeyboard();
            }
        });

        saveKeyboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!interfaceCtrl.isOpenKeyboard()) JOptionPane.showMessageDialog(parentFrame, "There is no keyboard open");
                boolean edited = interfaceCtrl.editKeyboard();
                if (!edited) JOptionPane.showMessageDialog(parentFrame, "Select 2 keys for editing");
                else interfaceCtrl.paintPrincipalWindowAfterEditText();
            }
        });
        add(saveKeyboard);
        add(backKeyboard);
        Action profileMenuAction = new AbstractAction("", resizeImageIcon(new ImageIcon("./icon/user_setting.png"))) {
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
     * @param icon The original icon to resize.
     * @return ImageIcon resized according to the specified parameters.
     */
    private static ImageIcon resizeImageIcon(ImageIcon icon) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }
}

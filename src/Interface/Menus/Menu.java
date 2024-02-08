package Interface.Menus;

import Interface.InterfaceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The Menu class extends JMenuBar to provide a customized menu bar in the user interface.
 * @author Rozhina Ahmadi, Jiahao Liu, Esther Lozano, and Bruno Ruano
 */
public class Menu extends JMenuBar {
    /**
     * Constructor that creates the menu bar with available options.
     *
     * @param parentFrame   The main frame of the application where this menu is displayed.
     * @param interfaceCtrl The interface controller that manages actions of menu items.
     */
    public Menu(JFrame parentFrame, InterfaceController interfaceCtrl) {
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newText = new JMenuItem("New Text");
        JMenuItem newWordList = new JMenuItem("New Word List");
        JMenuItem editText = new JMenuItem("Edit Text");
        JMenuItem deleteItem = new JMenuItem("Delete File");

        newText.addActionListener(e -> {
            interfaceCtrl.saveText();
            interfaceCtrl.createNewTextDialog();
        });

        newWordList.addActionListener(e -> interfaceCtrl.createNewWordListDialog());

        editText.addActionListener(e -> interfaceCtrl.createEditTextDialog());

        deleteItem.addActionListener(e -> interfaceCtrl.createDeleteFileDialog());

        fileMenu.add(newText);
        fileMenu.add(newWordList);
        fileMenu.add(editText);
        fileMenu.add(deleteItem);
        add(fileMenu);

        // Keyboard Menu
        JMenu keyboardMenu = new JMenu("Keyboard");
        JMenuItem createKeyboard = new JMenuItem("Create Keyboard");
        JMenuItem openKeyboard = new JMenuItem("Open Keyboard");
        JMenuItem editKeyboard = new JMenuItem("Edit Keyboard");
        JMenuItem optimizeKeyboard = new JMenuItem("Optimize Keyboard");
        JMenuItem saveKeyboard = new JMenuItem("Save Keyboard");
        JMenuItem deleteKeyboard = new JMenuItem("Delete Keyboard");
        JMenuItem showStatistics = new JMenuItem("Show Statistics");

        createKeyboard.addActionListener(e -> interfaceCtrl.createCreateKeyboardDialog());

        openKeyboard.addActionListener(e -> interfaceCtrl.createOpenKeyboardDialog());

        editKeyboard.addActionListener(e -> {
            boolean painted = interfaceCtrl.paintEditKeyboardPanel();
            if (!painted) JOptionPane.showMessageDialog(parentFrame, "There's no Keyboard open");
        });

        optimizeKeyboard.addActionListener(e -> interfaceCtrl.createAlgorithmDialog());

        saveKeyboard.addActionListener(e -> interfaceCtrl.createSaveKeyboardDialog());

        deleteKeyboard.addActionListener(e -> interfaceCtrl.createDeleteKeyboardDialog());

        showStatistics.addActionListener(e -> interfaceCtrl.createStatisticsDialog());

        keyboardMenu.add(createKeyboard);
        keyboardMenu.add(openKeyboard);
        keyboardMenu.add(editKeyboard);
        keyboardMenu.add(optimizeKeyboard);
        keyboardMenu.add(saveKeyboard);
        keyboardMenu.add(deleteKeyboard);
        keyboardMenu.add(showStatistics);
        add(keyboardMenu);

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

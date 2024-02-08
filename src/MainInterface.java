import Interface.InterfaceController;

import javax.swing.*;


public class MainInterface {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfaceController interfaceController = new InterfaceController();
                interfaceController.createWelcomeWindow();
                interfaceController.iniWelcomeWindow();
            }
        });
    }
}

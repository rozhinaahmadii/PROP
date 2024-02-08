package Interface.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * PrincipalWindowPanel displays a text area and an on-screen keyboard.
 * @author Esther Lozano
 */
public class PrincipalWindowPanel extends JPanel {

    private JTextArea textBox;
    private JButton selectedButton1 = null;
    private JButton selectedButton2 = null;
    private int x1 = -1, y1 = -1, x2 = -1, y2 = -1;

    /**
     * Constructs a PrincipalWindowPanel with a text area.
     */
    public PrincipalWindowPanel() {
        setLayout(new GridLayout(1, 1));

        // Text
        textBox = new JTextArea();
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        JScrollPane scrollText = new JScrollPane(textBox);
        scrollText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollText.setPreferredSize(textBox.getPreferredSize());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollText);
    }

    /**
     * Constructs a PrincipalWindowPanel with a text area and an on-screen keyboard.
     *
     * @param keyboard The on-screen keyboard layout as a 2D ArrayList of Strings.
     * @param size     The size of the keyboard buttons.
     */
    public PrincipalWindowPanel(ArrayList<ArrayList<String>> keyboard, float size) {
        setLayout(new GridLayout(2, 1));

        // Text
        textBox = new JTextArea();
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        JScrollPane scrollText = new JScrollPane(textBox);
        scrollText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollText.setPreferredSize(textBox.getPreferredSize());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollText);

        // Keyboard
        ActionListener buttonKey = e -> {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();
            String currentText = textBox.getText();
            textBox.setText(currentText + buttonText);
        };

        JPanel layout = new JPanel();
        int rows = keyboard.size();
        int cols = keyboard.get(0).size();
        layout.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        int fontSize = 14;
        if ((int) size == 40) fontSize = 10;
        else if ((int) size == 50) fontSize = 14;
        else if ((int) size == 60) fontSize = 16;

        Font fontMetrics = new Font("Arial", Font.PLAIN, fontSize);
        Dimension buttonSize = new Dimension((int) size, (int) size);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final String keyText = keyboard.get(i).get(j);
                gbc.gridx = j;
                gbc.gridy = i;

                JButton button = new JButton(keyText) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        if (keyText.equals(" ")) {
                            Graphics2D g2 = (Graphics2D) g.create();
                            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                            GradientPaint gp = new GradientPaint(0, 0, Color.LIGHT_GRAY, 0, getHeight(), Color.GRAY);
                            g2.setPaint(gp);
                            g2.fillRect(0, 0, getWidth(), getHeight());
                            g2.dispose();
                        }
                        super.paintComponent(g);
                    }
                };

                button.setFont(fontMetrics);
                button.setMaximumSize(buttonSize);
                button.setPreferredSize(buttonSize);
                button.addActionListener(buttonKey);

                if (keyText.equals(" ")) {
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(true);
                }

                layout.add(button, gbc);
            }
        }
        add(layout);
    }

    /**
     * Constructs a PrincipalWindowPanel with a text area, an on-screen keyboard, and editing features for the keyboard.
     *
     * @param keyboard    The on-screen keyboard layout as a 2D ArrayList of Strings.
     * @param size        The size of the keyboard buttons.
     * @param windowEdit  Specifies if the window is for editing.
     */
    public PrincipalWindowPanel(ArrayList<ArrayList<String>> keyboard, float size, boolean windowEdit) {
        setLayout(new GridLayout(2, 1));

        // TEXT PANEL
        textBox = new JTextArea();
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        JScrollPane scrollText = new JScrollPane(textBox);
        scrollText.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollText.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollText.setPreferredSize(textBox.getPreferredSize());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollText);

        // Edit mode of Keyboard
        ActionListener buttonKey = e -> {
            JButton pressedButton = (JButton) e.getSource();
            updateButtonSelection(pressedButton);
        };

        int rows = keyboard.size();
        int cols = keyboard.get(0).size();
        JPanel layout = new JPanel();
        layout.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        int fontSize = 14;
        if ((int) size == 40) fontSize = 10;
        else if ((int) size == 50) fontSize = 14;
        else if ((int) size == 60) fontSize = 16;

        Font fontMetrics = new Font("Arial", Font.PLAIN, fontSize);
        Dimension buttonSize = new Dimension((int) size, (int) size);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final String keyText = keyboard.get(i).get(j);
                gbc.gridx = j;
                gbc.gridy = i;
                JButton button = new JButton(keyText) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        if (keyText.equals(" ")) {
                            if (getBackground().equals(Color.RED)) {
                                super.paintComponent(g);
                            } else {
                                Graphics2D g2 = (Graphics2D) g.create();
                                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                                GradientPaint gp = new GradientPaint(0, 0, Color.LIGHT_GRAY, 0, getHeight(), Color.GRAY);
                                g2.setPaint(gp);
                                g2.fillRect(0, 0, getWidth(), getHeight());
                                g2.dispose();
                            }
                        } else {
                            super.paintComponent(g);
                        }
                    }
                };
                button.setActionCommand(i + "," + j);
                button.setFont(fontMetrics);
                button.setMaximumSize(buttonSize);
                button.setPreferredSize(buttonSize);
                button.addActionListener(buttonKey);

                if (keyText.equals(" ")) {
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(true);
                }

                layout.add(button, gbc);
            }
        }
        add(layout);
    }

    // Method to update button selection
    private void updateButtonSelection(JButton pressedButton) {
        String[] cords = pressedButton.getActionCommand().split(",");
        int newX = Integer.parseInt(cords[0]);
        int newY = Integer.parseInt(cords[1]);

        if (selectedButton1 == pressedButton) {
            resetButton(pressedButton);
            selectedButton1 = selectedButton2;
            x1 = x2;
            y1 = y2;
            selectedButton2 = null;
            x2 = -1;
            y2 = -1;
            return;
        } else if (selectedButton2 == pressedButton) {
            resetButton(pressedButton);
            selectedButton2 = null;
            x2 = -1;
            y2 = -1;
            return;
        }

        if (selectedButton1 != null && selectedButton2 != null) {
            resetButton(selectedButton1);
        }

        selectedButton1 = (selectedButton2 != null) ? selectedButton2 : selectedButton1;
        selectedButton2 = pressedButton;
        x1 = (selectedButton2 != null) ? x2 : x1;
        y1 = (selectedButton2 != null) ? y2 : y1;
        x2 = newX;
        y2 = newY;

        setColor(pressedButton);
    }

    private void resetButton(JButton button) {
        if (button.getText().equals(" ")) {
            button.setBackground(null);
            button.setContentAreaFilled(false);
            button.setOpaque(false);
        } else {
            button.setBackground(null);
        }
        button.repaint();
    }

    private void setColor(JButton button) {
        if (button.getText().equals(" ")) {
            button.setContentAreaFilled(true);
            button.setOpaque(true);
        }
        button.setBackground(Color.RED);
    }

    /**
     * Get the text from the text box.
     *
     * @return The text from the text box.
     */
    public String getTextBox() {
        return textBox.getText();
    }

    /**
     * Set the text in the text box.
     *
     * @param text The text to set in the text box.
     */
    public void setTextBox(String text) {
        this.textBox.setText(text);
    }

    /**
     * Get the X1 coordinate.
     *
     * @return The X1 coordinate.
     */
    public int getX1() {
        return x1;
    }

    /**
     * Get the Y1 coordinate.
     *
     * @return The Y1 coordinate.
     */
    public int getY1() {
        return y1;
    }

    /**
     * Get the X2 coordinate.
     *
     * @return The X2 coordinate.
     */
    public int getX2() {
        return x2;
    }

    /**
     * Get the Y2 coordinate.
     *
     * @return The Y2 coordinate.
     */
    public int getY2() {
        return y2;
    }
}

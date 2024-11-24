// InputPanel.java
package main.gui;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    public InputPanel() {
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Subject Name:"));
        add(new JTextField(15));

        add(new JLabel("Teacher Name:"));
        add(new JTextField(15));

        add(new JLabel("Classroom:"));
        add(new JTextField(15));

        add(new JLabel("Time Slot:"));
        add(new JTextField(15));

        add(new JLabel("Constraints:"));
        add(new JTextField(15));

        JButton addButton = new JButton("Add Entry");
        add(addButton);
        JButton clearButton = new JButton("Clear All");
        add(clearButton);
    }
}


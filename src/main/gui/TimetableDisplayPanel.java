// TimetableDisplayPanel.java
package main.gui;

import javax.swing.*;
import java.awt.*;

public class TimetableDisplayPanel extends JPanel {
    private JTable timetableTable;

    public TimetableDisplayPanel() {
        setLayout(new BorderLayout());
        String[] columns = {"Time Slot", "Subject", "Teacher", "Classroom"};
        String[][] data = {}; // Placeholder for timetable data
        timetableTable = new JTable(data, columns);
        add(new JScrollPane(timetableTable), BorderLayout.CENTER);
    }

    public void updateTimetable(String[][] timetableData) {
        String[] columns = {"Time Slot", "Subject", "Teacher", "Classroom"};
        timetableTable.setModel(new javax.swing.table.DefaultTableModel(timetableData, columns));
    }
}


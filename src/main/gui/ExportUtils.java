// ExportUtils.java
package main.gui;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportUtils {
    public static void exportToCSV(String[][] timetableData, String[] columns) {
        JFileChooser fileChooser = new JFileChooser();
        int choice = fileChooser.showSaveDialog(null);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                // Write column headers
                for (String column : columns) {
                    writer.append(column).append(",");
                }
                writer.append("\n");

                // Write data rows
                for (String[] row : timetableData) {
                    for (String cell : row) {
                        writer.append(cell).append(",");
                    }
                    writer.append("\n");
                }

                JOptionPane.showMessageDialog(null, "Timetable exported successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error exporting timetable: " + e.getMessage());
            }
        }
    }
}


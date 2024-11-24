package main.gui;

import main.core.TimetableGenerator;
import main.core.models.Subject;
import main.core.models.Teacher;
import main.core.models.Classroom;
import main.core.models.TimeSlot;
import main.core.models.Timetable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {

    private JTextField subjectField, teacherField, classroomField;
    private JComboBox<String> dayComboBox, dateComboBox, timeSlotComboBox;
    private List<Subject> subjects;
    private List<Teacher> teachers;
    private List<Classroom> classrooms;
    private List<TimeSlot> timeSlots;
    private List<String> availableTimeSlots;

    private JTable timetableTable;
    private DefaultTableModel tableModel;

    public MainFrame() {
        setTitle("Timetable Generator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the lists
        subjects = new ArrayList<>();
        teachers = new ArrayList<>();
        classrooms = new ArrayList<>();
        timeSlots = new ArrayList<>();
        availableTimeSlots = new ArrayList<>();

        // Initialize available time slots
        String[] timeRanges = {
                "08:30 AM - 09:20 AM",
                "09:20 AM - 10:10 AM",
                "10:30 AM - 11:20 AM",
                "11:20 AM - 12:10 PM",
                "01:30 PM - 02:20 PM"
        };
        for (String timeRange : timeRanges) {
            availableTimeSlots.add(timeRange); // Add all time slots to the available list initially
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Stack components vertically

        // Subject Name
        JPanel subjectPanel = new JPanel();
        subjectPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        subjectPanel.add(new JLabel("Subject:"));
        subjectField = new JTextField(20);
        subjectPanel.add(subjectField);
        panel.add(subjectPanel);

        // Teacher Name
        JPanel teacherPanel = new JPanel();
        teacherPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        teacherPanel.add(new JLabel("Teacher:"));
        teacherField = new JTextField(20);
        teacherPanel.add(teacherField);
        panel.add(teacherPanel);

        // Classroom
        JPanel classroomPanel = new JPanel();
        classroomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        classroomPanel.add(new JLabel("Classroom:"));
        classroomField = new JTextField(20);
        classroomPanel.add(classroomField);
        panel.add(classroomPanel);

        // Select Date
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("Select Date:"));
        String[] dates = {"2024-11-01", "2024-11-02", "2024-11-03", "2024-11-04", "2024-11-05"};
        dateComboBox = new JComboBox<>(dates);
        datePanel.add(dateComboBox);
        panel.add(datePanel);

        // Select Day
        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        dayPanel.add(new JLabel("Select Day:"));
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        dayComboBox = new JComboBox<>(days);
        dayPanel.add(dayComboBox);
        panel.add(dayPanel);

        // Select Time Slot
        JPanel timeSlotPanel = new JPanel();
        timeSlotPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        timeSlotPanel.add(new JLabel("Select Time Slot:"));
        timeSlotComboBox = new JComboBox<>(timeRanges);
        timeSlotPanel.add(timeSlotComboBox);
        panel.add(timeSlotPanel);

        // Add Data Button
        JButton addButton = new JButton("Add Data");
        panel.add(addButton);

        // Table to display timetable
        String[] columnNames = {"Subject", "Teacher", "Classroom", "Date", "Day", "Time Slot"};
        tableModel = new DefaultTableModel(columnNames, 0);
        timetableTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(timetableTable);
        tableScrollPane.setPreferredSize(new Dimension(750, 300));
        panel.add(tableScrollPane);

        // Modify and Remove buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton modifyButton = new JButton("Modify");
        JButton removeButton = new JButton("Remove");
        buttonPanel.add(modifyButton);
        buttonPanel.add(removeButton);
        panel.add(buttonPanel);

        // Set container layout to BorderLayout for proper organization
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panel, BorderLayout.NORTH);

        // Add Button ActionListener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectName = subjectField.getText();
                String teacherName = teacherField.getText();
                String classroomName = classroomField.getText();
                String selectedDay = (String) dayComboBox.getSelectedItem();
                String selectedDate = (String) dateComboBox.getSelectedItem();
                String selectedTimeSlot = (String) timeSlotComboBox.getSelectedItem();

                // Check if the time slot is available
                if (availableTimeSlots.contains(selectedTimeSlot)) {
                    // If available, book it
                    availableTimeSlots.remove(selectedTimeSlot);

                    // Add the subject, teacher, classroom, time slot, and date details to respective lists
                    subjects.add(new Subject(subjectName));
                    teachers.add(new Teacher(teacherName, "T" + (teachers.size() + 1)));
                    classrooms.add(new Classroom(classroomName));
                    timeSlots.add(new TimeSlot(selectedDay, selectedTimeSlot));

                    // Add data to the table
                    tableModel.addRow(new Object[]{subjectName, teacherName, classroomName, selectedDate, selectedDay, selectedTimeSlot});

                    // Sort the table based on the time slot
                    sortTableByTimeSlot();
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "This time slot has already been booked. Please select a different one.",
                            "Time Slot Booked", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Modify Button ActionListener
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = timetableTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get current row data
                    String subjectName = (String) tableModel.getValueAt(selectedRow, 0);
                    String teacherName = (String) tableModel.getValueAt(selectedRow, 1);
                    String classroomName = (String) tableModel.getValueAt(selectedRow, 2);
                    String selectedDate = (String) tableModel.getValueAt(selectedRow, 3);
                    String selectedDay = (String) tableModel.getValueAt(selectedRow, 4);
                    String selectedTimeSlot = (String) tableModel.getValueAt(selectedRow, 5);

                    // Prompt user for new values
                    String newSubjectName = JOptionPane.showInputDialog("Enter new subject name:", subjectName);
                    String newTeacherName = JOptionPane.showInputDialog("Enter new teacher name:", teacherName);
                    String newClassroomName = JOptionPane.showInputDialog("Enter new classroom:", classroomName);
                    String newDay = (String) JOptionPane.showInputDialog(MainFrame.this, "Select Day:", "Modify Day", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}, selectedDay);
                    String newTimeSlot = (String) JOptionPane.showInputDialog(MainFrame.this, "Select Time Slot:", "Modify Time Slot", JOptionPane.QUESTION_MESSAGE, null, new String[]{"08:30 AM - 09:20 AM", "09:20 AM - 10:10 AM", "10:30 AM - 11:20 AM", "11:20 AM - 12:10 PM", "01:30 PM - 02:20 PM"}, selectedTimeSlot);

                    // Update the row
                    tableModel.setValueAt(newSubjectName, selectedRow, 0);
                    tableModel.setValueAt(newTeacherName, selectedRow, 1);
                    tableModel.setValueAt(newClassroomName, selectedRow, 2);
                    tableModel.setValueAt(selectedDate, selectedRow, 3);
                    tableModel.setValueAt(newDay, selectedRow, 4);
                    tableModel.setValueAt(newTimeSlot, selectedRow, 5);

                    // Sort the table based on the time slot
                    sortTableByTimeSlot();
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Please select a row to modify.");
                }
            }
        });

        // Remove Button ActionListener
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = timetableTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the time slot of the selected row to add back to available time slots
                    String removedTimeSlot = (String) tableModel.getValueAt(selectedRow, 5);

                    // Add the removed time slot back to the available time slots
                    availableTimeSlots.add(removedTimeSlot);

                    // Remove selected row
                    tableModel.removeRow(selectedRow);

                    // Sort the table based on the time slot
                    sortTableByTimeSlot();
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "Please select a row to remove.");
                }
            }
        });

        // Make the frame visible after all components are added
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    // Sort table rows by time slot
    private void sortTableByTimeSlot() {
        List<RowData> rowDataList = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            rowDataList.add(new RowData(
                    (String) tableModel.getValueAt(i, 0),
                    (String) tableModel.getValueAt(i, 1),
                    (String) tableModel.getValueAt(i, 2),
                    (String) tableModel.getValueAt(i, 3),
                    (String) tableModel.getValueAt(i, 4),
                    (String) tableModel.getValueAt(i, 5)
            ));
        }

        // Sort by time slot
        rowDataList.sort(Comparator.comparing(RowData::getTimeSlot));

        // Clear table and repopulate with sorted rows
        tableModel.setRowCount(0);
        for (RowData row : rowDataList) {
            tableModel.addRow(new Object[]{row.subject, row.teacher, row.classroom, row.date, row.day, row.timeSlot});
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    // Helper class to store row data for sorting
    private static class RowData {
        String subject, teacher, classroom, date, day, timeSlot;

        RowData(String subject, String teacher, String classroom, String date, String day, String timeSlot) {
            this.subject = subject;
            this.teacher = teacher;
            this.classroom = classroom;
            this.date = date;
            this.day = day;
            this.timeSlot = timeSlot;
        }

        public String getTimeSlot() {
            return timeSlot;
        }
    }
}

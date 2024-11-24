package main.core;

import main.core.models.Subject;
import main.core.models.Teacher;
import main.core.models.Classroom;
import main.core.models.TimeSlot;
import main.core.models.Timetable;

import java.util.List;
import java.util.ArrayList;

public class TimetableGenerator {

    private List<Subject> subjects;
    private List<Teacher> teachers;
    private List<Classroom> classrooms;
    private List<TimeSlot> timeSlots;
    private Timetable timetable;

    // Constructor to initialize lists
    public TimetableGenerator(List<Subject> subjects, List<Teacher> teachers, List<Classroom> classrooms, List<TimeSlot> timeSlots) {
        this.subjects = subjects;
        this.teachers = teachers;
        this.classrooms = classrooms;
        this.timeSlots = timeSlots;
        this.timetable = new Timetable();
    }

    // Method to generate the timetable
    public Timetable generateTimetable(List<Subject> subjects, List<Teacher> teachers, List<Classroom> classrooms, List<TimeSlot> timeSlots) {
        // List to track which time slots are already assigned
        List<TimeSlot> assignedTimeSlots = new ArrayList<>();

        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot timeSlot = timeSlots.get(i);

            // Check if the time slot is already assigned
            if (assignedTimeSlots.contains(timeSlot)) {
                System.out.println("Conflict: Time slot " + timeSlot.getDay() + " " + timeSlot.getTimeRange() + " is already assigned.");
                continue; // Skip this time slot if it's already assigned
            }

            // Assign the subject, teacher, and classroom to this time slot
            Subject subject = subjects.get(i);
            Teacher teacher = teachers.get(i);
            Classroom classroom = classrooms.get(i);

            // Add this time slot to the assigned list to prevent re-assignment
            assignedTimeSlots.add(timeSlot);

            // Add the class to the timetable
            timetable.addClass(subject, teacher, classroom, timeSlot);
        }

        return timetable;
    }
}

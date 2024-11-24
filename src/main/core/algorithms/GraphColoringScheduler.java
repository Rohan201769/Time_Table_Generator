// GraphColoringScheduler.java
package main.core.algorithms;

import main.core.models.*;

import java.util.*;

public class GraphColoringScheduler {
    public Timetable schedule(List<Subject> subjects, List<Teacher> teachers, List<Classroom> classrooms, List<TimeSlot> timeSlots) {
        Timetable timetable = new Timetable();

        Map<Subject, List<String>> conflicts = buildConflictGraph(subjects, teachers, classrooms, timeSlots);
        Map<Subject, String> schedule = colorGraph(conflicts, timeSlots);

        for (Map.Entry<Subject, String> entry : schedule.entrySet()) {
            Subject subject = entry.getKey();
            String timeSlot = entry.getValue();
            String teacherName = subject.getTeacherName();
            timetable.addEntry(timeSlot, subject.getName() + "-" + teacherName);
        }

        return timetable;
    }

    private Map<Subject, List<String>> buildConflictGraph(List<Subject> subjects, List<Teacher> teachers, List<Classroom> classrooms, List<TimeSlot> timeSlots) {
        // Example logic for building conflict graph
        Map<Subject, List<String>> conflictGraph = new HashMap<>();
        for (Subject subject : subjects) {
            conflictGraph.put(subject, new ArrayList<>()); // Populate with actual conflicts
        }
        return conflictGraph;
    }

    private Map<Subject, String> colorGraph(Map<Subject, List<String>> conflicts, List<TimeSlot> timeSlots) {
        Map<Subject, String> coloring = new HashMap<>();
        for (Subject subject : conflicts.keySet()) {
            for (TimeSlot slot : timeSlots) {
                if (canAssignSlot(subject, slot, coloring)) {
                    coloring.put(subject, slot.getSlot());
                    break;
                }
            }
        }
        return coloring;
    }

    private boolean canAssignSlot(Subject subject, TimeSlot slot, Map<Subject, String> coloring) {
        for (Map.Entry<Subject, String> entry : coloring.entrySet()) {
            if (entry.getValue().equals(slot.getSlot())) {
                return false;
            }
        }
        return true;
    }
}


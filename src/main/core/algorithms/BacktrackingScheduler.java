// BacktrackingScheduler.java
package main.core.algorithms;

import main.core.models.*;

import java.util.List;
import java.util.Map;

public class BacktrackingScheduler {
    public boolean schedule(
            List<Subject> subjects,
            List<Teacher> teachers,
            List<Classroom> classrooms,
            List<TimeSlot> timeSlots,
            Timetable timetable
    ) {
        // Recursive backtracking to generate timetable
        return backtrack(0, subjects, teachers, classrooms, timeSlots, timetable);
    }

    private boolean backtrack(
            int index,
            List<Subject> subjects,
            List<Teacher> teachers,
            List<Classroom> classrooms,
            List<TimeSlot> timeSlots,
            Timetable timetable
    ) {
        if (index >= subjects.size()) return true;

        Subject subject = subjects.get(index);

        for (TimeSlot timeSlot : timeSlots) {
            for (Classroom classroom : classrooms) {
                Teacher teacher = findTeacher(teachers, subject.getTeacherName());
                if (teacher != null && teacher.isAvailable(timeSlot.getSlot())) {
                    String entry = subject.getName() + "-" + teacher.getName() + "-" + classroom.getRoomNumber();
                    timetable.addEntry(timeSlot.getSlot(), entry);

                    if (backtrack(index + 1, subjects, teachers, classrooms, timeSlots, timetable)) {
                        return true;
                    }

                    timetable.addEntry(timeSlot.getSlot(), null);
                }
            }
        }

        return false;
    }

    private Teacher findTeacher(List<Teacher> teachers, String teacherName) {
        for (Teacher teacher : teachers) {
            if (teacher.getName().equals(teacherName)) {
                return teacher;
            }
        }
        return null;
    }
}


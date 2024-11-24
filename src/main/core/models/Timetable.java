package main.core.models;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<ClassDetails> classes = new ArrayList<>();

    // Define the ClassDetails inner class to store class data
    public class ClassDetails {
        private Subject subject;
        private Teacher teacher;
        private Classroom classroom;
        private TimeSlot timeSlot;

        public ClassDetails(Subject subject, Teacher teacher, Classroom classroom, TimeSlot timeSlot) {
            this.subject = subject;
            this.teacher = teacher;
            this.classroom = classroom;
            this.timeSlot = timeSlot;
        }

        // Getters for each of the attributes
        public Subject getSubject() {
            return subject;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public Classroom getClassroom() {
            return classroom;
        }

        public TimeSlot getTimeSlot() {
            return timeSlot;
        }
    }

    // Method to add a class to the timetable
    public void addClass(Subject subject, Teacher teacher, Classroom classroom, TimeSlot timeSlot) {
        classes.add(new ClassDetails(subject, teacher, classroom, timeSlot));
    }

    // Getter for classes
    public List<ClassDetails> getClasses() {
        return classes;
    }
}

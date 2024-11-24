// ConstraintHandler.java

package main.core;

import main.core.models.Teacher;

import java.util.List;

public class ConstraintHandler {
    public boolean checkTeacherAvailability(Teacher teacher, String timeSlot) {
        return teacher.isAvailable(timeSlot);
    }

    public boolean checkClassroomAvailability(String classroom, List<String> occupiedRooms, String timeSlot) {
        String roomSlot = classroom + "-" + timeSlot;
        return !occupiedRooms.contains(roomSlot);
    }
}


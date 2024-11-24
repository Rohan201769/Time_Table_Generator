package main.core.models;

public class TimeSlot {
    private String day;
    private String timeRange;

    public TimeSlot(String day, String timeRange) {
        this.day = day;
        this.timeRange = timeRange;
    }

    public String getDay() {
        return day;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public String getDetails() {
        return day + ": " + timeRange;
    }
}

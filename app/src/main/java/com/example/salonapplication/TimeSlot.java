package com.example.salonapplication;

public class TimeSlot {
    private String time;
    private boolean isAvailable;
    private int availableSpots;
    private int duration; // in minutes

    public TimeSlot(String time, boolean isAvailable, int availableSpots, int duration) {
        this.time = time;
        this.isAvailable = isAvailable;
        this.availableSpots = availableSpots;
        this.duration = duration;
    }

    public String getTime() { return time; }
    public boolean isAvailable() { return isAvailable; }
    public int getAvailableSpots() { return availableSpots; }
    public int getDuration() { return duration; }
    public String getFormattedDuration() {
        return duration + " min";
    }
}
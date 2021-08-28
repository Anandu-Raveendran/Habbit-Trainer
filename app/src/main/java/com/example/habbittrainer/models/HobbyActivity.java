package com.example.habbittrainer.models;

import java.sql.Time;
public class HobbyActivity {
    private String name;
    private Time timeNeeded = Time.valueOf("0:01:0");
    private int repetitions = 1;
    private Time breakLength = Time.valueOf("0:01:0");
    private Time breakAfterActivity = Time.valueOf("0:01:0");

    public HobbyActivity(String name, Time timeNeeded, int repetitions, Time breakLength, Time breakAfterActivity) {
        this.name = name;
        this.timeNeeded = timeNeeded;
        this.repetitions = repetitions;
        this.breakLength = breakLength;
        this.breakAfterActivity = breakAfterActivity;
    }

    public HobbyActivity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getTimeNeeded() {
        return timeNeeded;
    }

    public void setTimeNeeded(Time timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public Time getBreakLength() {
        return breakLength;
    }

    public void setBreakLength(Time breakLength) {
        this.breakLength = breakLength;
    }

    public Time getBreakAfterActivity() {
        return breakAfterActivity;
    }

    public void setBreakAfterActivity(Time breakAfterActivity) {
        this.breakAfterActivity = breakAfterActivity;
    }

    @Override
    public String toString() {
        return "HobbyActivity{" +
                "name='" + name + '\'' +
                ", timeNeeded=" + timeNeeded +
                ", repetitions=" + repetitions +
                ", breakLength=" + breakLength +
                ", breakAfterActivity=" + breakAfterActivity +
                '}';
    }
}
package com.example.habbittrainer.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HobbyActivity implements Serializable {
    private String name;
    private LocalTime timeNeeded = LocalTime.of(0, 0);
    private int repetitions = 1;
    private LocalTime breakLength = LocalTime.of(0, 0);
    private LocalTime breakAfterActivity = LocalTime.of(0, 0);

    public HobbyActivity(String name, LocalTime timeNeeded, int repetitions, LocalTime breakLength, LocalTime breakAfterActivity) {
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

    public LocalTime getTimeNeeded() {
        return timeNeeded;
    }

    public void setTimeNeeded(LocalTime timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public LocalTime getBreakLength() {
        return breakLength;
    }

    public void setBreakLength(LocalTime breakLength) {
        this.breakLength = breakLength;
    }

    public LocalTime getBreakAfterActivity() {
        return breakAfterActivity;
    }

    public void setBreakAfterActivity(LocalTime breakAfterActivity) {
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
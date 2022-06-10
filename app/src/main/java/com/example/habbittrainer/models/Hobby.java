package com.example.habbittrainer.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hobby implements Serializable {
    private String name;
    private Time scheduledTime = new Time(System.currentTimeMillis());
    private boolean[] days = new boolean[7];
    private boolean enableReminder = false;
    private List<HobbyActivity> hobbyActivities;
    private int scoreStreak;

    public Hobby(String name, Time scheduledTime, boolean[] days, boolean enableReminder, int scoreStreak) {
        this.name = name;
        this.scheduledTime = scheduledTime;
        this.days = days;
        this.enableReminder = enableReminder;
        this.hobbyActivities = new ArrayList<>();
        this.scoreStreak = scoreStreak;
    }

    public Hobby(String name) {
        this.name = name;
    }

    public Hobby() {
        this.name = null;
        this.hobbyActivities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Time scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public  boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public boolean isEnableReminder() {
        return enableReminder;
    }

    public void setEnableReminder(boolean enableReminder) {
        this.enableReminder = enableReminder;
    }

    public List<HobbyActivity> getHobbyActivities() {
        return hobbyActivities;
    }

    public void setHobbyActivities(List<HobbyActivity> hobbyActivities) {
        this.hobbyActivities = hobbyActivities;
    }

    public int getScoreStreak() {
        return scoreStreak;
    }

    public void setScoreStreak(int scoreStreak) {
        this.scoreStreak = scoreStreak;
    }

    @Override
    public String toString() {
        return "Hobby{" +
                "name='" + name + '\'' +
                ", scheduledTime=" + scheduledTime +
                ", days=" + Arrays.toString(days) +
                ", enableReminder=" + enableReminder +
                ", hobbyActivities=" + hobbyActivities +
                ", scoreStreak=" + scoreStreak +
                '}';
    }
}

package com.example.habbittrainer.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Hobby implements Serializable {
    private String name;
    private Time scheduledTime = new Time(System.currentTimeMillis());
    private int days = Days.NONE.getIntValue();
    private boolean enableReminder = false;
    private List<HobbyActivity> hobbyActivities;

    public Hobby(String name, Time scheduledTime, int days, boolean enableReminder) {
        this.name = name;
        this.scheduledTime = scheduledTime;
        this.days = days;
        this.enableReminder = enableReminder;
        this.hobbyActivities = new ArrayList<>();
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

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
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

    @Override
    public String toString() {
        return "Hobby{" +
                "name='" + name + '\'' +
                ", scheduledTime=" + scheduledTime +
                ", days=" + days +
                ", enableReminder=" + enableReminder +
                ", hobbyActivities=" + hobbyActivities +
                '}';
    }
}

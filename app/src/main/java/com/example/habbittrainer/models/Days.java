package com.example.habbittrainer.models;

public enum Days {
    SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6);

    private int days = 0;

    Days(int days) {
        this.days = days;
    }

    public int getIntValue() {
        return this.days;
    }
}

package com.example.habbittrainer.models;

public enum Days {
    NONE(0), SUNDAY(1), MONDAY(2), TUESDAY(4), WEDNESDAY(8), THURSDAY(16), FRIDAY(32), SATURDAY(64);

    private int days = 0;

    Days(int i) {
        this.days = i;
    }

    public int getIntValue() {
        return this.days;
    }
}

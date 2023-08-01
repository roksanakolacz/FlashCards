package com.example.FlashCards.model;

import java.sql.Time;

public enum Subject {
    FOOD("Food"),
    FAMILY("Family"),
    HEALTH("Health"),
    TIME_AND_DATE("Time and date"),
    WORK("Work"),
    HOUSE("House"),
    TRAVELS("Travels"),
    SHOPPING("Shopping"),
    EDUCATION("Education"),
    DAILY_ACTIVITIES("Daily activities"),
    NATURE("Nature"),
    SPORT("Sport"),
    CULTURE("Culture"),
    TECHNOLOGY("Technology"),
    SOCIAL_RELATIONS("Social relations"),
    OTHER("Other");


    private final String displayedName;

    Subject(String displayedName) {
        this.displayedName = displayedName;
    }

    public String getDisplayName() {
        return displayedName;
    }

}
package com.example.critter.enums;

public enum CritterMood {
    HAPPY("Happy"),
    ANGRY("Angry"),
    SAD("Sad");

    private String mood;

    CritterMood(String mood) {
        this.mood = mood;
    }
    public String getMood() {
        return mood;
    }
}

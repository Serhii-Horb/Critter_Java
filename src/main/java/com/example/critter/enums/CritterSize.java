package com.example.critter.enums;

public enum CritterSize {
    SMALL("Smal"),
    MEDIUM("Medium"),
    LARGE("Large");

    private String size;

    CritterSize(String size) {
        this.size = size;
    }
    public String getSize() {
        return size;
    }
}

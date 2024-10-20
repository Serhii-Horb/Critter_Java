package com.example.critter.enums;

public enum CritterType {
    DRAGON("Dragon"),
    PHOENIX("Phoenix"),
    GRIFFIN("Griffin"),
    UNICORN("Unicorn");

    private String type;

    CritterType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

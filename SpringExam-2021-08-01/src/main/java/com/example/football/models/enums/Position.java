package com.example.football.models.enums;

public enum Position {
    ATT("ATT"),
    MID("MID"),
    DEF("DEF");

    private final String position;

    Position(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}

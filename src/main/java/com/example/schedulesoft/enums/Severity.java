package com.example.schedulesoft.enums;

import javafx.scene.paint.Color;

public enum Severity {
    SUCCESS(Color.GREEN),
    ERROR(Color.RED),
    WARNING(Color.ORANGE),
    INFO(Color.BLUE);

    private final Color color;

    Severity(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
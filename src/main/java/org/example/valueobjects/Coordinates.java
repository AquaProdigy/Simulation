package org.example.valueobjects;

public record Coordinates(int y, int x) {
    public Coordinates {
        if (y < 0 || x < 0) {
            throw new IllegalArgumentException("Coordinates coordinates cannot be negative");
        }
    }
}

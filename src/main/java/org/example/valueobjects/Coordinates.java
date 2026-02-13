package org.example.valueobjects;

public record Coordinates(int x, int y) {
    public Coordinates {
        if (y < 0 || x < 0) {
            throw new IllegalArgumentException("Coordinates coordinates cannot be negative");
        }
    }
}

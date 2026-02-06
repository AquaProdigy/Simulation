package org.example.valueobjects;

public record Speed(int value) {
    public Speed {
        if (value <= 0) {
            throw new IllegalArgumentException("Speed cannot be negative");
        }
    }
}

package org.example.valueobjects;

public record Health(int value) {
    public Health {
        if (value < 0) {
            throw new IllegalArgumentException("Health value cannot be negative");
        }
    }

    public boolean isAlive() {
        return value > 0;
    }

}

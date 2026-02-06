package org.example.valueobjects;

public record AttackPower(int value) {
    public AttackPower {
        if  (value <= 0) {
            throw new IllegalArgumentException("Attack power value must be positive");
        }
    }
}

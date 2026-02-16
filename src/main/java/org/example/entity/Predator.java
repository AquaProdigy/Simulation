package org.example.entity;

import org.example.map.GameMap;
import org.example.valueobjects.AttackPower;
import org.example.valueobjects.Coordinates;
import org.example.valueobjects.Health;
import org.example.valueobjects.Speed;

import java.util.Optional;

public class Predator extends Creature {
    private final AttackPower attackPower;

    public Predator(Health health, Speed speed, AttackPower attackPower) {
        super(health, speed);
        this.attackPower = attackPower;
    }

    @Override
    public void attack(GameMap gameMap, Coordinates targetCoordinates) {
        Optional<Entity> targetEntity = gameMap.getEntity(targetCoordinates);
        if (targetEntity.isEmpty()) {
            return;
        }
        if (!this.getTargetClass().equals(targetEntity.get().getClass())) {
            return;
        }

        Herbivore target = (Herbivore) targetEntity.get();

        target.takeDamage(this.attackPower.value());

        if (target.isDead()) {
            gameMap.removeEntity(targetCoordinates);
        }
    }

    @Override
    public Class<? extends Entity> getTargetClass() {
        return Herbivore.class;
    }
}

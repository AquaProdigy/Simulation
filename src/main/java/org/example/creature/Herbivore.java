package org.example.creature;

import org.example.entity.Entity;
import org.example.entity.Grass;
import org.example.map.GameMap;
import org.example.pathfinder.PathFinder;
import org.example.valueobjects.AttackPower;
import org.example.valueobjects.Coordinates;
import org.example.valueobjects.Health;
import org.example.valueobjects.Speed;

import java.util.List;
import java.util.Optional;

public class Herbivore extends Creature {
    public Herbivore(Health health, Speed speed) {
        super(health, speed);
    }

    @Override
    public Class<? extends Entity> getTargetClass() {
        return Grass.class;
    }

    @Override
    public void attack(GameMap gameMap, Coordinates targetCoordinates) {
        Optional<Entity> targetEntity = gameMap.getEntity(targetCoordinates);
        if (targetEntity.isEmpty()) return;
        if (!this.getTargetClass().equals(targetEntity.get().getClass())) return;
        gameMap.removeEntity(targetCoordinates);
    }
}

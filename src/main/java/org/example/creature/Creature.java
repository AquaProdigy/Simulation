package org.example.creature;

import org.example.entity.Entity;
import org.example.entity.Grass;
import org.example.map.GameMap;
import org.example.pathfinder.PathFinder;
import org.example.valueobjects.Coordinates;
import org.example.valueobjects.Health;
import org.example.valueobjects.Speed;

import java.util.List;
import java.util.Optional;

abstract public class Creature extends Entity {
    protected Health health;
    protected Speed speed;

    public Creature(Health health, Speed speed) {
        this.health = health;
        this.speed = speed;
    }

    public abstract void makeMove(GameMap gameMap, PathFinder pathFinder);

    public void takeDamage(int damage) {
        int newHealthValue = Math.max(0, health.value() - damage);
        this.health = new Health(newHealthValue);
    }

    public boolean isDead() {
        return health.value() <= 0;
    }
}

package org.example.entity;

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

    public abstract Class<? extends Entity> getTargetClass();

    public void makeMove(GameMap gameMap, PathFinder pathFinder) {
        Optional<Coordinates> currentPosition = gameMap.getCoordinates(this);
        if (currentPosition.isEmpty()) {
            return;
        }

        List<Coordinates> listCoordinatesToTarget = pathFinder.findPath(
                gameMap,
                currentPosition.get(),
                this.getTargetClass()
        );

        if (listCoordinatesToTarget.isEmpty()) {
            return;
        }

        if (listCoordinatesToTarget.size() == 1) {
            this.attack(gameMap, listCoordinatesToTarget.get(0));

            return;
        }
        moveTowardsTarget(gameMap, listCoordinatesToTarget, currentPosition.get());
    }

    public abstract void attack(GameMap gameMap, Coordinates targetCoordinates);

    public void takeDamage(int damage) {
        int newHealthValue = Math.max(0, health.value() - damage);
        this.health = new Health(newHealthValue);
    }

    public boolean isDead() {
        return health.value() <= 0;
    }

    private void moveTowardsTarget(GameMap gameMap, List<Coordinates> listCoordinatesToTarget, Coordinates currentPosition) {
        int stepsToMove = Math.min(speed.value(), listCoordinatesToTarget.size() - 1);
        Coordinates newPosition = listCoordinatesToTarget.get(stepsToMove - 1);

        if (gameMap.isCellEmpty(newPosition) && gameMap.isCoordinatesInBounds(newPosition)) {
            gameMap.removeEntity(currentPosition);
            gameMap.addEntity(newPosition, this);
        }
    }
}

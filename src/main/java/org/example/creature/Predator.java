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

public class Predator extends Creature {
    private AttackPower attackPower;

    public Predator(Health health, Speed speed, AttackPower attackPower) {
        super(health, speed);
        this.attackPower = attackPower;
    }

    @Override
    public void makeMove(GameMap gameMap, PathFinder pathFinder) {
        Optional<Coordinates> currentPosition = gameMap.getCoordinates(this);
        if (currentPosition.isEmpty()) return;

        List<Coordinates> listCoordinatesToTarget = pathFinder.findPath(
                gameMap,
                currentPosition.get(),
                Herbivore.class
        );

        if (listCoordinatesToTarget.isEmpty()) return;

        if (listCoordinatesToTarget.size() == 1) {
            attack(gameMap, currentPosition.get(), listCoordinatesToTarget.get(0));
            return;
        }
        moveTowardsTarget(gameMap, currentPosition.get(), listCoordinatesToTarget);

    }


    private void moveTowardsTarget(GameMap gameMap, Coordinates currentPos, List<Coordinates> path) {
        int stepsToMove = Math.min(speed.value(), path.size());
        Coordinates newPosition = path.get(stepsToMove - 1);
        gameMap.moveEntity(currentPos, newPosition);
    }

    private void attack(GameMap gameMap, Coordinates attackerPos, Coordinates targetPos) {
        Optional<Entity> entityOpt = gameMap.getEntity(targetPos);

        if (entityOpt.isEmpty() || !(entityOpt.get() instanceof Herbivore)) {
            return;
        }

        Herbivore prey = (Herbivore) entityOpt.get();
        prey.takeDamage(attackPower.value());

        if (prey.isDead()) {
            gameMap.removeEntity(targetPos);
            gameMap.moveEntity(attackerPos, targetPos);
        }
    }


    @Override
    public String getSymbol() {
        return "\uD83D\uDC05";
    }
}

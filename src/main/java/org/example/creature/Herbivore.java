package org.example.creature;

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
    public void makeMove(GameMap gameMap, PathFinder pathFinder) {
        Optional<Coordinates> coordinatesThisEntity = gameMap.getCoordinates(this);
        if (coordinatesThisEntity.isEmpty()) return;

        List<Coordinates> listCoordinatesToTarget = pathFinder.findPath(
                gameMap,
                coordinatesThisEntity.get(),
                Grass.class
        );

        if (listCoordinatesToTarget.isEmpty()) return;

        if (listCoordinatesToTarget.size() == 1) {
            gameMap.removeEntity(listCoordinatesToTarget.get(0));
            gameMap.moveEntity(coordinatesThisEntity.get(), listCoordinatesToTarget.get(0));
            return;
        }

        int stepsToMove = Math.min(speed.value(), listCoordinatesToTarget.size());
        Coordinates newPosition = listCoordinatesToTarget.get(stepsToMove - 1);
        gameMap.moveEntity(coordinatesThisEntity.get(), newPosition);
    }

    @Override
    public String getSymbol() {
        return "\uD83D\uDC04";
    }
}

package org.example.action;

import org.example.entity.Creature;
import org.example.map.GameMap;
import org.example.pathfinder.PathFinder;

import java.util.ArrayList;
import java.util.List;

public class MoveCreatures implements Action {
    private final PathFinder pathFinder;

    public MoveCreatures(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    @Override
    public void execute(GameMap map) {
        List<Creature> creatures = new ArrayList<>(map.getAllCreatures().values());
        for (Creature creature : creatures) {
            if (!creature.isDead()) {
                creature.makeMove(map, pathFinder);
            }
        }
    }
}

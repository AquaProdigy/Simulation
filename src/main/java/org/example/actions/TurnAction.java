package org.example.actions;

import org.example.creature.Creature;
import org.example.creature.Predator;
import org.example.map.GameMap;
import org.example.pathfinder.BFSFinder.BFSFinder;
import org.example.valueobjects.Coordinates;

import java.util.Map;

public class TurnAction implements Actions{
    @Override
    public void execute(GameMap map) {
        Map<Coordinates, Creature> predators = map.getAllCreatures();

        for (Map.Entry<Coordinates, Creature> entry : predators.entrySet()) {
            Creature creature = entry.getValue();
            Coordinates coordinates = entry.getKey();

            creature.makeMove(map, new BFSFinder());
        }
    }
}

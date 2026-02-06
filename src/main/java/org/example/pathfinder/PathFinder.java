package org.example.pathfinder;

import org.example.creature.Creature;
import org.example.entity.Entity;
import org.example.map.GameMap;
import org.example.valueobjects.Coordinates;

import java.util.List;

public interface PathFinder {
    List<Coordinates> findPath(GameMap map, Coordinates from, Class<? extends Entity> target);
}

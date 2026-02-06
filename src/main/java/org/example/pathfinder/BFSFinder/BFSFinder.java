package org.example.pathfinder.BFSFinder;

import org.example.creature.Creature;
import org.example.entity.Entity;
import org.example.map.GameMap;
import org.example.pathfinder.PathFinder;
import org.example.valueobjects.Coordinates;

import java.util.*;

public class BFSFinder implements PathFinder {

    private List<Coordinates> getNeighbours(Coordinates start) {
        return List.of(
                new Coordinates(start.y(), start.x() + 1),
                new Coordinates(start.y(), Math.max(start.x() - 1, 0)),
                new Coordinates(start.y() + 1, start.x()),
                new Coordinates(Math.max(start.y() - 1, 0), start.x())
        );
    }

    @Override
    public List<Coordinates> findPath(
            GameMap map,
            Coordinates from,
            Class<? extends Entity> target
    ) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        HashMap<Coordinates, Coordinates> parents = new HashMap<>();

        queue.add(from);
        visited.add(from);

        Coordinates targetCoordinates = null;

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            Optional<Entity> entity = map.getEntity(current);
            if (entity.isPresent() && target.isInstance(entity.get())) {
                targetCoordinates = current;
                break;
            }

            for (Coordinates neighbour : getNeighbours(current)) {
                if (!map.isCoordinatesInBounds(neighbour)) continue;
                if (visited.contains(neighbour)) continue;

                Optional<Entity> neighbourEntity = map.getEntity(neighbour);
                if (neighbourEntity.isPresent() &&
                        !(neighbourEntity.get() instanceof Creature) &&
                        !target.isInstance(neighbourEntity.get())) {
                    continue;
                }

                visited.add(neighbour);
                queue.add(neighbour);
                parents.put(neighbour, current);

            }
        }
        if (targetCoordinates == null) {
            return List.of();
        }

        return reconstructPath(parents, from, targetCoordinates);
    }


    private List<Coordinates> reconstructPath(
            HashMap<Coordinates, Coordinates> parents,
            Coordinates from,
            Coordinates target
    ) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = target;

        while (current != null && !current.equals(from)) {
            path.add(current);
            current = parents.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}

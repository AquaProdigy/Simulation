package org.example.map;

import org.example.entity.Creature;
import org.example.entity.Entity;
import org.example.valueobjects.Coordinates;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameMap {
    private final int width;
    private final int height;
    private final HashMap<Coordinates, Entity> entities = new HashMap<>();

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public void addEntity(Coordinates coordinates, Entity entity) {
        if (isCoordinatesInBounds(coordinates)) {
            entities.put(coordinates, entity);
        }
    }

    public void removeEntity(Coordinates coordinates) {
        if (isCoordinatesInBounds(coordinates)) {
            entities.remove(coordinates);
        }
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        if (isCoordinatesInBounds(coordinates)) {
            return !entities.containsKey(coordinates);
        }
        throw new IllegalArgumentException("Coordinates out of bounds");
    }

    public Optional<Entity> getEntity(Coordinates coordinates) {
        return Optional.ofNullable(entities.get(coordinates));
    }

    public Optional<Coordinates> getCoordinates(Entity entity) {
        return entities.entrySet().stream()
                .filter(e -> e.getValue().equals(entity))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Map<Coordinates, Creature> getAllCreatures() {
        return Map.copyOf(getEntitiesByType(Creature.class));
    }

    public boolean isCoordinatesInBounds(Coordinates coordinates) {
        return coordinates.y() >= 0 && coordinates.y() < height &&
                coordinates.x() >= 0 && coordinates.x() < width;
    }

    public void clearMap() {
        entities.clear();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private <T extends Entity> Map<Coordinates, T> getEntitiesByType(Class<T> type) {
        return Map.copyOf(entities.entrySet().stream()
                .filter(entry -> type.isInstance(entry.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> type.cast(entry.getValue())
                )));
    }
}

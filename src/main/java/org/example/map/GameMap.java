package org.example.map;

import org.example.creature.Creature;
import org.example.creature.Herbivore;
import org.example.creature.Predator;
import org.example.entity.Entity;
import org.example.valueobjects.Coordinates;
import org.example.valueobjects.Health;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameMap {
    private final int width;
    private final int height;
    private HashMap<Coordinates, Entity> entities = new HashMap<>();

    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public void addEntity(Coordinates coordinates, Entity entity) {
        entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        entities.remove(coordinates);
    }

    public void moveEntity(Coordinates from, Coordinates to) {
        Optional<Entity> entity = getEntity(from);

        if (entity.isEmpty()) {
            throw new IllegalStateException("Entity not found");
        }

        removeEntity(from);
        addEntity(to, entity.get());


    }

    public boolean isCellEmpty(Coordinates coordinates) {
        return !entities.containsKey(coordinates);
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

    public boolean isContainsEntity(Coordinates coordinates) {
        return entities.containsKey(coordinates);
    }

    public Map<Coordinates, Creature> getAllCreatures() {
        return Map.copyOf(getEntitiesByType(Creature.class));
    }

    public boolean isCoordinatesInBounds(Coordinates coordinates) {
        return coordinates.y() >= 0 && coordinates.y() < width &&
                coordinates.x() >= 0 && coordinates.x() < height;
    }

    private <T extends Entity> Map<Coordinates, T> getEntitiesByType(Class<T> type) {
        return Map.copyOf(entities.entrySet().stream()
                .filter(entry -> type.isInstance(entry.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> type.cast(entry.getValue())
                )));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

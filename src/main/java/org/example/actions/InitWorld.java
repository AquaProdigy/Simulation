package org.example.actions;

import org.example.creature.Herbivore;
import org.example.creature.Predator;
import org.example.entity.Grass;
import org.example.entity.Rock;
import org.example.entity.Tree;
import org.example.map.GameMap;
import org.example.valueobjects.AttackPower;
import org.example.valueobjects.Coordinates;
import org.example.valueobjects.Health;
import org.example.valueobjects.Speed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InitWorld implements Actions {
    private final Random random = new Random();

    // Параметры существ
    private static final int PREDATOR_MIN_HEALTH = 80;
    private static final int PREDATOR_MAX_HEALTH = 120;
    private static final int PREDATOR_MIN_SPEED = 1;
    private static final int PREDATOR_MAX_SPEED = 3;
    private static final int PREDATOR_MIN_ATTACK = 15;
    private static final int PREDATOR_MAX_ATTACK = 30;

    private static final int HERBIVORE_MIN_HEALTH = 40;
    private static final int HERBIVORE_MAX_HEALTH = 80;
    private static final int HERBIVORE_MIN_SPEED = 1;
    private static final int HERBIVORE_MAX_SPEED = 2;

    @Override
    public void execute(GameMap map) {
        int width = map.getWidth();
        int height = map.getHeight();
        int totalCells = width * height;

        int occupiedCells = (int) (totalCells * 0.6);

        int predatorCount  = (int) (occupiedCells * 0.05);
        int herbivoreCount = (int) (occupiedCells * 0.15);
        int grassCount     = (int) (occupiedCells * 0.40);
        int rockCount      = (int) (occupiedCells * 0.20);

        int treeCount = occupiedCells
                - predatorCount
                - herbivoreCount
                - grassCount
                - rockCount;

        List<Coordinates> availableCoordinates = generateAllCoordinates(width, height);
        Collections.shuffle(availableCoordinates, random);

        int index = 0;

        for (int i = 0; i < predatorCount; i++)
            map.addEntity(availableCoordinates.get(index++), createRandomPredator());

        for (int i = 0; i < herbivoreCount; i++)
            map.addEntity(availableCoordinates.get(index++), createRandomHerbivore());

        for (int i = 0; i < grassCount; i++)
            map.addEntity(availableCoordinates.get(index++), new Grass());

        for (int i = 0; i < rockCount; i++)
            map.addEntity(availableCoordinates.get(index++), new Rock());

        for (int i = 0; i < treeCount; i++)
            map.addEntity(availableCoordinates.get(index++), new Tree());
    }


    private List<Coordinates> generateAllCoordinates(int width, int height) {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                coordinates.add(new Coordinates(y, x));
            }
        }
        return coordinates;
    }

    private Predator createRandomPredator() {
        Health health = new Health(randomInRange(PREDATOR_MIN_HEALTH, PREDATOR_MAX_HEALTH));
        Speed speed = new Speed(randomInRange(PREDATOR_MIN_SPEED, PREDATOR_MAX_SPEED));
        AttackPower attackPower = new AttackPower(randomInRange(PREDATOR_MIN_ATTACK, PREDATOR_MAX_ATTACK));
        return new Predator(health, speed, attackPower);
    }

    private Herbivore createRandomHerbivore() {
        Health health = new Health(randomInRange(HERBIVORE_MIN_HEALTH, HERBIVORE_MAX_HEALTH));
        Speed speed = new Speed(randomInRange(HERBIVORE_MIN_SPEED, HERBIVORE_MAX_SPEED));
        return new Herbivore(health, speed);
    }

    private int randomInRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
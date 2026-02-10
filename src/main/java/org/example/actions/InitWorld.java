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
    final Integer MAX_HEALTH_HERBIVORE = 80;
    final Integer MIN_HEALTH_HERBIVORE = 20;
    final Integer MAX_SPEED_HERBIVORE = 2;
    final Integer MIN_SPEED_HERBIVORE = 1;
    final Integer MAX_POWER_PREDATOR = 25;
    final Integer MIN_POWER_PREDATOR = 15;

    private final Random random = new Random();

    @Override
    public void execute(GameMap map) {
        int height = map.getHeight();
        int width = map.getWidth();
        int countCellMap = height * width;

        // 50% map is empty
        int countCellForSpawn = countCellMap / 2;
        //count entity in % for each
        int countGrass = (int) countCellForSpawn * 25 / 100;
        int countRock = (int) countCellForSpawn * 25 / 100;
        int countTree = (int) countCellForSpawn * 25 / 100;
        int countHerbivore = (int) countCellForSpawn * 20 / 100;
        int countPredator = (int) countCellForSpawn * 5 / 100;


        spawnGrass(countGrass, map);
        spawnRock(countRock, map);
        spawnHerbivore(countHerbivore, map);
        spawnPredator(countPredator, map);
        spawnTree(countTree, map);

    }

    private Coordinates createRandomCoordinates(GameMap map) {
        while (true) {
            Coordinates coordinates = new Coordinates(
                    random.nextInt(0, map.getHeight()),
                    random.nextInt(0, map.getWidth()));

            if (map.isCellEmpty(coordinates)) {
                return coordinates;
            }
        }
    }

    private void spawnGrass(int count, GameMap map) {
        for (int i = 0; i < count; i++) {
            map.addEntity(createRandomCoordinates(map), new Grass());
        }
    }

    private void spawnRock(int count, GameMap map) {
        for (int i = 0; i < count; i++) {
            map.addEntity(createRandomCoordinates(map), new Rock());
        }
    }

    private void spawnTree(int count, GameMap map) {
        for (int i = 0; i < count; i++) {
            map.addEntity(createRandomCoordinates(map), new Tree());
        }
    }

    private void spawnPredator(int count, GameMap map) {
        for (int i = 0; i < count; i++) {
            map.addEntity(createRandomCoordinates(map), new Predator(
                    new Health(1),
                    new Speed(1),
                    new AttackPower(random.nextInt(MIN_POWER_PREDATOR, MAX_POWER_PREDATOR))
            ));
        }
    }

    private void spawnHerbivore(int count, GameMap map) {
        for (int i = 0; i < count; i++) {
            map.addEntity(createRandomCoordinates(map), new Herbivore(
                    new Health(random.nextInt(MIN_HEALTH_HERBIVORE, MAX_HEALTH_HERBIVORE)),
                    new Speed(random.nextInt(MIN_SPEED_HERBIVORE, MAX_SPEED_HERBIVORE))
            ));
        }
    }


}
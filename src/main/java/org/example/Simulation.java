package org.example;

import org.example.action.InitWorld;
import org.example.action.MoveCreatures;
import org.example.map.GameMap;
import org.example.renderer.Renderer;

public class Simulation {
    private volatile boolean running = false;
    private volatile boolean paused = false;
    private volatile boolean initialized = false;

    private final GameMap gameMap;
    private final Renderer renderer;
    private final InitWorld initWorld;
    private final MoveCreatures moveCreatures;

    private int steps = 0;
    private static final int sleep = 1000;

    public Simulation(GameMap gameMap, Renderer renderer, InitWorld initWorld, MoveCreatures moveCreatures) {
        this.gameMap = gameMap;
        this.renderer = renderer;
        this.initWorld = initWorld;
        this.moveCreatures = moveCreatures;
    }

    public void nextTurn() {
        initializationWorld();
        takeStep();
        moveCreatures.execute(gameMap);
        renderer.render(gameMap);
    }

    public void startSimulation() {
        if (running) {
            System.out.println("Simulation already running");
            return;
        }

        initializationWorld();
        while (running) {
            if (!paused) {
                nextTurn();
                takeStep();
                sleepSimulation();
            }
        }
    }

    public void pauseSimulation() {
        paused = true;
    }

    public void continueSimulation() {
        paused = false;
    }

    public void finishSimulation() {
        running = false;
        paused = false;
        initialized = false;
        steps = 0;

        gameMap.clearMap();
    }

    public int getSteps() {
        return steps;
    }

    private void takeStep() {
        steps++;
    }

    private void initializationWorld() {
        if (!initialized) {
            initWorld.execute(gameMap);
            running = true;
            initialized = true;
        }
    }

    private void sleepSimulation() {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

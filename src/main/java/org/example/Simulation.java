package org.example;

import org.example.action.InitWorld;
import org.example.action.MoveCreatures;
import org.example.map.GameMap;
import org.example.renderer.Renderer;

import java.util.concurrent.atomic.AtomicInteger;

public class Simulation {
    private volatile boolean running = false;
    private volatile boolean paused = false;
    private volatile boolean initialized = false;

    private final GameMap gameMap;
    private final Renderer renderer;
    private final InitWorld initWorld;
    private final MoveCreatures moveCreatures;

    private static final int SLEEP = 1000;

    private final AtomicInteger steps = new AtomicInteger(0);

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
                sleepSimulation();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void pauseSimulation() {
        paused = true;
    }

    public void continueSimulation() {
        paused = false;
    }

    public synchronized void finishSimulation() {
        running = false;
        paused = false;
        initialized = false;
        steps.set(0);

        gameMap.clearMap();
    }

    public int getSteps() {
        return steps.get();
    }

    private void takeStep() {
        steps.addAndGet(1);
    }

    private synchronized void initializationWorld() {
        if (!initialized) {
            initWorld.execute(gameMap);
            running = true;
            initialized = true;
        }
    }

    private void sleepSimulation() {
        try {
            Thread.sleep(SLEEP);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}

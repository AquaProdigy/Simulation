package org.example;

import org.example.actions.Actions;
import org.example.actions.InitWorld;
import org.example.actions.TurnAction;
import org.example.map.GameMap;
import org.example.pathfinder.PathFinder;
import org.example.renderer.Impl.CliRendererImpl;
import org.example.renderer.RendererInterface;

public class Simulation {
    private GameMap gameMap;
    private Integer counter = 0;
    private RendererInterface render;
    private InitWorld initAction;
    private TurnAction turnAction;
    private PathFinder pathFinder;

    public Simulation(GameMap gameMap, RendererInterface render, InitWorld initAction, TurnAction turnAction, PathFinder pathFinder) {
        this.gameMap = gameMap;
        this.render = render;
        this.initAction = initAction;
        this.turnAction = turnAction;
        this.pathFinder = pathFinder;

        initAction.execute(gameMap);
    }

    public void nextTurn() {
        render.render(gameMap);
        turnAction.execute(gameMap);
    }

    public void startSimulation() throws InterruptedException {
        while (true) {
            render.render(gameMap);
            turnAction.execute(gameMap);
            Thread.sleep(2000);
        }
    }

    public void pauseSimulation() {

    }
}

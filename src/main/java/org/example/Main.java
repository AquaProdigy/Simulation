package org.example;

import org.example.actions.InitWorld;
import org.example.actions.TurnAction;
import org.example.map.GameMap;
import org.example.pathfinder.BFSFinder.BFSFinder;
import org.example.pathfinder.PathFinder;
import org.example.renderer.Impl.CliRendererImpl;
import org.example.renderer.RendererInterface;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GameMap map = new GameMap(10, 10);
        RendererInterface render = new CliRendererImpl();
        InitWorld initWorld = new InitWorld();
        PathFinder pathFinder = new BFSFinder();
        TurnAction turnAction = new TurnAction();

        Simulation simulation = new Simulation(map, render, initWorld, turnAction, pathFinder);
        simulation.startSimulation();

    }
}

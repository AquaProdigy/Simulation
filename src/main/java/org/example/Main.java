package org.example;

import org.example.action.InitWorld;
import org.example.action.MoveCreatures;
import org.example.map.GameMap;
import org.example.pathfinder.BfsFinder;
import org.example.pathfinder.PathFinder;
import org.example.renderer.ConsoleRenderer;
import org.example.renderer.Renderer;

public class Main {
    public static void main(String[] args) {
        GameMap map = new GameMap(15, 15);
        Renderer render = new ConsoleRenderer();
        InitWorld initWorld = new InitWorld();
        PathFinder pathFinder = new BfsFinder();
        MoveCreatures moveCreatures = new MoveCreatures(pathFinder);

        Simulation simulation = new Simulation(map, render, initWorld, moveCreatures);
        DialogSimulation dialogSimulation = new DialogSimulation(simulation);

        dialogSimulation.run();
    }
}

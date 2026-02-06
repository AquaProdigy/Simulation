package org.example.renderer.Impl;

import org.example.map.GameMap;
import org.example.renderer.RendererInterface;
import org.example.valueobjects.Coordinates;

public class CliRendererImpl implements RendererInterface {

    @Override
    public void render(GameMap map) {
        clearConsole();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Coordinates coordinates = new Coordinates(y, x);
                if (map.isContainsEntity(coordinates)) {
                    System.out.print(map.getEntity(coordinates).get().getSymbol());
                }else {
                    System.out.print(EMPTY_CELL);
                }
            }
            System.out.println();
        }
    }

    private void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

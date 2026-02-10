package org.example.renderer.Impl;

import org.example.entity.Entity;
import org.example.map.GameMap;
import org.example.renderer.RendererInterface;
import org.example.valueobjects.Coordinates;

import java.util.Optional;

public class CliRendererImpl implements RendererInterface {
    private final String EMPTY_CELL = "⬜";

    @Override
    public void render(GameMap map) {
        clearConsole();

        for (int y = 0; y < map.getHeight(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < map.getWidth(); x++) {

                Coordinates coordinates = new Coordinates(y, x);
                Optional<Entity> entity = map.getEntity(coordinates);

                if (entity.isPresent()) {
                    line.append(selectSpriteEntity(entity.get()));
                } else {
                    line.append(EMPTY_CELL);
                }
            }
            System.out.println(line);
        }
        System.out.println("\n\n\n");
    }


    private String selectSpriteEntity(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Grass" -> "\uD83C\uDF3F";
            case "Rock" -> "\uD83E\uDEA8";
            case "Tree" -> "\uD83C\uDF33";
            case "Herbivore" -> "\uD83D\uDC04";
            case "Predator" -> "\uD83D\uDC05";
            default -> EMPTY_CELL;
        };
    }

    private void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

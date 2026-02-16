package org.example.renderer;

import org.example.entity.*;
import org.example.map.GameMap;
import org.example.valueobjects.Coordinates;

import java.util.Optional;

public class ConsoleRenderer implements Renderer {
    private static final String EMPTY_CELL = "⬜";

    @Override
    public void render(GameMap map) {
        clearConsole();

        for (int y = 0; y < map.getHeight(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < map.getWidth(); x++) {

                Coordinates coordinates = new Coordinates(x, y);
                Optional<Entity> entity = map.getEntity(coordinates);

                if (entity.isPresent()) {
                    line.append(selectSpriteEntity(entity.get()));
                } else {
                    line.append(EMPTY_CELL);
                }
                line.append(" ");
            }
            System.out.println(line);
        }
    }


    private String selectSpriteEntity(Entity entity) {
        return switch (entity) {
            case Grass g -> "\uD83C\uDF3F";
            case Rock r -> "\uD83E\uDEA8";
            case Tree t -> "\uD83C\uDF33";
            case Herbivore h -> "\uD83D\uDC04";
            case Predator p -> "\uD83D\uDC05";
            default -> EMPTY_CELL;
        };
    }

    private void clearConsole(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

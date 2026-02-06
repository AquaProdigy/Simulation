package org.example.renderer;

import org.example.map.GameMap;

public interface RendererInterface {
    final String EMPTY_CELL = "⬜";
    public void render(GameMap map);
}

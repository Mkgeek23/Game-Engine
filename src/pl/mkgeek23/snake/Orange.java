package pl.mkgeek23.snake;

import game.engine.GameEngine;

import java.awt.*;

public class Orange extends GameObject {
    private static final String ORANGE_SIGN = "\uD83C\uDF46";
    public boolean isAlive = true;

    public Orange(int x, int y) {
        super(x, y);
    }

    public void draw(GameEngine game) {
        game.setCellValueEx(x, y, Color.GREEN, ORANGE_SIGN, Color.getHSBColor(.23f,.4f,.45f), 75);
    }
}

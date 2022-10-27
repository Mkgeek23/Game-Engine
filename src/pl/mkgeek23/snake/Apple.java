package pl.mkgeek23.snake;

import game.engine.GameEngine;

import java.awt.*;

public class Apple extends GameObject {
    private static final String APPLE_SIGN = "\uD83C\uDF4E";
    public boolean isAlive = true;

    public Apple(int x, int y) {
        super(x, y);
    }

    public void draw(GameEngine game) {
        game.setCellValueEx(x, y, Color.GREEN, APPLE_SIGN, Color.RED, 75);
    }
}

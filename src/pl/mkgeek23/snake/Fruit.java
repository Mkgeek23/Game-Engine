package pl.mkgeek23.snake;

import game.engine.GameEngine;

import java.awt.*;

public class Fruit extends GameObject {
    private FruitHelper fruitHelper;
    public boolean isAlive = true;

    public Fruit(int x, int y, FruitHelper fruitHelper) {
        super(x, y);
        this.fruitHelper = fruitHelper;
        if (this.fruitHelper == null) {
            this.fruitHelper = new FruitHelper("", Color.GREEN, 0);
        }
    }

    public int getScore() {
        return fruitHelper.getScore();
    }

    public void draw(GameEngine game) {
        game.setCellValueEx(x, y, Color.GREEN, fruitHelper.getFruitSign(), fruitHelper.getColor(), 75);
    }
}

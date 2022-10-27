package pl.mkgeek23.snake;

import game.engine.GameEngine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        super(x, y);
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void setDirection(Direction direction) {
        if (!isAlive) {
            return;
        }
        if ((this.direction == Direction.UP && direction == Direction.DOWN)
                || (this.direction == Direction.DOWN && direction == Direction.UP)
                || (this.direction == Direction.LEFT && direction == Direction.RIGHT)
                || (this.direction == Direction.RIGHT && direction == Direction.LEFT)) {
            return;
        }
        if ((this.direction == Direction.LEFT && snakeParts.get(0).y != snakeParts.get(1).y)
                || (this.direction == Direction.RIGHT && snakeParts.get(0).y != snakeParts.get(1).y)
                || (this.direction == Direction.UP && snakeParts.get(0).x != snakeParts.get(1).x)
                || (this.direction == Direction.DOWN && snakeParts.get(0).x != snakeParts.get(1).x)) {
            this.direction = direction;
        }
    }

    public GameObject createNewHead() {
        int newX = snakeParts.get(0).x;
        int newY = snakeParts.get(0).y;

        /*switch (direction) {
            case UP -> newY--;
            case DOWN -> newY++;
            case LEFT -> newX--;
            case RIGHT -> newX++;
        }*/

        switch (direction) {
            case UP:
                newX--;
                break;
            case DOWN:
                System.out.println("DOWN");
                newX++;
                break;
            case LEFT:
                newY--;
                break;
            case RIGHT:
                newY++;
                break;
        }

        return new GameObject(newX, newY);
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public int getLength() {
        return snakeParts.size();
    }

    public void draw(GameEngine game) {
        Color color = (isAlive) ? Color.BLACK : Color.RED;
        for (GameObject object : snakeParts) {
            if (object.equals(snakeParts.get(0))) {
                game.setCellValueEx(object.x, object.y, Color.GREEN, HEAD_SIGN, color, 75);
            } else {
                game.setCellValueEx(object.x, object.y, Color.GREEN, BODY_SIGN, color, 75);
            }
        }
    }

    public void move(Fruit apple) {
        GameObject snakePart = createNewHead();
        if (snakePart.x < 0 || snakePart.y < 0 || snakePart.x >= 15 || snakePart.y >= 15 || checkCollision(snakePart)) {
            isAlive = false;
        } else {
            snakeParts.add(0, snakePart);
            if (apple.x == snakePart.x && apple.y == snakePart.y) {
                apple.isAlive = false;
            } else {
                removeTail();
            }
        }
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject object : snakeParts) {
            if (object.x == gameObject.x && object.y == gameObject.y) {
                return true;
            }
        }
        return false;
    }
}

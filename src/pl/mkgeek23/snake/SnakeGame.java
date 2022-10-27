package pl.mkgeek23.snake;

import game.engine.GameEngine;
import game.engine.Key;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SnakeGame extends GameEngine {
    public static final int WIDTH = 15, HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Fruit apple;
    private Orange orange;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;
    private Map<String, FruitHelper> fruits;

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.initialize();
    }

    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        initializeFruits();
        createGame();
        setTitle("Snake Game");
    }

    public void initializeFruits(){
        fruits = new HashMap<>();
        fruits.put("Apple", new FruitHelper("\uD83C\uDF4E", Color.RED, 5));
        fruits.put("Lemon", new FruitHelper("\uD83C\uDF4B", Color.YELLOW, 10));
    }

    private void createGame() {
        score = 0;
        setScore(score);
        turnDelay = 300;
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
        setTurnTimer(turnDelay);
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.GREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple() {
        do {
            int newX = getRandomNumber(WIDTH);
            int newY = getRandomNumber(HEIGHT);
            apple = new Fruit(newX, newY, fruits.get("Lemon"));
        } while (snake.checkCollision(apple));
    }
    private void createNewOrange() {
        do {
            int newX = getRandomNumber(WIDTH);
            int newY = getRandomNumber(HEIGHT);
            orange = new Orange(newX, newY);
        } while (snake.checkCollision(orange));
    }

    private void gameOver() {
        isGameStopped = true;
        //showMessageDialog(Color.WHITE, "GAME OVER", Color.RED, 75);
    }

    private void win() {
        isGameStopped = true;
        //showMessageDialog(Color.WHITE, "YOU WIN", Color.GREEN, 75);
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case SPACE:
                if (isGameStopped) {
                    createGame();
                }
                break;
        }
    }

    @Override
    public void onTurn(int time) {
        super.onTurn(time);

        snake.move(apple);
        if (!apple.isAlive) {
            score += apple.getScore();
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }
}
package pl.mkgeek23.snake;

import java.awt.*;

public class FruitHelper {
    private final String FRUIT_SIGN;
    private final Color COLOR;
    private final int SCORE;

    public FruitHelper(String fruitSign, Color color, int score) {
        FRUIT_SIGN = fruitSign;
        COLOR = color;
        SCORE = score;
    }

    public String getFruitSign() {
        return FRUIT_SIGN;
    }

    public Color getColor() {
        return COLOR;
    }

    public int getScore() {
        return SCORE;
    }
}

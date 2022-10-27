package pl.mkgeek23.minesweeper;

import game.engine.GameEngine;

import java.awt.*;

public class Minesweeper extends GameEngine {

    private final int SIDE = 5;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField = 0;
    private int countFlags;
    private boolean isGameStopped;
    private int countClosedTiles;
    private int score;
    private final static String MINE = "\uD83D\uDCA3";
    private final static String FLAG = "\uD83D\uDEA9";
    private final static String FAILFLAG = "\uD83D\uDDF4";

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
        minesweeper.initialize();
    }

    @Override
    public boolean isGameStopped() {
        return isGameStopped;
    }

    private void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        setTitle("Minesweeper");
    }

    private void createGame() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                boolean isMine = getRandomNumber(100) < 15;
                if (isMine) countMinesOnField++;

                gameField[i][j] = new GameObject(j, i, isMine);
                setCellValue(i, j, "");
                setCellColor(i, j, Color.ORANGE);
            }
        }

        countFlags = countMinesOnField;

        countClosedTiles = SIDE * SIDE;
        score = 0;

        setScore(score);

        countMineNeighbors();
    }

    private void restart() {
        isGameStopped = false;
        countClosedTiles = SIDE * SIDE;
        countMinesOnField = 0;
        createGame();
    }

    private void gameOver() {
        isGameStopped = true;
        //showMessageDialog(Color.GREEN, "LOSE", Color.RED, 3);
    }

    private void win() {
        isGameStopped = true;
        flagAllMines();
        //showMessageDialog(Color.GREEN, "WIN", Color.RED, 3);
    }

    private void flagAllMines() {
        for (int y = 0; y < gameField.length; y++) {
            for (int x = 0; x < gameField[0].length; x++) {
                if (!gameField[y][x].isOpen && !gameField[y][x].isFlag) {
                    markTile(x, y);
                }
            }
        }
    }

    private void openTile(int x, int y) {
        if (isGameStopped || gameField[y][x].isOpen || gameField[y][x].isFlag) return;
        gameField[y][x].isOpen = true;
        countClosedTiles--;
        if (gameField[y][x].isMine) {
            setCellValueEx(x, y, Color.RED, MINE);
            gameOver();
        } else {
            if (gameField[y][x].countMineNeighbors == 0) {
                if (x > 0) {
                    if (!gameField[y][x - 1].isOpen) openTile(x - 1, y);
                    if (y > 0) {
                        if (!gameField[y - 1][x - 1].isOpen) openTile(x - 1, y - 1);
                    }
                    if (y < SIDE - 1) {
                        if (!gameField[y + 1][x - 1].isOpen) openTile(x - 1, y + 1);
                    }
                }
                if (x < SIDE - 1) {
                    if (!gameField[y][x + 1].isOpen) openTile(x + 1, y);
                    if (y > 0) {
                        if (!gameField[y - 1][x + 1].isOpen) openTile(x + 1, y - 1);
                    }
                    if (y < SIDE - 1) {
                        if (!gameField[y + 1][x + 1].isOpen) openTile(x + 1, y + 1);
                    }
                }

                if (y > 0) {
                    if (!gameField[y - 1][x].isOpen) openTile(x, y - 1);
                }
                if (y < SIDE - 1) {
                    if (!gameField[y + 1][x].isOpen) openTile(x, y + 1);
                }

                setCellValue(x, y, "");
            } else {
                setCellNumber(x, y, gameField[y][x].countMineNeighbors);
            }
            score += 5;
            setScore(score);
            if (countClosedTiles == countMinesOnField) {
                win();
            }
            setCellColor(x, y, Color.GREEN);
        }
    }

    private void countMineNeighbors() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                if (!gameField[i][j].isMine) gameField[i][j].countMineNeighbors = getNeighbors(gameField[i][j]);
            }
        }
    }

    private void markTile(int x, int y) {
        if (gameField[y][x].isOpen || (countFlags == 0 && !gameField[y][x].isFlag)) return;

        if (!gameField[y][x].isFlag) {
            gameField[y][x].isFlag = true;
            countFlags--;
            setCellValue(x, y, FLAG);
            setCellColor(x, y, Color.YELLOW);
        } else {
            gameField[y][x].isFlag = false;
            countFlags++;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.ORANGE);
        }
    }

    @Override
    public void onMouseLeftAndRightClick(int x, int y) {
        openNeighbourFields(x, y);
    }

    private void openNeighbourFields(int x, int y){
        if (gameField[y][x].isOpen) {
            if (countFlags(x, y) == gameField[y][x].countMineNeighbors){
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (isInRange(x + j, 0, SIDE - 1) && isInRange(y + i, 0, SIDE - 1)) {
                            openTile(x+j, y+i);
                        }
                    }
                }
            }
        }
    }

    public int countFlags(int x, int y) {
        int numOfFlags = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isInRange(x + j, 0, SIDE - 1) && isInRange(y + i, 0, SIDE - 1))
                    numOfFlags += gameField[y + i][x + j].isFlag ? 1 : 0;
            }
        }
        return numOfFlags;
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        if (isGameStopped) {
            restart();
        } else {
            openTile(x, y);
        }
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }

    private int getNeighbors(GameObject gameObject) {
        int mineNeighbors = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isInRange(gameObject.x + j, 0, SIDE - 1) && isInRange(gameObject.y + i, 0, SIDE - 1))
                    mineNeighbors += gameField[gameObject.y + i][gameObject.x + j].isMine ? 1 : 0;
            }
        }

        return mineNeighbors;
    }

}

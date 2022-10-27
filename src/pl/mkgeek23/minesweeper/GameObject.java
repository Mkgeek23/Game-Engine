package pl.mkgeek23.minesweeper;

public class GameObject {
    public int x;
    public int y;

    public boolean isMine = false;
    public boolean isOpen = false;
    public boolean isFlag = false;

    public int countMineNeighbors;

    public GameObject(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }
}

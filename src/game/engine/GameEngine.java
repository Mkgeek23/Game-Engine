package game.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Random;

public class GameEngine extends JFrame implements MouseListener {
    private final int GAMEWIDTH = 500;
    private final int GAMEHEIGHT = 500;
    private JButton[][] cells;

    private boolean leftBtnClicked = false;
    private boolean rightBtnClicked = false;

    private Label scoreLabel;
    private JPanel header;

    public GameEngine() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game Engine");
        setBounds(200, 100, 800, 700);
        setVisible(true);
    }

    private void initScore() {
        if (header == null) {
            initHeader();
        }
        scoreLabel = new Label();
        scoreLabel.setFont(new Font("Courier", Font.PLAIN, 18));
        header.add(scoreLabel, BorderLayout.CENTER);
    }

    private void initHeader() {
        header = new JPanel();
        header.setBounds(0, 0, 100, 100);
        add(header, BorderLayout.PAGE_START);
    }

    public void setScore(int score){
        if(scoreLabel == null){
            initScore();
        }
        scoreLabel.setText(Integer.toString(score));
    }


    public void setScreenSize(int x, int y) {
        setVisible(false);
        JPanel jPanel = new JPanel();

        int width = GAMEWIDTH / x;
        int height = GAMEHEIGHT / y;

        cells = new JButton[y][x];

        jPanel.setLayout(new GridLayout(x, y));

        int borderSize = 1;

        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {

                cells[i][j] = new JButton();
                cells[i][j].setPreferredSize(new Dimension(width, height));
                cells[i][j].setBorder(BorderFactory.createMatteBorder(
                        j != 0 ? -borderSize : borderSize,
                        i != 0 ? -borderSize : borderSize,
                        borderSize,
                        borderSize, Color.BLACK));
                cells[i][j].setBackground(Color.WHITE);
                cells[i][j].addMouseListener(this);
                cells[i][j].setFont(new Font("Courier", Font.PLAIN, 22));
                cells[i][j].setFocusPainted(false);
                jPanel.add(cells[i][j]);

                new JButton();
            }

            add(jPanel, BorderLayout.CENTER);

            pack();

            SwingUtilities.updateComponentTreeUI(this);

            revalidate();
            repaint();

            setVisible(true);
        }
    }

    public boolean isGameStopped() {
        return false;
    }

    public int getRandomNumber(int val) {
        return new Random().nextInt(val);
    }

    public void setCellColor(int x, int y, Color color) {
        cells[y][x].setBackground(color);
    }

    public void setCellValue(int x, int y, String text) {
        cells[y][x].setText(text);
    }

    public void setCellValueEx(int x, int y, Color color, String text) {
        cells[y][x].setText(text);
        setCellColor(x, y, color);
    }

    public void setCellNumber(int x, int y, int number) {
        cells[y][x].setText(Integer.toString(number));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    public void onMouseLeftClick(int x, int y) {
    }

    public void onMouseRightClick(int x, int y) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftBtnClicked = true;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightBtnClicked = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        int y = button.getX() / button.getWidth();
        int x = button.getY() / button.getHeight();

        if (leftBtnClicked && e.getButton() == MouseEvent.BUTTON1)
            onMouseLeftClick(x, y);
        if (rightBtnClicked && e.getButton() == MouseEvent.BUTTON3)
            onMouseRightClick(x, y);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        leftBtnClicked = false;
        rightBtnClicked = false;
    }

    public boolean isInRange(int val, int min, int max) {
        return val >= min && val <= max;
    }
}

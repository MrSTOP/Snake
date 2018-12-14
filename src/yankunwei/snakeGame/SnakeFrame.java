package yankunwei.snakeGame;

import yankunwei.util.MouseComponent;

import javax.swing.*;
import java.util.Vector;

public class SnakeFrame extends JFrame {
    private Vector<Food> foods;
    private GamePanel gamePanel;

    public SnakeFrame() {
        foods = new Vector<>();
        gamePanel = new GamePanel();
//        MouseComponent mouseComponent = new MouseComponent();
//        this.add(mouseComponent);
        this.add(gamePanel);
        this.pack();
//        this.setSize(1920, 1080);
    }
}

package yankunwei.snakeGame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 500;
    private Food food= new Food(100, 100, 30, 30, 5);
    private Snake snake;

    public GamePanel() {
        this.snake = new Snake();
        Random random = new Random();
        new Thread(() -> {
            while (true) {
                int direction = random.nextInt(3);
                for (int i = 0; i < 10; i++) {
                    snake.move(direction);
                    repaint();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        super.paintComponent(graphics);
        graphics2D.fill(food);
        snake.paintSnake(graphics2D);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}

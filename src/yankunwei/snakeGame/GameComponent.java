package yankunwei.snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameComponent extends JComponent {
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 500;
    private static final int GAME_LOGIC_TICK = 200;
    private Food food= new Food(100, 100, 30, 30, 5);
    private Snake snake;
    KeyBoardControl control;
    private int currentDirection;

    public GameComponent() {
        this.control = new KeyBoardControl();
        this.setFocusable(true);
        this.setBackground(Color.CYAN);
        this.addKeyListener(this.control);
        this.snake = new Snake(this.getPreferredSize());
        gameLogicLoop();
    }

    private void gameLogicLoop() {
        Runnable runnable = () -> {
            long start = System.currentTimeMillis();
            long end;
            while (true) {
                end = System.currentTimeMillis();
                if (end - start >= GAME_LOGIC_TICK) {
                    start = System.currentTimeMillis();
                    doGameLogic();
                }
                repaint();
            }
        };
        new Thread(runnable).start();
    }

    private void doGameLogic() {
        snake.move(currentDirection);
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

    public class KeyBoardControl extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (!snake.directionConflict(Snake.DIRECTION_UP)) {
                        currentDirection = Snake.DIRECTION_UP;
                    }
                    break;
                case KeyEvent.VK_S:
                    if (!snake.directionConflict(Snake.DIRECTION_DOWN)) {
                        currentDirection = Snake.DIRECTION_DOWN;
                    }
                    break;
                case KeyEvent.VK_A:
                    if (!snake.directionConflict(Snake.DIRECTION_LEFT)) {
                       currentDirection = Snake.DIRECTION_LEFT;
                    }
                    break;
                case KeyEvent.VK_D:
                    if (!snake.directionConflict(Snake.DIRECTION_RIGHT)) {
                        currentDirection = Snake.DIRECTION_RIGHT;
                    }
                    break;
            }
        }
    }
}

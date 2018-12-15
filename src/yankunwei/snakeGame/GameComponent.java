package yankunwei.snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameComponent extends JComponent {
    private static final int DEFAULT_HEIGHT = 800;
    private static final int DEFAULT_WIDTH = 800;
    private static final int GAME_LOGIC_TICK = 100;//20
    private static final int GAME_RENDER_TICK = 10;

    private SnakePanel parent;
    private Snake snake;
    KeyBoardControl control;
    private int currentDirection;
    private FoodManager foodManager;

    public GameComponent(SnakePanel parent) {
        this.parent = parent;
        this.control = new KeyBoardControl();
        this.setFocusable(true);
        this.setBackground(Color.CYAN);
        this.addKeyListener(this.control);
        this.snake = new Snake(this.getPreferredSize());
        this.foodManager = new FoodManager(this.getPreferredSize());
    }

    public void startGame() {
        Runnable logic = () -> {
            long start = System.currentTimeMillis();
            long end;
            while (true) {
                end = System.currentTimeMillis();
                if (end - start >= GAME_LOGIC_TICK) {
                    start = System.currentTimeMillis();
                    doGameLogic();
                }
            }
        };
        new Thread(logic).start();
        Runnable render = () -> {
            long start = System.currentTimeMillis();
            long end;
            while (true) {
                end = System.currentTimeMillis();
                if (end - start >= GAME_RENDER_TICK) {
                    repaint();
                }
            }
        };
        new Thread(render).start();
    }

    private void doGameLogic() {
        snake.move(currentDirection);
        Food food = foodManager.canEatFood(snake.head);
        if (food != null) {
            System.out.println("EAT");
            foodManager.eatFood(food);
            snake.grow();
        }
        if (snake.conflictToSelf()) {
            System.out.println("CONFLICT");
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        super.paintComponent(graphics);
//        graphics2D.fill(food);
        foodManager.paintFood(graphics2D);
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
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (snake.directionConflict(Snake.DIRECTION_UP)) {
                        currentDirection = Snake.DIRECTION_UP;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (snake.directionConflict(Snake.DIRECTION_DOWN)) {
                        currentDirection = Snake.DIRECTION_DOWN;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (snake.directionConflict(Snake.DIRECTION_LEFT)) {
                       currentDirection = Snake.DIRECTION_LEFT;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (snake.directionConflict(Snake.DIRECTION_RIGHT)) {
                        currentDirection = Snake.DIRECTION_RIGHT;
                    }
                    break;
            }
        }
    }
}

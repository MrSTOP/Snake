package yankunwei.snakeGame;

import yankunwei.snakeGame.food.Food;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameComponent extends JComponent {
    /**
     * 默认高度
     */
    private static final int DEFAULT_HEIGHT = 800;
    /**
     * 默认宽度
     */
    private static final int DEFAULT_WIDTH = 800;
    /**
     * 游戏逻辑帧时长
     */
    private static final int GAME_LOGIC_TICK = 100;//20
    /**
     * 游戏绘制帧时长
     */
    private static final int GAME_RENDER_TICK = 10;

    /**
     * 父面板（用于显示对话框）
     */
    private SnakePanel parent;
    /**
     * 蛇
     */
    private Snake snake;
    /**
     * 键盘控制
     */
    KeyBoardControl control;
    /**
     * 当前蛇的方向
     */
    private int currentDirection;
    /**
     * 食物管理器
     */
    private FoodManager foodManager;
    /**
     * 逻辑线程
     */
    private Thread logicThread;
    /**
     * 绘制线程
     */
    private Thread renderThread;

    /**
     * @param parent 父面板
     */
    public GameComponent(SnakePanel parent) {
        this.parent = parent;
        this.snake = new Snake(this.getPreferredSize());
        this.foodManager = new FoodManager(this.getPreferredSize());
        this.control = new KeyBoardControl();
        this.setFocusable(true);
        this.setBackground(Color.CYAN);
        this.addKeyListener(this.control);
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        Runnable logic = () -> {
            long start = System.currentTimeMillis();
            long end;
            while (!Thread.currentThread().isInterrupted()) {
                end = System.currentTimeMillis();
                if (end - start >= GAME_LOGIC_TICK) {
                    start = System.currentTimeMillis();
                    doGameLogic();
                }
            }
            System.out.println("LOGIC THREAD INTERRUPTED");
        };
        Runnable render = () -> {
            long start = System.currentTimeMillis();
            long end;
            while (!Thread.currentThread().isInterrupted()) {
                end = System.currentTimeMillis();
                if (end - start >= GAME_RENDER_TICK) {
                    repaint();
                }
            }
            System.out.println("RENDER THREAD INTERRUPTED");
        };
        this.logicThread = new Thread(logic);
        this.logicThread.setName("Logic Thread");
        this.renderThread = new Thread(render);
        this.renderThread.setName("Render Thread");
        this.logicThread.start();
        this.renderThread.start();
    }

    /**
     * 停止游戏
     */
    public void stopGame() {
        this.saveGame();
        this.logicThread.interrupt();
        this.renderThread.interrupt();
        System.out.println("STOP");
        parent.returnMainInterface();
    }

    /**
     * 继续游戏
     */
    public void continueGame() {
        this.loadGame();
        this.startGame();
    }

    public void gameOver() {

    }

    /**
     * 执行游戏逻辑
     */
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
            this.gameOver();
        }
    }

    /**
     * 加载游戏
     */
    private void loadGame() {
        try (FileInputStream fileInputStream = new FileInputStream("GameData.dat");
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            this.snake = (Snake) objectInputStream.readObject();
            this.foodManager = (FoodManager) objectInputStream.readObject();
            this.currentDirection = objectInputStream.readInt();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this.parent, "文件不存在", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this.parent, "文件读取失败", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this.parent, "类不存在，这个错误不该发生。", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 保存游戏
     */
    private void saveGame() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("GameData.dat");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(this.snake);
            objectOutputStream.writeObject(this.foodManager);
            objectOutputStream.writeInt(this.currentDirection);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this.parent, "文件不存在", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this.parent, "文件写入失败", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * 绘制所有组件
     * @param graphics 用于绘制Graphics
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        super.paintComponent(graphics);
//        graphics2D.fill(food);
        foodManager.paintFood(graphics2D);
        snake.paintSnake(graphics2D);
        graphics2D.drawString(String.valueOf(snake.getScore()), 10, 10);
    }

    /**
     * 获取组件大小
     * @return 代表组件大小的Dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * 键盘控制
     */
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
                case KeyEvent.VK_ESCAPE:
                    stopGame();
                    break;
            }
        }
    }
}

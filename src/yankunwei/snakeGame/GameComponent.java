package yankunwei.snakeGame;

import yankunwei.newRender.OpenGLRender;
import yankunwei.snakeGame.dialog.GameOverDialog;
import yankunwei.snakeGame.food.Food;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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
    private static final int GAME_LOGIC_TICK = 80;//20
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

//    /**
//     * 绘制线程
//     */
//    private Thread renderThread;

    /**
     * 蛇是否死亡的标志
     */
    private boolean death;

    /**
     * OpenGL绘制的图像
     */
    private BufferedImage image;

    /**
     * 用来渲染的OpenGL
     */
    private OpenGLRender openGLRender;

    /**
     * 游戏结束对话框
     */
    private GameOverDialog gameOverDialog;

    /**
     * @param parent 父面板
     */
    public GameComponent(SnakePanel parent) {
        this.parent = parent;
        this.control = new KeyBoardControl();
        this.setFocusable(true);
        this.setBackground(Color.CYAN);
        this.addKeyListener(this.control);
        this.gameOverDialog = new GameOverDialog(parent.getParentFrame(), 0);
        this.initGame();
    }

    /**
     * 初始化游戏
     */
    private void initGame() {
        this.snake = new Snake(this.getPreferredSize());
        this.foodManager = new FoodManager(this.getPreferredSize());
        this.death = false;
        this.currentDirection = Snake.DIRECTION_DOWN;
        this.openGLRender = OpenGLRender.getInstant();
        this.openGLRender.updateRenderInfo(this, this.snake, this.foodManager);
    }

    /**
     * 开始游戏，如果是新游戏则初始化游戏
     * @param isNew 是否是新游戏
     */
    public void startGame(boolean isNew) {
        if (death || isNew) {
            this.initGame();
            if (logicThread != null) {
                this.logicThread.interrupt();
            }
//            if (renderThread != null) {
//                this.renderThread.interrupt();
//            }
        }
        Runnable logic =() -> {
            long start = System.currentTimeMillis();
            long end;
            while (!Thread.currentThread().isInterrupted() && !GameComponent.this.death) {
                end = System.currentTimeMillis();
                if (end - start >= GAME_LOGIC_TICK) {
                    start = System.currentTimeMillis();
                    snake.move(currentDirection);
                    Food food = foodManager.canEatFood(snake.head);
                    if (food != null) {
//            System.out.println("EAT");
                        snake.grow(foodManager.eatFood(food));
                    }
                    if (snake.conflictToSelf()) {
//                        System.out.println("CONFLICT");
                        GameComponent.this.death = true;
                    }
                    if (snake.conflictToBorder()) {
//                        System.out.println("CONFLICT TO BORDER");
                        GameComponent.this.death = true;
                    }
                }
            }
            System.out.println("LOGIC THREAD INTERRUPTED");
            if (death) {
                GameComponent.this.gameOver();
            }
        };
//        Runnable render = () -> {
//            long start = System.currentTimeMillis();
//            long end;
//            while (!Thread.currentThread().isInterrupted()) {
//                end = System.currentTimeMillis();
//                if (end - start >= GAME_RENDER_TICK) {
//                    start = System.currentTimeMillis();
//                    repaint();
//                }
//            }
//            System.out.println("RENDER THREAD INTERRUPTED");
//        };
        this.logicThread = new Thread(logic, "Logic Thread");
//        this.renderThread = new Thread(render);
//        this.renderThread.setName("Render Thread");
        this.logicThread.start();
//        this.renderThread.start();
        this.openGLRender.updateRenderInfo(this, this.snake, this.foodManager);
        this.openGLRender.startRender();
    }

    public void stopGame(boolean save) {
        if (save) {
            this.saveGame();
        }
        this.logicThread.interrupt();
//        this.renderThread.interrupt();
        this.openGLRender.stopRender();
        this.repaint();
//        System.out.println("STOP");
    }

    /**
     * 继续游戏
     */
    public void continueGame() {
        this.loadGame();
        this.startGame(false);
    }

    public void gameOver() {
        this.death = true;
        this.stopGame(false);
        //System.out.println("STOP");
        this.gameOverDialog.setScore(this.snake.getScore());
        this.gameOverDialog.showDialog();
        parent.returnMainInterface();
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * 绘制所有组件
     * @param graphics 用于绘制Graphics
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        super.paintComponent(graphics);
//        this.foodManager.getFoodRenderInfo(graphics2D);
//        this.snake.getsnakeRenderInfo(graphics2D);
        if (image != null) {
            this.parent.hideOpenGLInitDialog();
            graphics2D.drawImage(image, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, 0, image.getHeight(), image.getWidth(), 0, null);
        }
        graphics2D.setFont(new Font("Serif", Font.PLAIN, 60));
        graphics2D.setColor(Color.GRAY);
        graphics2D.drawString(String.valueOf(snake.getScore()), 10, 60);
        graphics2D.dispose();
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
                    stopGame(true);
                    parent.returnMainInterface();
                    break;
            }
        }
    }
}

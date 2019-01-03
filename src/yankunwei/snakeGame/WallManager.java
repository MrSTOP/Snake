package yankunwei.snakeGame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Random;
import java.util.Vector;

/**
 * Description: 墙壁管理器
 *
 * @author 闫坤炜
 * Date: 2019-01-03 21:22
 */
public class WallManager {
    private static final int WALL_MAX_LENGTH = 120;
    private static final int WALL_MIN_LENGTH = 30;
    private static final int WALL_WIDTH = 20;
    /**
     * 窗口大小
     */
    private Dimension frameBorder;
    private Vector<RectangularShape> walls = new Vector<>();
    private Random random = new Random(System.nanoTime());
    public WallManager(Dimension frameBorder) {
        this.frameBorder = frameBorder;
        this.generateWall();
        for (RectangularShape s: this.walls) {
            System.out.println(s);
        }
    }

    public boolean judgeConflict(Snake snake) {
        for (RectangularShape shape: this.walls) {
            if (shape.intersects(snake.head.getFrame())) {
                return true;
            }
        }
        return false;
    }

    private void generateWall() {
        int wallCount = random.nextInt(6) + 1;
        for (int i = 0; i <= wallCount; i++) {
            if (random.nextBoolean()) {
                this.generateHorizontalWall();
            } else {
                this.generateVerticalWall();
            }
        }
    }

    private void generateVerticalWall() {
        int length = random.nextInt(10) + 5;
        int x = random.nextInt(frameBorder.width - 160) + 20;
        int y = random.nextInt(frameBorder.height - 20) + 20;
        for (int i = 0; i < length; i++) {
            walls.add(new Rectangle2D.Double(x, y + i * 10, 10, 10));
        }
    }

    private void generateHorizontalWall() {
        int length = random.nextInt(10) + 5;
        int x = random.nextInt(frameBorder.width - 20) + 20;
        int y = random.nextInt(frameBorder.height - 160) + 20;
        for (int i = 0; i < length; i++) {
            walls.add(new Rectangle2D.Double(x + i * 10, y, 10, 10));
        }
    }

    public Vector<Point2D.Float> getWallRenderInfo() {
        Vector<Point2D.Float> infos = new Vector<>();
        for (RectangularShape shape: this.walls) {
            float x = (float) (shape.getX() * 2 / frameBorder.width) - 1;
            float y = 2 - (float) (shape.getY() * 2 / frameBorder.height) - 1;
            infos.add(new Point2D.Float(x, y));
        }
        return infos;
    }
}

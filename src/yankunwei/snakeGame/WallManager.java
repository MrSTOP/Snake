package yankunwei.snakeGame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

/**
 * Description: 墙壁管理器
 *
 * @author 闫坤炜
 * Date: 2019-01-03 21:22
 */
public class WallManager implements Serializable {
    /**
     * 窗口大小
     */
    private Dimension frameBorder;
    private Vector<RectangularShape> walls = new Vector<>();
    private Random random = new Random(System.nanoTime());
    public WallManager(Dimension frameBorder) {
        this.frameBorder = frameBorder;
        this.generateWall();
    }

    public boolean judgeConflict(Snake snake) {
        for (RectangularShape shape: this.walls) {
            if (shape.intersects(snake.head.getFrame())) {
                return true;
            }
        }
        return false;
    }

    public boolean judgeConflict(Rectangle2D s) {
        for (RectangularShape shape: this.walls) {
            if (shape.intersects(s)) {
                return true;
            }
        }
        return false;
    }

    private void generateWall() {
        int wallCount = random.nextInt(30) + 1;
        int x = 0;
        int y = 0;
        for (int i = 0; i <= wallCount; i++) {
            int length = random.nextInt(10) + 5;
            if (random.nextBoolean()) {
                do {
                    x = random.nextInt(frameBorder.width - 20) + 20;
                    y = random.nextInt(frameBorder.height - 160) + 20;
                } while (x <= 300 && y <= 400);
                this.generateHorizontalWall(length, x, y);
            } else {
                do {
                    x = random.nextInt(frameBorder.width - 160) + 20;
                    y = random.nextInt(frameBorder.height - 20) + 20;
                } while (x <= 300 && y <= 400);
                this.generateVerticalWall(length, x, y);
            }
        }
    }

    private void generateVerticalWall(int length, int x, int y) {
        for (int i = 0; i < length; i++) {
            walls.add(new Rectangle2D.Double(x, y + i * 10, 10, 10));
        }
    }

    private void generateHorizontalWall(int length, int x, int y) {
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

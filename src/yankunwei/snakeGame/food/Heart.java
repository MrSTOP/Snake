package yankunwei.snakeGame.food;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Description: 心形食物，//可以增加生命
 * @author 闫坤炜
 * Date: 2018-12-17 18:11
 */
public class Heart extends Food {
    private static final int DEFAULT_HEIGHT = 20;
    private static final int DEFAULT_WIDTH = 20;

    public Heart(Point2D point2D) {
        this(point2D.getX(), point2D.getY());
    }

    public Heart(double x, double y) {
        this(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public Heart(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public int getScore() {
        return 20;
    }

    @Override
    public double getMinX() {
        return this.x + this.width / 10;
    }

    @Override
    public double getMinY() {
        return this.y + this.height / 10;
    }

    @Override
    public double getMaxX() {
        return this.x + (this.width * 9) / 10;
    }

    @Override
    public double getMaxY() {
        return this.y + (this.height * 9) / 10;
    }

    @Override
    public Rectangle2D.Double getJudgeBounds() {
        return new Rectangle2D.Double(this.getMinX(), this.getMinY(), this.getMaxX() - this.getMinX(), this.getMaxY() - this.getMinY());
    }

    @Override
    public boolean contains(double x, double y) {
        return x >= this.getMinX()
                && x <= this.getMaxX()
                && y >= this.getMinY()
                && y <= this.getMaxY();
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        if (this.width <= 0 || this.height <= 0) {
            return false;
        }
        return (x + w > this.getMinX() && y + h > this.getMinY() && x < this.getMaxX() && y < this.getMaxY());
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new HeartIterator(this, at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new HeartIterator(this, at);
    }
}

package yankunwei.snakeGame.food;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

/**
 * Description: 心形食物，可以增加生命
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
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
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

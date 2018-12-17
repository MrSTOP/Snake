package yankunwei.snakeGame.food;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.NoSuchElementException;

/**
 * Description: 心形食物的路径遍历器
 * @author 闫坤炜
 * Date: 2018-12-17 18:24
 */
public class HeartIterator implements PathIterator {
    private double startX;
    private double startY;
    private double ctrl1X;
    private double ctrl1Y;
    private double ctrl2X;
    private double ctrl2Y;
    private double ctrl3X;
    private double ctrl3Y;
    private int index;
    private AffineTransform affineTransform;

    public HeartIterator(Heart heart, AffineTransform affineTransform) {
        this.startX = heart.getCenterX();
        this.startY = heart.getY() + heart.getHeight() / 3;
        this.ctrl1X = heart.getX() - heart.getWidth() / 2;
        this.ctrl1Y = heart.getY() - heart.getHeight() / 2;
        this.ctrl2X = this.startX;
        this.ctrl2Y = heart.getMY();
        this.ctrl3X = heart.getMX() + heart.getWidth() / 2;
        this.ctrl3Y = this.ctrl1Y;
        this.affineTransform = affineTransform;
    }

    @Override
    public int getWindingRule() {
        return 0;
    }

    @Override
    public boolean isDone() {
        return this.index > 3;
    }

    @Override
    public void next() {
        index++;
    }

    @Override
    public int currentSegment(float[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("Food iterator out of bounds");
        }
        if (index == 3) {
            return SEG_CLOSE;
        }
        if (index == 0) {
            coords[0] = (float) this.startX;
            coords[1] = (float) this.startY;
        }
        if (index == 1) {
            coords[0] = (float) this.startX;
            coords[1] = (float) this.startY;
            coords[2] = (float) this.ctrl1X;
            coords[3] = (float) this.ctrl1Y;
            coords[4] = (float) this.ctrl2X;
            coords[5] = (float) this.ctrl2Y;
        }
        if (index == 2) {
            coords[0] = (float) this.ctrl2X;
            coords[1] = (float) this.ctrl2Y;
            coords[2] = (float) this.ctrl3X;
            coords[3] = (float) this.ctrl3Y;
            coords[4] = (float) this.startX;
            coords[5] = (float) this.startY;
        }
        if (affineTransform != null) {
            affineTransform.transform(coords, 0, coords, 0, 1);
        }
        return (index == 0 ? SEG_MOVETO : SEG_CUBICTO);
    }

    @Override
    public int currentSegment(double[] coords) {
        if (isDone()) {
        throw new NoSuchElementException("Food iterator out of bounds");
    }
        if (index == 3) {
            return SEG_CLOSE;
        }
        if (index == 0) {
            coords[0] = this.startX;
            coords[1] = this.startY;
        }
        if (index == 1) {
            coords[0] = this.startX;
            coords[1] = this.startY;
            coords[2] = this.ctrl1X;
            coords[3] = this.ctrl1Y;
            coords[4] = this.ctrl2X;
            coords[5] = this.ctrl2Y;
        }
        if (index == 2) {
            coords[0] = this.ctrl2X;
            coords[1] = this.ctrl2Y;
            coords[2] = this.ctrl3X;
            coords[3] = this.ctrl3Y;
            coords[4] = this.startX;
            coords[5] = this.startY;
        }
        if (affineTransform != null) {
            affineTransform.transform(coords, 0, coords, 0, 1);
        }
        return (index == 0 ? SEG_MOVETO : SEG_CUBICTO);
    }
}

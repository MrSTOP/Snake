package yankunwei.snakeGame.food;

import yankunwei.snakeGame.food.Food;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.NoSuchElementException;

public class FoodIterator implements PathIterator {
    private double x;
    private double y;
    private double width;
    private double height;
    private double edgeWidth;
    private double spaceWidth;
    private AffineTransform affineTransform;
    private int index;

    public FoodIterator(Food food, AffineTransform affineTransform) {
        this.x = food.getX();
        this.y = food.getY();
        this.width = food.getWidth();
        this.height = food.getHeight();
        this.edgeWidth = food.getEdgeWidth();
        this.spaceWidth = (this.width - this.edgeWidth) / 2;
        this.affineTransform = affineTransform;
        if (width <= 0 || height <= 0) {
            this.index = 14;
        } else {
            this.index = 0;
        }
    }

    @Override
    public int getWindingRule() {
        return 0;
    }

    @Override
    public boolean isDone() {
        return this.index > 13;
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
        if (index == 13) {
            return SEG_CLOSE;
        }
        if (index == 0) {
            coords[0] = (float) (this.x + this.spaceWidth);
            coords[1] = (float) this.y;
        }
        if (index == 1) {
            coords[0] += (float) edgeWidth;
        }
        if (index == 2) {
            coords[1] += (float) spaceWidth;
        }
        if (index == 3){
            coords[0] += (float) spaceWidth;
        }
        if (index == 4) {
            coords[1] += (float) edgeWidth;
        }
        if (index == 5) {
            coords[0] -= (float) spaceWidth;
        }
        if (index == 6) {
            coords[1] += (float) spaceWidth;
        }
        if (index == 7) {
            coords[0] -= (float) edgeWidth;
        }
        if (index == 8) {
            coords[1] -= (float) spaceWidth;
        }
        if (index == 9) {
            coords[0] -= (float) spaceWidth;
        }
        if (index == 10) {
            coords[1] -= (float) edgeWidth;
        }
        if (index == 11) {
            coords[0] += (float) spaceWidth;
        }
        if (index == 12) {
            coords[1] -= (float) spaceWidth;
        }
        if (affineTransform != null) {
            affineTransform.transform(coords, 0, coords, 0, 1);
        }
        return (index == 0 ? SEG_MOVETO : SEG_LINETO);
    }

    @Override
    public int currentSegment(double[] coords) {
        if (isDone()) {
            throw new NoSuchElementException("Food iterator out of bounds");
        }
        if (index == 13) {
            return SEG_CLOSE;
        }
        if (index == 0) {
            coords[0] = this.x + this.spaceWidth;
            coords[1] = this.y;
        }
        if (index == 1) {
            coords[0] += edgeWidth;
        }
        if (index == 2) {
            coords[1] += spaceWidth;
        }
        if (index == 3){
            coords[0] += spaceWidth;
        }
        if (index == 4) {
            coords[1] += edgeWidth;
        }
        if (index == 5) {
            coords[0] -= spaceWidth;
        }
        if (index == 6) {
            coords[1] += spaceWidth;
        }
        if (index == 7) {
            coords[0] -= edgeWidth;
        }
        if (index == 8) {
            coords[1] -= spaceWidth;
        }
        if (index == 9) {
            coords[0] -= spaceWidth;
        }
        if (index == 10) {
            coords[1] -= edgeWidth;
        }
        if (index == 11) {
            coords[0] += spaceWidth;
        }
        if (index == 12) {
            coords[1] -= spaceWidth;
        }
        if (affineTransform != null) {
            affineTransform.transform(coords, 0, coords, 0, 1);
        }
        return (index == 0 ? SEG_MOVETO : SEG_LINETO);
    }
}

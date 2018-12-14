package yankunwei.snakeGame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Food implements Shape {
    public double x;
    public double y;
    public double width;
    public double height;
    public double edgeWidth;
    private double spaceWidth;

    public Food(double x, double y, double width, double height, double edgeWidth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.edgeWidth = edgeWidth;
        this.spaceWidth = (this.width - this.edgeWidth) / 2;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getEdgeWidth() {
        return edgeWidth;
    }

    public double getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    public double getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }

    public void setFrame(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getBounds() {
        double width = getWidth();
        double height = getHeight();
        if (width < 0 || height < 0) {
            return new Rectangle();
        }
        double MinX = Math.floor(this.x);
        double MinY = Math.floor(this.y);
        double MaxX = Math.ceil(this.x + width);
        double MaxY = Math.ceil(this.y + height);
        return new Rectangle((int) MinX, (int) MinY,
                (int) (MaxX - MinX), (int) (MaxY - MinY));
    }

    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public boolean contains(double x, double y) {
        if (x >= this.x
                && x <= this.x + width
                && y >= this.y
                && y <= this.y + height){
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return this.contains(p.getX(), p.getY());
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        if (this.width <= 0 || this.height <= 0) {
            return false;
        }
        return (x + w > this.x && y + h > this.y && x < this.x + this.width && y < this.y + this.height);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return this.intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return (contains(x, y) &&
                contains(x + w, y) &&
                contains(x, y + h) &&
                contains(x + w, y + h));
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new FoodIterator(this, at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new FoodIterator(this, at);
    }
}

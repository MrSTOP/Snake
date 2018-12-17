package yankunwei.snakeGame.food;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * 食物
 */
public class Food implements Shape, Serializable {
    /**
     * 食物x坐标
     */
    public double x;
    /**
     * 食物y坐标
     */
    public double y;
    /**
     * 食物宽度
     */
    public double width;
    /**
     * 食物高度
     */
    public double height;
    /**
     * 加号的边缘宽度
     */
    public double edgeWidth;
    /**
     * 加号的空白宽度
     */
    private double spaceWidth;

    /**
     * @param x 食物的x坐标
     * @param y 食物的y坐标
     * @param width 食物的宽度
     * @param height 食物的高度
     * @param edgeWidth 加号的宽度
     */
    public Food(double x, double y, double width, double height, double edgeWidth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.edgeWidth = edgeWidth;
        this.spaceWidth = (this.width - this.edgeWidth) / 2;
    }

    /**
     * 获取食物的x坐标
     * @return 食物的x坐标
     */
    public double getX() {
        return x;
    }

    /**
     * 获取食物的y坐标
     * @return 食物的y坐标
     */
    public double getY() {
        return y;
    }

    /**
     * 获取食物的宽度
     * @return 食物的宽度
     */
    public double getWidth() {
        return width;
    }

    /**
     * 获取食物的高度
     * @return 食物的高度
     */
    public double getHeight() {
        return height;
    }

    /**
     * 获取加号的边缘宽度坐标
     * @return 加号的边缘宽度
     */
    public double getEdgeWidth() {
        return edgeWidth;
    }

    /**
     * 获取食物的中心x坐标
     * @return 食物的中心x坐标
     */
    public double getCenterX() {
        return this.getX() + this.getWidth() / 2;
    }

    /**
     * 获取食物的中心y坐标
     * @return 食物的中心y坐标
     */
    public double getCenterY() {
        return this.getY() + this.getHeight() / 2;
    }

    /**
     * 设置食物的位置
     * @param x 食物的新x坐标
     * @param y 食物的新y坐标
     * @param width 食物的新宽度
     * @param height 食物的新高度
     */
    public void setFrame(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * 获取食物的边界
     * @return 一个完全包围食物的Rectangle
     * @see #getBounds2D()
     */
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

    /**
     * 获取食物的边界
     * @return 一个完全包围食物的Rectangle2D，精度比{@link #getBounds() getBounds}高
     * @see #getBounds()
     */
    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    /**
     * 判断点是否包含在食物中
     * @param x 要判断的点的x坐标
     * @param y 要判断的点的y坐标
     * @return 如果点包含在食物中返回true否则返回false
     * @see #contains(Point2D)
     * @see #contains(Rectangle2D)
     * @see #contains(double, double, double, double)
     */
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

    /**
     * 获取食物的边界
     * @param p 要判断的点
     * @return 如果点包含在食物中返回true否则返回false
     * @see #contains(double, double)
     * @see #contains(Rectangle2D)
     * @see #contains(double, double, double, double)
     */
    @Override
    public boolean contains(Point2D p) {
        return this.contains(p.getX(), p.getY());
    }

    /**
     * 测试另一个Shape是否与此食物相交
     * @param x Shape的x坐标
     * @param y Shape的y坐标
     * @param w Shape的宽度
     * @param h Shape的高度
     * @return 如果Shape与食物相交返回true，否则返回false
     * @see #intersects(Rectangle2D)
     */
    @Override
    public boolean intersects(double x, double y, double w, double h) {
        if (this.width <= 0 || this.height <= 0) {
            return false;
        }
        return (x + w > this.x && y + h > this.y && x < this.x + this.width && y < this.y + this.height);
    }

    /**
     * 测试另一个Rectangle2D是否与此食物相交
     * @param r 要测试是否相交的Rectangle2D
     * @return 如果Shape与食物相交返回true，否则返回false
     * @see #intersects(double, double, double, double)
     */
    @Override
    public boolean intersects(Rectangle2D r) {
        return this.intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * 测试另一个Shape是否包含在此食物中
     * @param x Shape的x坐标
     * @param y Shape的y坐标
     * @param w Shape的宽度
     * @param h Shape的高度
     * @return 如果另一个Shape包含在此食物中返回true，否则返回false
     * @see #contains(double, double)
     * @see #contains(Point2D)
     * @see #contains(Rectangle2D)
     */
    @Override
    public boolean contains(double x, double y, double w, double h) {
        return (contains(x, y) &&
                contains(x + w, y) &&
                contains(x, y + h) &&
                contains(x + w, y + h));
    }

    /**
     * 测试另一个Rectangle2D是否包含在此食物中
     * @param r 要测试是否包含的Rectangle2D
     * @return 如果另一个Rectangle2D包含在此食物中返回true，否则返回false
     * @see #contains(double, double)
     * @see #contains(Point2D)
     * @see #contains(double, double, double, double)
     */
    @Override
    public boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * 获取此食物的路径遍历器
     * @param at 在迭代中返回时应用于坐标的可选<code>AffineTransform</code>，如果需要未转换的坐标，则为<code>null</code>
     * @return 此食物的路径遍历器
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new FoodIterator(this, at);
    }

    /**
     * 获取此食物的路径遍历器
     * @param at 在迭代中返回时应用于坐标的可选<code>AffineTransform</code>，如果需要未转换的坐标，则为<code>null</code>
     * @param flatness 平坦度允许线段用于近似弯曲段的最大距离偏离原始曲线上的任何点
     * @return 此食物的路径遍历器
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new FoodIterator(this, at);
    }
}

package yankunwei.snakeGame.food;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Description: 普通的食物
 * @author 闫坤炜
 * Date: 2018-12-17 18:13
 */
public class Plus extends Food {
    private static final int DEFAULT_EDGE_LENGTH = 4;
    /**
     * 加号的边缘宽度
     */
    public double edgeWidth;
    /**
     * 加号的空白宽度
     */
    private double spaceWidth;

    public Plus(Point2D point2D) {
        this(point2D.getX(), point2D.getY());
    }

    /**
     * @param x 食物的x坐标
     * @param y 食物的y坐标
     */
    public Plus(double x, double y) {
        this(x, y, DEFAULT_EDGE_LENGTH);
    }

    /**
     * @param x 食物的x坐标
     * @param y 食物的y坐标
     * @param edgeWidth 加号的边缘宽度
     */
    public Plus(double x, double y, double edgeWidth) {
        this(x, y,DEFAULT_WIDTH, DEFAULT_HEIGHT, edgeWidth);
    }

    /**
     * @param x 食物的x坐标
     * @param y 食物的y坐标
     * @param width 食物的宽度
     * @param height 食物的高度
     * @param edgeWidth 加号的宽度
     */
    public Plus(double x, double y, double width, double height, double edgeWidth) {
        super(x, y, width, height);
        this.edgeWidth = edgeWidth;
        this.spaceWidth = (this.width - this.edgeWidth) / 2;
    }

    /**
     * 获取加号的边缘宽度坐标
     * @return 加号的边缘宽度
     */
    public double getEdgeWidth() {
        return edgeWidth;
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
     * 获取此食物的路径遍历器
     * @param at 在迭代中返回时应用于坐标的可选<code>AffineTransform</code>，如果需要未转换的坐标，则为<code>null</code>
     * @return 此食物的路径遍历器
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new PlusIterator(this, at);
    }

    /**
     * 获取此食物的路径遍历器
     * @param at 在迭代中返回时应用于坐标的可选<code>AffineTransform</code>，如果需要未转换的坐标，则为<code>null</code>
     * @param flatness 平坦度允许线段用于近似弯曲段的最大距离偏离原始曲线上的任何点
     * @return 此食物的路径遍历器
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new PlusIterator(this, at);
    }
}

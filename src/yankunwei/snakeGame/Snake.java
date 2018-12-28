package yankunwei.snakeGame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.util.Vector;

/**
 * 蛇
 */
public class Snake implements Serializable {

    /**
     * 方向常量：向上
     */
    public static final int DIRECTION_UP = 0;
    /**
     * 方向常量：向下
     */
    public static final int DIRECTION_DOWN = 1;
    /**
     * 方向常量：向左
     */
    public static final int DIRECTION_LEFT = 2;
    /**
     * 方向常量：向右
     */
    public static final int DIRECTION_RIGHT = 3;

    /**
     * 单位步长
     */
    private static final int STEPS = 10;

    /**
     * 存储身体的Vector
     */
    private Vector<RectangularShape> body;
    public Vector<RectangularShape> lastBody;
    /**
     * 当前运动方向
     */
    private int direction;
    /**
     * 得分
     */
    private int score;
    /**
     * 蛇头
     */
    public final RectangularShape head;
    /**
     * 窗口大小
     */
    private Dimension frameBorder;

    /**
     * @param border 窗口大小
     */
    public Snake(Dimension border) {
        this(5, 100, 100, border);
    }

    /**
     * @param initLength 初始长度
     * @param headX 头部x坐标
     * @param headY 头部y坐标
     * @param border 窗口大小
     */
    public Snake(int initLength, int headX, int headY, Dimension border) {
        body = new Vector<>();
        RoundRectangle2D arc = new RoundRectangle2D.Double(headX, headY, 10, 10, 10, 500);
        this.head = arc;
        body.add(arc);
        for (int i = 0; i < initLength - 1; i++) {
            Rectangle2D rect = new Rectangle2D.Double(headX + 10 * i, headY, 10, 10);
            body.add(rect);
        }
    this.direction = DIRECTION_DOWN;
        this.frameBorder = border;
        this.lastBody = new Vector<>();
        this.score = 0;
    }

    /**
     * 向指定方向移动蛇
     * @param direction 移动方向
     */
    public void move(int direction) {
        this.lastBody.removeAllElements();
        this.lastBody.addAll(body);
        if (!judgeBorder(this.frameBorder)) {
            this.direction = direction;
            moveSnakeToDirection();
            return;
        }
        Rectangle2D.Double headFrame = (Rectangle2D.Double) head.getFrame();
        if (this.direction == DIRECTION_UP) {
            headFrame.y = frameBorder.height;
        } else if (this.direction == DIRECTION_DOWN) {
            headFrame.y = 0;
        } else if (this.direction == DIRECTION_LEFT) {
            headFrame.x = frameBorder.width;
        } else if (this.direction == DIRECTION_RIGHT) {
            headFrame.x = 0;
        }
        body.get(0).setFrame(headFrame);
        moveSnakeToDirection();
    }

    /**
     * 使蛇身增长并增加指定的分数
     * @param score 要增加的分数
     */
    public void grow(int score) {
        this.score += score;
        RectangularShape tail = body.lastElement();
        Rectangle2D.Double rect = new Rectangle2D.Double(tail.getX(), tail.getY(), tail.getWidth(), tail.getHeight());
        synchronized (this.body) {
            body.add(rect);
        }
    }

    /**
     * 使蛇身增长并增加默认的分数
     */
    public void grow() {
        this.grow(10);
    }

    /**
     * 获取蛇的当前分数
     * @return 当前分数
     */
    public int getScore() {
        return this.score;
    }

    /**
     * 判断是否碰到自己
     * @return 如果碰到自己返回true，否则返回false
     */
    public boolean conflictToSelf() {
        for (RectangularShape shape: body) {
            if (shape instanceof RoundRectangle2D.Double) {
                continue;
            }
            if (shape.intersects(head.getFrame())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据当前方向移动蛇
     */
    private void moveSnakeToDirection(){
        Rectangle2D.Double currentFrame = null;
        for (int i = 0; i < body.size(); ++i) {
            RectangularShape shape = body.get(i);
            if (i == 0) {
                currentFrame = (Rectangle2D.Double) shape.getFrame();
                Rectangle2D.Double frame = (Rectangle2D.Double) shape.getFrame();
                switch (this.direction) {
                    case DIRECTION_UP:
                        frame.y -= STEPS;
                        break;
                    case DIRECTION_DOWN:
                        frame.y += STEPS;
                        break;
                    case DIRECTION_LEFT:
                        frame.x -= STEPS;
                        break;
                    case DIRECTION_RIGHT:
                        frame.x += STEPS;
                        break;
                }
                shape.setFrame(frame);
            } else {
                Rectangle2D.Double temp = (Rectangle2D.Double) shape.getFrame();
                Rectangle2D.Double frontFrame = (Rectangle2D.Double) currentFrame.getFrame();
                shape.setFrame(frontFrame);
                currentFrame = temp;
            }
        }
    }

    /**
     * 判断是否达到窗口边缘
     * @param dimension 窗口大小
     * @return 如果到达窗口边缘返回true，否则返回false
     */
    public boolean judgeBorder(Dimension dimension) {
        if (this.head.getY() >= dimension.height || this.head.getY() <= -10 || this.head.getX() >= dimension.width || this.head.getX() <= -10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断传入的方向是否与当前蛇的运动方向冲突
     * @param direction 要判断的方向
     * @return 如果传入的方向与当前方向冲突返回true，否则返回false
     */
    public boolean directionConflict(int direction) {
        if (this.direction == DIRECTION_UP && direction == DIRECTION_DOWN) {
            return false;
        }
        if (this.direction == DIRECTION_DOWN && direction == DIRECTION_UP) {
            return false;
        }
        if (this.direction == DIRECTION_LEFT && direction == DIRECTION_RIGHT) {
            return false;
        }
        if (this.direction == DIRECTION_RIGHT && direction == DIRECTION_LEFT) {
            return false;
        }
        return true;
    }

    /**
     * 绘制蛇
     * @param graphics2D 用于绘制蛇的Graphics2D
     */
    public void paintSnake(Graphics2D graphics2D) {
//        for (int i = 0; i < body.size(); ++i) {
//            RectangularShape lastPos = lastBody.get(i);
//            RectangularShape pos = body.get(i);
//            if (lastPos != null) {
//                smoothPoint.x -= point.x;
//                smoothPoint.y -= point.y;
//            }
//        }
        synchronized (this.body) {
            for (RectangularShape shape: body) {
                graphics2D.draw(shape);
            }
        }
    }

    /**
     * 设置新的窗口大小
     * @param frameBorder 要设置的新窗口大小
     */
    public void setFrameBorder(Dimension frameBorder) {
        this.frameBorder = frameBorder;
    }
}

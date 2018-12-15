package yankunwei.snakeGame;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

public class Snake {

    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_DOWN = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_RIGHT = 3;

    private static final int STEPS = 10;

    private Vector<RectangularShape> body;
    public Vector<RectangularShape> lastBody;
    private int direction;
    public final RectangularShape head;
    private Dimension frameBorder;

    Snake(Dimension border) {
        this(5, 100, 100, border);
    }

    Snake(int initLength, int headX, int headY, Dimension border) {
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
    }

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

    public void grow() {
        RectangularShape tail = body.lastElement();
        Rectangle2D.Double rect = new Rectangle2D.Double(tail.getX(), tail.getY(), tail.getWidth(), tail.getHeight());
        synchronized (this.body) {
            body.add(rect);
        }
    }

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

    public boolean judgeBorder(Dimension dimension) {
        if (this.head.getY() >= dimension.height || this.head.getY() <= -10 || this.head.getX() >= dimension.width || this.head.getX() <= -10) {
            return true;
        } else {
            return false;
        }
    }

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

    public void setFrameBorder(Dimension frameBorder) {
        this.frameBorder = frameBorder;
    }
}

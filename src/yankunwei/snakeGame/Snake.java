package yankunwei.snakeGame;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

public class Snake {

    private static final int DIRECTION_UP = 0;
    private static final int DIRECTION_DOWN = 1;
    private static final int DIRECTION_LEFT = 2;
    private static final int DIRECTION_RIGHT = 3;

    private Vector<RectangularShape> body;
    private int direction;

    Snake() {
        this(3);
    }

    Snake(int initLength, int headX, int headY) {
        initSnakeBody(initLength);
        this.direction = DIRECTION_DOWN;
    }

    private void initSnakeBody(int initLength, int headX, int headY) {
        body = new Vector<>();
        RoundRectangle2D arc = new RoundRectangle2D.Double(40, 50, 10, 10, 10, 500);
        body.add(arc);
        for (int i = 0; i < initLength - 1; i++) {
            Rectangle2D rect = new Rectangle2D.Double(50 + 10 * i, 50, 10, 10);
            body.add(rect);
        }
    }

    public void move(int direction) {
        this.direction = direction;
        moveSnake();
    }

    private void moveSnake(){
        switch (this.direction) {
            case DIRECTION_UP:
                moveSnakeUp();
                break;
            case DIRECTION_DOWN:
                moveSnakeDown();
                break;
            case DIRECTION_LEFT:
                moveSnakeLeft();
                break;
            case DIRECTION_RIGHT:
                moveSnakeRight();
                break;
        }
    }

    private void moveSnakeRight() {
        for (int i = 0; i < body.size(); ++i) {
            RectangularShape shape = body.get(i);
            if (shape instanceof RoundRectangle2D.Double) {
                Rectangle2D.Double frame = (Rectangle2D.Double) shape.getFrame();
                frame.x++;
                shape.setFrame(frame);
            } else {
                RectangularShape frontShape = body.get(i - 1);
                moveSnakeBodyShape(frontShape, shape);
            }
        }
    }

    private void moveSnakeUp() {
        for (int i = 0; i < body.size(); ++i) {
            RectangularShape shape = body.get(i);
            if (shape instanceof RoundRectangle2D.Double) {
                Rectangle2D.Double frame = (Rectangle2D.Double) shape.getFrame();
                frame.y--;
                shape.setFrame(frame);
            } else {
                RectangularShape frontShape = body.get(i - 1);
                moveSnakeBodyShape(frontShape, shape);
            }
        }
    }

    private void moveSnakeDown() {
        for (int i = 0; i < body.size(); ++i) {
            RectangularShape shape = body.get(i);
            if (shape instanceof RoundRectangle2D.Double) {
                Rectangle2D.Double frame = (Rectangle2D.Double) shape.getFrame();
                frame.y++;
                shape.setFrame(frame);
            } else {
                RectangularShape frontShape = body.get(i - 1);
                moveSnakeBodyShape(frontShape, shape);
            }
        }
    }

    private void moveSnakeLeft() {
        for (int i = 0; i < body.size(); ++i) {
            RectangularShape shape = body.get(i);
            if (shape instanceof RoundRectangle2D.Double) {
                Rectangle2D.Double frame = (Rectangle2D.Double) shape.getFrame();
                frame.x--;
                shape.setFrame(frame);
            } else {
                RectangularShape frontShape = body.get(i - 1);
                moveSnakeBodyShape(frontShape, shape);
            }
        }
    }


    private void moveSnakeBodyShape(RectangularShape frontShape, RectangularShape shape) {
        Rectangle2D.Double frontFrame = (Rectangle2D.Double) frontShape.getFrame();
        shape.setFrame(frontFrame);
    }

    public void paintSnake(Graphics2D graphics2D) {
        for (Shape shape: body) {
            graphics2D.draw(shape);
        }
    }
}

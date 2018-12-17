package yankunwei.snakeGame;

import yankunwei.snakeGame.food.Food;
import yankunwei.snakeGame.food.Plus;
import yankunwei.util.SoundPlayer;

import java.awt.*;
import java.awt.geom.RectangularShape;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

/**
 * 食物管理器
 */
public class FoodManager implements Serializable {
    private static final int DEFAULT_FOOD_WIDTH = 10;
    private static final int DEFAULT_FOOD_HEIGHT = 10;
    private static final int DEFAULT_PLUS_EDGE_WIDTH = 4;
    /**
     * 随机数生成器，用于生成食物坐标
     */
    private final Random random;
    /**
     * 窗口大小
     */
    private Dimension frameBorder;
    /**
     * 存储食物的Vector
     */
    private final Vector<Food> foods;

    /**
     * @param frameBorder 窗口的大小
     */
    public FoodManager(Dimension frameBorder) {
        this.foods = new Vector<>();
        this.frameBorder = frameBorder;
        this.random = new Random(System.nanoTime());
        for (int i = 0; i < 1; i++) {
            Food food = new Plus(random.nextInt(frameBorder.width), random.nextInt(frameBorder.height), DEFAULT_FOOD_WIDTH, DEFAULT_FOOD_HEIGHT, DEFAULT_PLUS_EDGE_WIDTH);
            this.foods.add(food);
        }
    }

    /**
     * 判断是否可以吃掉食物
     * @param snake 蛇头
     * @return food 如果有食物被吃掉则返回被吃掉的食物，否则返回null
     */
    public Food canEatFood(RectangularShape snake) {
        for (Food food: foods) {
            if (food.intersects(snake.getFrame())) {
                return food;
            }
        }
        return null;
    }

    /**
     * 吃掉食物
     * @param food 要被吃掉的食物
     */
    public void eatFood(Food food) {
        this.eatFood(food, true);
    }

    /**
     * 吃掉食物
     * @param food 要被吃掉的食物
     * @param generateFood 是否生成新的食物
     */
    public void eatFood(Food food, boolean generateFood) {
        SoundPlayer.getInstant().playSound("C");
        synchronized (this.foods) {
            foods.remove(food);
            if (generateFood) {
                this.generateFood();
            }
        }
    }

    /**
     * 生成新的食物
     */
    public void generateFood() {
        Food food = new Plus(random.nextInt(frameBorder.width), random.nextInt(frameBorder.height), DEFAULT_FOOD_WIDTH, DEFAULT_FOOD_HEIGHT, DEFAULT_PLUS_EDGE_WIDTH);
            foods.add(food);
    }

    /**
     * 绘制食物
     * @param graphics2D 用于绘制食物的Graphics2D
     */
    public void paintFood(Graphics2D graphics2D) {
        synchronized (this.foods) {
            for (Food food: foods) {
                graphics2D.draw(food);
            }
        }
    }
}

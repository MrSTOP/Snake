package yankunwei.snakeGame;

import yankunwei.snakeGame.food.Food;
import yankunwei.snakeGame.food.FoodRenderInfo;
import yankunwei.snakeGame.food.Heart;
import yankunwei.snakeGame.food.Plus;
import yankunwei.util.SoundPlayer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

/**
 * 食物管理器
 */
public class FoodManager implements Serializable {
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

    private WallManager wallManager;

    private Snake snake;

    /**
     * @param frameBorder 窗口的大小
     */
    public FoodManager(Dimension frameBorder, WallManager wallManager, Snake snake) {
        this.foods = new Vector<>();
        this.frameBorder = frameBorder;
        this.wallManager = wallManager;
        this.snake = snake;
        this.random = new Random(System.nanoTime());
//        for (int i = 0; i < 1; i++) {
            Food food = new Plus(generateFoodPosition());
            this.foods.add(food);
//        }
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
     * @return 得分
     */
    public int eatFood(Food food) {
        return this.eatFood(food, true);
    }

    /**
     * 吃掉食物
     * @param food 要被吃掉的食物
     * @param generateFood 是否生成新的食物
     * @return 得分
     */
    public int eatFood(Food food, boolean generateFood) {
        SoundPlayer.getInstant().playSound("Eat", 30);
        int score = food.getScore();
        synchronized (this.foods) {
            foods.remove(food);
            if (generateFood) {
//                for (int i = 0; i < 100; i++) {
                    this.generateFood();
//                }
            }
        }
        return score;
    }

    /**
     * 生成新的食物
     */
    public void generateFood() {
        Food food;
        if (random.nextInt(100) >= 10) {
            food = new Heart(generateFoodPosition());
        } else {
            food = new Plus(generateFoodPosition());
        }
        foods.add(food);
    }

    private Point2D generateFoodPosition() {
        double x;
        double y;
        Rectangle2D rect;
        do {
            x = random.nextInt(frameBorder.width - 40) + 15;
            y = random.nextInt(frameBorder.height - 40) + 15;
            rect = new Rectangle2D.Double(x - 20, y - 20,  60, 60);
        } while ((x <= 170 && y <= 80)
                || wallManager.judgeConflict(rect)
                || snake.judgeConflict(rect));
        return new Point2D.Double(x, y);
    }

    /**
     * 绘制食物
     * @param graphics2D 用于绘制食物的Graphics2D
     */
    public void paintFood(Graphics2D graphics2D) {
        synchronized (this.foods) {
            for (Food food: foods) {
                graphics2D.draw(food);
//                Color color = graphics2D.getColor();
//                graphics2D.setColor(Color.CYAN);
//                graphics2D.draw(food.getJudgeBounds());
//                graphics2D.setColor(color);
            }
        }
    }

    public Vector<FoodRenderInfo> getFoodRenderInfo() {
        Vector<FoodRenderInfo> points = new Vector<>();
        synchronized (this.foods) {
            for (Food food: foods) {
                float x = (float) (food.getX() * 2 / frameBorder.width) - 1;
                float y = 2 - (float) (food.getY() * 2 / frameBorder.height) - 1;
                FoodRenderInfo.FoodType foodType;
                if (food instanceof Heart) {
                    foodType = FoodRenderInfo.FoodType.HEART;
                } else {
                    foodType = FoodRenderInfo.FoodType.PLUS;

                }
                points.add(new FoodRenderInfo(x, y, foodType));
            }
        }
        return points;
    }
}

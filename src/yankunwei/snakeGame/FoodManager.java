package yankunwei.snakeGame;

import java.awt.*;
import java.awt.geom.RectangularShape;
import java.util.Random;
import java.util.Vector;

public class FoodManager {
    private static final int DEFAULT_FOOD_WIDTH = 10;
    private static final int DEFAULT_FOOD_HEIGHT = 10;
    private static final int DEFAULT_PLUS_EDGE_WIDTH = 4;
    private final Random random;
    private Dimension frameBorder;
    Vector<Food> foods;

    public FoodManager(Dimension frameBorder) {
        this.foods = new Vector<>();
        this.frameBorder = frameBorder;
        this.random = new Random(System.nanoTime());
        for (int i = 0; i < 10; i++) {
            Food food = new Food(random.nextInt(frameBorder.width), random.nextInt(frameBorder.height), DEFAULT_FOOD_WIDTH, DEFAULT_FOOD_HEIGHT, DEFAULT_PLUS_EDGE_WIDTH);
            this.foods.add(food);
        }
    }

    public Food canEatFood(RectangularShape snake) {
        for (Food food: foods) {
            if (food.intersects(snake.getFrame())) {
                return food;
            }
        }
        return null;
    }

    public void eatFood(Food food) {
        this.eatFood(food, true);
    }

    public void eatFood(Food food, boolean generateFood) {
        foods.remove(food);
        if (generateFood) {
            this.generateFood();
        }
    }

    public void generateFood() {
        Food food = new Food(random.nextInt(frameBorder.width), random.nextInt(frameBorder.height), DEFAULT_FOOD_WIDTH, DEFAULT_FOOD_HEIGHT, DEFAULT_PLUS_EDGE_WIDTH);
        foods.add(food);
    }

    public void paintFood(Graphics2D graphics2D) {
        for (Food food: foods) {
            graphics2D.draw(food);
        }
    }
}

package yankunwei.snakeGame.food;

/**
 * Description: 渲染食物所需信息
 *
 * @author 闫坤炜
 * Date: 2019-01-02 18:44
 */
public class FoodRenderInfo {
    public enum FoodType {
        PLUS,
        HEART
    }

    public FoodRenderInfo(float x, float y, FoodType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public float x;
    public float y;
    public FoodType type;
}

package yankunwei.snakeGame;

/**
 * Description: 渲染蛇所需要的信息
 *
 * @author 闫坤炜
 * Date: 2019-01-03 14:53
 */
public class SnakeRenderInfo {
    public enum SnakeType {
        HEAD,
        BODY,
    }

    public SnakeRenderInfo(float x, float y, SnakeType type, int dir) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.dir = dir;
    }

    public float x;
    public float y;
    public SnakeType type;
    public int dir;
}

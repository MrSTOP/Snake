package yankunwei.util;

/**
 * Description: 数据库分数
 *
 * @author 闫坤炜
 * Date: 2018-12-29 15:11
 */
public class Score {
    String UUID;
    String name;
    int score;

    public Score(String UUID, String name, int score) {
        this.UUID = UUID;
        this.name = name;
        this.score = score;
    }
}

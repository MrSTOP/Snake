package yankunwei.snakeGame.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * Description: 游戏结束对话框
 * @author 闫坤炜
 * Date: 2018-12-17 18:02
 */
public class GameOverDialog extends JDialog {
    Font font;
    JLabel label;
    int score;
    public GameOverDialog(Frame owner, int score) {
        super(owner);
        this.setLayout(new GridLayout(2, 1));
        this.font = new Font("Serif", Font.PLAIN, 60);
        this.label = new JLabel("总分：" + String.valueOf(score) + "分");
        this.label.setFont(font);
        JLabel gameOver = new JLabel("Game Over!");
        gameOver.setFont(font);
        gameOver.setForeground(Color.RED);
        this.add(gameOver);
        this.add(label);
        this.setLocationRelativeTo(owner);
        this.pack();
    }

    public void setScore(int score) {
        this.score = score;
        this.label.setText("总分：" + this.score + "分");
        this.pack();
    }
}

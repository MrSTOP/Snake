package yankunwei.snakeGame.dialog;

import yankunwei.snakeGame.SQLiteDatabase;

import javax.swing.*;
import java.awt.*;

/**
 * Description: 游戏结束对话框
 * @author 闫坤炜
 * Date: 2018-12-17 18:02
 */
public class GameOverDialog extends CommonDialog {
    Font font;
    JLabel label;
    JTextField textField;
    Frame owner;
    JButton confirm;
    int score;
    public GameOverDialog(Frame owner, int score) {
        super(owner);
        this.owner = owner;
        this.setLayout(new GridLayout(4, 1));
        this.font = new Font("Serif", Font.PLAIN, 60);
        this.label = new JLabel("总分：" + score + "分");
        this.confirm = new JButton("确认");
        this.confirm.addActionListener(event -> {
            String playerName = textField.getText().trim();
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "玩家名不能为空！", "警告", JOptionPane.WARNING_MESSAGE);
            }
            else{
                confirm.setEnabled(false);
                SQLiteDatabase.insertScore(playerName, this.score);
                this.setVisible(false);
            }
        });
        this.label.setHorizontalAlignment(JLabel.CENTER);
        this.textField = new NameTextField(15, "请输入玩家昵称");
        this.label.setFont(font);
        this.textField.setFont(font);

        JLabel gameOver = new JLabel("Game Over!");
        gameOver.setFont(font);
        gameOver.setForeground(Color.RED);
        gameOver.setHorizontalAlignment(JLabel.CENTER);
        this.add(gameOver);
        this.add(label);
        this.add(textField);
        this.add(confirm);
        this.relocation();
        this.pack();
    }

    @Override
    public void showDialog() {
        this.confirm.setEnabled(true);
        super.showDialog();
    }

    public void setScore(int score) {
        this.score = score;
        this.label.setText("总分：" + this.score + "分");
        this.textField.setText("");
        this.pack();
        this.relocation();
    }
}

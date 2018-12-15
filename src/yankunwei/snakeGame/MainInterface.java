package yankunwei.snakeGame;

import yankunwei.util.LookAndFeelHelper;

import javax.swing.*;

public class MainInterface extends JPanel {
    private JButton startGameButton;
    private JButton continueGameButton;
    private JButton exitGameButton;
    private JButton scoreGameButton;
    private JButton aboutGameButton;
    private JButton lookAndFeelButton;

    public MainInterface() {
        this.startGameButton = new JButton("开始游戏");
        this.continueGameButton = new JButton("继续游戏");
        this.exitGameButton = new JButton("退出游戏");
        this.scoreGameButton = new JButton("分数查看");
        this.aboutGameButton = new JButton("关于");
        this.lookAndFeelButton = new JButton("观感设置");

        this.lookAndFeelButton.addActionListener(event -> {
            JOptionPane.showOptionDialog(this, "选择观感", "观感", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, LookAndFeelHelper.getLookAndFeelButtonArray(this), JOptionPane.OK_OPTION);
        });

        this.add(startGameButton);
        this.add(continueGameButton);
        this.add(exitGameButton);
        this.add(scoreGameButton);
        this.add(aboutGameButton);
        this.add(lookAndFeelButton);
    }
}

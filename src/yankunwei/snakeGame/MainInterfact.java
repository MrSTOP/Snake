package yankunwei.snakeGame;

import javax.swing.*;

public class MainInterfact extends JPanel {
    private JButton startGameButton;
    private JButton continueGameButton;
    private JButton exitGameButton;
    private JButton scoreGameButton;
    private JButton aboutGameButton;
    private JButton lookAndFeelButton;

    public MainInterfact() {
        this.startGameButton = new JButton("开始游戏");
        this.continueGameButton = new JButton("继续游戏");
        this.exitGameButton = new JButton("退出游戏");
        this.scoreGameButton = new JButton("分数查看");
        this.aboutGameButton = new JButton("关于");
        this.lookAndFeelButton = new JButton("观感设置");
    }
}

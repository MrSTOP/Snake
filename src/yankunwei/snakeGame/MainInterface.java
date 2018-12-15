package yankunwei.snakeGame;

import yankunwei.snakeGame.dialog.AboutDialog;
import yankunwei.snakeGame.dialog.LookAndFeelDialog;

import javax.swing.*;

public class MainInterface extends JPanel {
    private SnakePanel parent;
    private JButton startGameButton;
    private JButton continueGameButton;
    private JButton exitGameButton;
    private JButton scoreGameButton;
    private JButton aboutGameButton;
    private JButton lookAndFeelButton;

    public MainInterface(SnakePanel parent) {
        this.parent = parent;
        this.startGameButton = new JButton("开始游戏");
        this.continueGameButton = new JButton("继续游戏");
        this.exitGameButton = new JButton("退出游戏");
        this.scoreGameButton = new JButton("分数查看");
        this.aboutGameButton = new JButton("关于");
        this.lookAndFeelButton = new JButton("观感设置");

        this.startGameButton.addActionListener(event -> {
            this.parent.startGame();
        });
        this.continueGameButton.addActionListener(event -> {
            this.parent.continueGame();
        });
        this.lookAndFeelButton.addActionListener(event -> {
            new LookAndFeelDialog(parent.getParentFrame()).setVisible(true);
        });
        this.aboutGameButton.addActionListener(event -> {
            new AboutDialog(parent.getParentFrame()).setVisible(true);
        });

        this.add(startGameButton);
        this.add(continueGameButton);
        this.add(exitGameButton);
        this.add(scoreGameButton);
        this.add(aboutGameButton);
        this.add(lookAndFeelButton);
    }
}

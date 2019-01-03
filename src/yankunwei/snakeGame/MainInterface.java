package yankunwei.snakeGame;

import yankunwei.snakeGame.dialog.AboutDialog;
import yankunwei.snakeGame.dialog.LookAndFeelDialog;
import yankunwei.util.ToolHelper;

import javax.swing.*;
import java.awt.*;

public class MainInterface extends JPanel {
    private static final int BUTTON_GRID_X = 1;
    private SnakePanel parent;
    private JLabel snakeTitleImage;
    private JButton startGameButton;
    private JButton continueGameButton;
    private JButton exitGameButton;
    private JButton scoreGameButton;
    private JButton aboutGameButton;
    private JButton lookAndFeelButton;

    private AboutDialog aboutDialog;
    private LookAndFeelDialog lookAndFeelDialog;

    public MainInterface(SnakePanel parent) {
        this.setLayout(new GridBagLayout());
        this.parent = parent;
        this.snakeTitleImage = new JLabel(new ImageIcon(ToolHelper.getInstant().getURL("/resources/images/cta.png")));
        this.startGameButton = new JButton("开始游戏");
        this.continueGameButton = new JButton("继续游戏");
        this.exitGameButton = new JButton("退出游戏");
        this.scoreGameButton = new JButton("分数查看");
        this.aboutGameButton = new JButton("关    于");
        this.lookAndFeelButton = new JButton("观感设置");

        this.aboutDialog = new AboutDialog(parent.getParentFrame());
        this.lookAndFeelDialog = new LookAndFeelDialog(parent.getParentFrame());

        this.startGameButton.addActionListener(event -> {
            this.parent.startGame();
        });
        this.continueGameButton.addActionListener(event -> {
            this.parent.continueGame();
        });
        this.exitGameButton.addActionListener(event -> {
            System.exit(0);
        });
        this.lookAndFeelButton.addActionListener(event -> {
            this.lookAndFeelDialog.showDialog();
        });
        this.scoreGameButton.addActionListener(event -> {
            this.parent.showScoreTable();
        });
        this.aboutGameButton.addActionListener(event -> {
            this.aboutDialog.showDialog();
        });
        init();
    }

    private void init() {
        Insets insets = new Insets(10, 30, 10, 30);
        GridBagConstraints titleImageGBC = new GridBagConstraints();
        titleImageGBC.gridx = BUTTON_GRID_X;
        titleImageGBC.gridy = 0;
        GridBagConstraints startGBC = new GridBagConstraints();
        startGBC.gridx = BUTTON_GRID_X;
        startGBC.gridy = 1;
        startGBC.insets = insets;
        startGBC.ipadx = 50;
        startGBC.ipady = 50;
        GridBagConstraints continueGBC = new GridBagConstraints();
        continueGBC.gridx = BUTTON_GRID_X;
        continueGBC.gridy = 2;
        continueGBC.insets = insets;
        continueGBC.ipadx = 50;
        continueGBC.ipady = 50;
        GridBagConstraints exitGBC = new GridBagConstraints();
        exitGBC.gridx = BUTTON_GRID_X;
        exitGBC.gridy = 3;
        exitGBC.insets = insets;
        exitGBC.ipadx = 50;
        exitGBC.ipady = 50;
        GridBagConstraints scoreGBC = new GridBagConstraints();
        scoreGBC.gridx = BUTTON_GRID_X;
        scoreGBC.gridy = 4;
        scoreGBC.insets = insets;
        scoreGBC.ipadx = 50;
        scoreGBC.ipady = 50;
        GridBagConstraints aboutGBC = new GridBagConstraints();
        aboutGBC.gridx = BUTTON_GRID_X;
        aboutGBC.gridy = 5;
        aboutGBC.insets = insets;
        aboutGBC.ipadx = 50;
        aboutGBC.ipady = 50;
        GridBagConstraints lookAndFeelGBC = new GridBagConstraints();
        lookAndFeelGBC.gridx = BUTTON_GRID_X;
        lookAndFeelGBC.gridy = 6;
        lookAndFeelGBC.insets = insets;
        lookAndFeelGBC.ipadx = 50;
        lookAndFeelGBC.ipady = 50;
        this.add(snakeTitleImage, titleImageGBC);
        this.add(startGameButton, startGBC);
        this.add(continueGameButton, continueGBC);
        this.add(exitGameButton, exitGBC);
        this.add(scoreGameButton, scoreGBC);
        this.add(aboutGameButton, aboutGBC);
        this.add(lookAndFeelButton, lookAndFeelGBC);

    }
}

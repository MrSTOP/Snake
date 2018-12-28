package yankunwei.snakeGame;

import yankunwei.util.SoundPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * 贪吃蛇窗口框架
 */
public class SnakeFrame extends JFrame {
    SnakePanel snakePanel;
    public SnakeFrame() {
        SoundPlayer.getInstant().playSound("BGM");
        this.snakePanel = new SnakePanel(this);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension panelSize = snakePanel.getPreferredSize();
        this.setLocation((screen.width - panelSize.width) / 2, (screen.height - panelSize.height) / 2);
        this.add(snakePanel);
        this.pack();
    }
}

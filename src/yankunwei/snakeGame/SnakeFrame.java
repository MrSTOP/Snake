package yankunwei.snakeGame;

import yankunwei.util.SoundPlayer;
import yankunwei.util.ToolHelper;

import javax.swing.*;
import java.awt.*;

/**
 * 贪吃蛇窗口框架
 */
public class SnakeFrame extends JFrame {
    SnakePanel snakePanel;
    public SnakeFrame() {
        this.snakePanel = new SnakePanel(this);
        this.setTitle("贪吃蛇");
        this.setIconImage(new ImageIcon(ToolHelper.getInstant().getURL("/resources/images/logo.png")).getImage());
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension panelSize = snakePanel.getPreferredSize();
        this.setLocation((screen.width - panelSize.width) / 2, (screen.height - panelSize.height) / 2);
        this.add(snakePanel);
        this.setResizable(false);
        this.pack();
    }
}

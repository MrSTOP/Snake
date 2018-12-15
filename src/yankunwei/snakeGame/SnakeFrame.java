package yankunwei.snakeGame;

import javax.swing.*;

public class SnakeFrame extends JFrame {
    SnakePanel snakePanel;
    public SnakeFrame() {
       this.snakePanel = new SnakePanel(this);
       this.add(snakePanel);
       this.pack();
    }
}

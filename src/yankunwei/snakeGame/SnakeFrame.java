package yankunwei.snakeGame;

import yankunwei.util.SoundPlayer;

import javax.swing.*;

public class SnakeFrame extends JFrame {
    SnakePanel snakePanel;
    public SnakeFrame() {
       SoundPlayer.getInstant().playSound("BGM");
       this.snakePanel = new SnakePanel(this);
       this.add(snakePanel);
       this.pack();
    }
}

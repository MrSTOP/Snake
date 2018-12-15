package yankunwei.snakeGame;

import javax.swing.*;

public class SnakeFrame extends JFrame {
    private GameComponent gameComponent;

    public SnakeFrame() {
        gameComponent = new GameComponent();
        this.add(gameComponent);
        this.pack();
//        this.setSize(1920, 1080);
    }
}

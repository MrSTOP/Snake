package yankunwei.snakeGame;

import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {
    private GameComponent gameComponent;

    public SnakeFrame() {
        this.setLayout(new BorderLayout());
        gameComponent = new GameComponent();
        JButton start = new JButton("Start");
        JButton exit = new JButton("Exit");
        start.addActionListener(event -> {
            gameComponent.requestFocusInWindow();
            gameComponent.startGame();
        });
        this.add(gameComponent, BorderLayout.CENTER);
        this.add(start, BorderLayout.NORTH);
        this.add(exit, BorderLayout.SOUTH);
        this.pack();
//        this.setSize(1920, 1080);
    }
}

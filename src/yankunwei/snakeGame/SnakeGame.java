package yankunwei.snakeGame;

import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame snakeFrame = new SnakeFrame();
            snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            snakeFrame.setVisible(true);
        });
    }
}

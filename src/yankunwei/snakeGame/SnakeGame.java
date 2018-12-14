package yankunwei.snakeGame;

import javax.swing.*;
import java.awt.*;

public class SnakeGame {
    public static void main(String[] args) {
//        Frame frame = new Frame();
//        Button button = new Button("JJ");
//        button.addActionListener(event -> {
//            System.out.println("JJ");
//        });
//        frame.add(button);
//        frame.setVisible(true);

//
//            JFrame snakeFrame = new SnakeFrame();
//            snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            snakeFrame.setVisible(true);

        EventQueue.invokeLater(() -> {
            JFrame snakeFrame = new SnakeFrame();
            snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            snakeFrame.setVisible(true);
        });
    }
}

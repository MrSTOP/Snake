package yankunwei.snakeGame.dialog;

import yankunwei.util.LookAndFeelHelper;

import javax.swing.*;
import java.awt.*;

public class LookAndFeelDialog extends JDialog {
    public LookAndFeelDialog(Frame owner) {
        super(owner, true);
        this.setTitle("观感");
        JPanel panel = new JPanel(new GridLayout(3, 3, 10, 10));
        JButton[] buttons = LookAndFeelHelper.getFeelAndLookButtonArray(owner, this);
        for (JButton button : buttons) {
            panel.add(button);
        }
        JButton ok = new JButton("OK");
        ok.addActionListener(event -> this.setVisible(false));
        panel.add(ok);
        this.add(panel);
        this.pack();
    }
}

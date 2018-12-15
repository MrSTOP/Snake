package yankunwei.snakeGame.dialog;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(Frame owner) {
        super(owner);
        JLabel label = new JLabel("作者：闫坤炜");
        this.add(label);
        this.pack();
    }
}

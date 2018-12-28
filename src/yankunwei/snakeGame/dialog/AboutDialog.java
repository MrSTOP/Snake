package yankunwei.snakeGame.dialog;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    private static final int ABOUT_DIALOG_WIDTH = 100;
    private static final int ABOUT_DIALOG_HEIGHT = 100;
    public AboutDialog(Frame owner) {
        super(owner);
        JLabel label = new JLabel("作者：闫坤炜");
        this.setLocationRelativeTo(owner);
        this.add(label);
        this.setSize(ABOUT_DIALOG_WIDTH, ABOUT_DIALOG_HEIGHT);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(ABOUT_DIALOG_WIDTH, ABOUT_DIALOG_HEIGHT);
    }
}

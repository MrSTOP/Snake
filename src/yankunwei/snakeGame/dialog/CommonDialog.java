package yankunwei.snakeGame.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * Description: common dialog
 *
 * @author 闫坤炜
 * Date: 2019-01-03 09:46
 */
public class CommonDialog extends JDialog {
    protected Frame owner;

    public CommonDialog(Frame owner) {
        super(owner);
        this.owner = owner;
    }

    public CommonDialog(Frame owner, boolean modal) {
        super(owner, modal);
        this.owner = owner;
    }

    public void showDialog() {
        this.relocation();
        this.setVisible(true);
    }

    public void hideDialog() {
        this.setVisible(false);
    }

    public void relocation() {
        this.setLocation(locateDialog(owner, this));
    }

    public static Point locateDialog(Component parent, Dialog dialog) {
        int x = parent.getX() + (parent.getPreferredSize().width - dialog.getPreferredSize().width) / 2;
        int y = parent.getY() + (parent.getPreferredSize().height - dialog.getPreferredSize().height) / 2;
        return new Point(x, y);
    }
}

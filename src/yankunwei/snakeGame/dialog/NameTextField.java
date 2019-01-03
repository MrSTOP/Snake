package yankunwei.snakeGame.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * Description: 姓名文本域（带有PlaceHolder）
 *
 * @author 闫坤炜
 * Date: 2019-01-03 10:15
 */
public class NameTextField extends JTextField {

    String placeHolder = "PlaceHolder";

    public NameTextField() {
    }

    public NameTextField(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public NameTextField(int columns, String placeHolder) {
        super(columns);
        this.placeHolder = placeHolder;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(getText().isEmpty()/* && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)*/){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(getFont());
            g2.drawString(placeHolder, 5, 60);
            g2.dispose();
        }
    }
}

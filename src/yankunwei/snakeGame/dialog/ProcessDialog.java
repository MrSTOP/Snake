package yankunwei.snakeGame.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * Description: 进度对话框
 *
 * @author 闫坤炜
 * Date: 2019-01-02 22:52
 */
public class ProcessDialog extends CommonDialog {
    public ProcessDialog(Frame owner) {
        super(owner);
        JLabel label = new JLabel("Initialize OpenGL");
        JProgressBar jProgressBar = new JProgressBar();
        UIManager.put("ProgressBar.cycleTime", 1500);
        jProgressBar.setIndeterminate(true);
        this.add(label, BorderLayout.NORTH);
        this.add(jProgressBar, BorderLayout.CENTER);
        this.relocation();
        this.pack();
    }
}

package yankunwei.snakeGame.dialog;

import yankunwei.util.ToolHelper;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends CommonDialog {
    private static final int ABOUT_DIALOG_WIDTH = 100;
    private static final int ABOUT_DIALOG_HEIGHT = 100;
    Frame owner;
    JLabel author;
    JLabel label;
    JLabel LWJGL3;
    JLabel OpenGL;
    JButton confirm;
    public AboutDialog(Frame owner) {
        super(owner);
        this.owner = owner;
        this.setTitle("关于");
        this.setLayout(new GridBagLayout());
        author = new JLabel("作者：闫坤炜");
        author.setFont(new Font("Serif", Font.PLAIN, 40));
        label = new JLabel("Powered by:");
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        LWJGL3 = new JLabel(new ImageIcon(ToolHelper.getInstant().getURL("/resources/images/LWJGL3.png")));
        OpenGL = new JLabel(new ImageIcon(ToolHelper.getInstant().getURL("/resources/images/OpenGL.png")));
        this.confirm = new JButton("确定");
        confirm.addActionListener(event -> {
            this.hideDialog();
        });
        this.relocation();
        initLayout();
//        this.setModal(true);
        this.setResizable(false);
        this.pack();
    }

    private void initLayout() {
        GridBagConstraints authorGBC = new GridBagConstraints();
        authorGBC.gridx = 0;
        authorGBC.gridy = 0;
        GridBagConstraints labelGBC = new GridBagConstraints();
        labelGBC.gridx = 0;
        labelGBC.gridy = 1;
        labelGBC.anchor = GridBagConstraints.WEST;
        GridBagConstraints LWJGL3GBC = new GridBagConstraints();
        LWJGL3GBC.gridx = 0;
        LWJGL3GBC.gridy = 2;
        GridBagConstraints OpenGLBGC = new GridBagConstraints();
        OpenGLBGC.gridx = 0;
        OpenGLBGC.gridy = 3;
        GridBagConstraints ButtonBGC = new GridBagConstraints();
        ButtonBGC.gridx = 0;
        ButtonBGC.gridy = 4;
        ButtonBGC.fill = GridBagConstraints.HORIZONTAL;
        this.add(author, authorGBC);
        this.add(label, labelGBC);
        this.add(LWJGL3, LWJGL3GBC);
        this.add(OpenGL, OpenGLBGC);
        this.add(confirm, ButtonBGC);
    }
}

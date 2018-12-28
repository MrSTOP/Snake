package yankunwei.snakeGame;

import yankunwei.util.LookAndFeelHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * 进行游戏的面板
 */
public class SnakePanel extends JPanel {
    /**
     * 默认的面板高度
     */
    private static final int DEFAULT_HEIGHT = 800;
    /**
     * 默认的面板宽度
     */
    private static final int DEFAULT_WIDTH = 800;

    /**
     * 主界面代码
     */
    private static final int MAIN_INTERFACE = 0;
    /**
     * 游戏界面代码
     */
    private static final int GAME_COMPONENT = 1;

    /**
     * 父窗口引用
     */
    private final Frame parentFrame;
    /**
     * 主界面
     */
    private MainInterface mainInterface;
    /**
     * 游戏界面
     */
    private GameComponent gameComponent;
    /**
     * 布局管理器
     */
    private final CardLayout layout;

    /**
     * @param parent 父窗口
     */
    public SnakePanel(Frame parent) {

        LookAndFeelHelper.setWindowsLookAndFeel(this);

        this.parentFrame = parent;
        this.mainInterface = new MainInterface(this);
        this.gameComponent = new GameComponent(this);
        this.layout = new CardLayout();
        this.setLayout(layout);
        this.initLayout();
//        this.setSize(1920, 1080);
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        this.add(this.mainInterface, "MainInterface");
        this.add(this.gameComponent, "GameComponent");
        this.layout.show(this, "MainInterface");
    }

    /**
     * @return 返回父窗口
     */
    public Frame getParentFrame() {
        return parentFrame;
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        JOptionPane.showMessageDialog(this, "A S W D或上下左右移动，ESC退出", "游戏方法", JOptionPane.INFORMATION_MESSAGE);
        this.changeComponent(GAME_COMPONENT);
        this.gameComponent.requestFocusInWindow();
        this.gameComponent.startGame(true);
    }

    /**
     * 继续游戏
     */
    public void continueGame() {
        this.changeComponent(GAME_COMPONENT);
        this.gameComponent.requestFocusInWindow();
        this.gameComponent.continueGame();
    }

    /**
     * 返回主界面
     */
    public void returnMainInterface() {
        this.changeComponent(MAIN_INTERFACE);
    }

    /**
     * 切换组件
     * @param ID 组件的ID
     */
    private void changeComponent(int ID) {
        switch (ID) {
            case MAIN_INTERFACE:
                this.layout.show(this, "MainInterface");
                break;
            case GAME_COMPONENT:
                this.layout.show(this, "GameComponent");
                break;
        }
    }

    /**
     * 获取组件大小
     * @return 代表组件大小的Dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}

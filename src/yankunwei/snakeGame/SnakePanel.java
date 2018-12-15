package yankunwei.snakeGame;

import yankunwei.util.LookAndFeelHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SnakePanel extends JPanel {
    private static final int DEFAULT_HEIGHT = 800;
    private static final int DEFAULT_WIDTH = 800;

    private static final int MAIN_INTERFACE = 0;
    private static final int GAME_COMPONENT = 1;

    private final Frame parentFrame;
    private MainInterface mainInterface;
    private GameComponent gameComponent;
    private final CardLayout layout;
    private ComponentControl control;

    public SnakePanel(Frame parent) {

        LookAndFeelHelper.setWindowsLookAndFeel(this);

        this.parentFrame = parent;
        this.mainInterface = new MainInterface(this);
        this.gameComponent = new GameComponent(this);
        this.control = new ComponentControl();
        this.layout = new CardLayout();
        this.setLayout(layout);
        this.initLayout();
        this.addComponentListener(control);
//        this.setSize(1920, 1080);
    }

    private void initLayout() {
        this.add(this.mainInterface, "MainInterface");
        this.add(this.gameComponent, "GameComponent");
        this.layout.show(this, "MainInterface");
    }

    public Frame getParentFrame() {
        return parentFrame;
    }

    public void startGame() {
        this.changeComponent(GAME_COMPONENT);
        this.gameComponent.requestFocusInWindow();
        this.gameComponent.startGame();
    }

    public void continueGame() {
        this.changeComponent(GAME_COMPONENT);
        this.gameComponent.requestFocusInWindow();
        this.gameComponent.continueGame();
    }

    public void returnMainInterface() {
        this.changeComponent(MAIN_INTERFACE);
    }

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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private class ComponentControl extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            super.componentResized(e);
            gameComponent.repaint();
        }
    }
}

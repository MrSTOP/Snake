package yankunwei.util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Stream;

public class LookAndFeelHelper {
    public static JPanel getNewLookAndFeelPanel(Component parent, Component component){
        JPanel lookAndFeelPanel = new JPanel();
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        for (JButton button: getFeelAndLookButtonArray(infos, parent, component)) {
            lookAndFeelPanel.add(button);
        }
        return lookAndFeelPanel;
    }

    /**
     * 将组件的观感设置为Windows观感
     * @param component 要设置观感的组件
     */
    public static void setWindowsLookAndFeel(Component component){
        setLookAndFeel(component, "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    }

    public static void setWindowsClassicLookAndFeel(Component component){
        setLookAndFeel(component, "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
    }

    public static void setMetalLookAndFeel(Component component){
        setLookAndFeel(component, "javax.swing.plaf.metal.MetalLookAndFeel");
    }

    public static void setNimbusLookAndFeel(Component component){
        setLookAndFeel(component, "javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }

    public static void setMotifLookAndFeel(Component component){
        setLookAndFeel(component, "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    }

    /**
     * 根据观感类名设置组件观感
     * @param component 要设置观感的组件
     * @param className 要设置的观感类名
     */
    private static void setLookAndFeel(Component component, String className){
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(component);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JButton[] getFeelAndLookButtonArray(Component parent, Component component) {
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        return getFeelAndLookButtonArray(infos, parent, component);
    }

    private static JButton[] getFeelAndLookButtonArray(UIManager.LookAndFeelInfo[] infos, Component parent, Component component) {
        ArrayList<JButton> buttons = new ArrayList<>(infos.length);
        Arrays.stream(infos).forEach(lookAndFeelInfo -> {
            JButton button = new JButton(lookAndFeelInfo.getName());
            button.addActionListener(event -> {
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                    SwingUtilities.updateComponentTreeUI(parent);
                    SwingUtilities.updateComponentTreeUI(component);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            buttons.add(button);
        });
        return buttons.toArray(new JButton[infos.length]);
    }
}

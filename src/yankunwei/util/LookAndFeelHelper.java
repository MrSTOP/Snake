package yankunwei.util;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Stream;

public class LookAndFeelHelper {
    public static JPanel getNewLookAndFeelPanel(Component component){
        JPanel lookAndFeelPanel = new JPanel();
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        Arrays.stream(infos).forEach(lookAndFeelInfo -> {
            JButton button = new JButton(lookAndFeelInfo.getName());
            button.addActionListener(event -> {
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                    SwingUtilities.updateComponentTreeUI(component);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            lookAndFeelPanel.add(button);
        });
        return lookAndFeelPanel;
    }

    public static JButton[] getLookAndFeelButtonArray(Component component) {
        Vector<JButton> buttons = new Vector<>();
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        Arrays.stream(infos).forEach(lookAndFeelInfo -> {
            JButton button = new JButton(lookAndFeelInfo.getClassName());
            button.addActionListener(event -> {
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                    SwingUtilities.updateComponentTreeUI(component);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            buttons.add(button);
        });
        return buttons.toArray(new JButton[buttons.size()]);
    }

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

    private static void setLookAndFeel(Component component, String className){
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(component);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

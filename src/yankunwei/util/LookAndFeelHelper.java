package yankunwei.util;

import javax.swing.*;
import java.util.Arrays;

public class LookAndFeelHelper {
    public static JPanel getNewLookAndFeelPanel(JFrame frame){
        JPanel lookAndFeelPanel = new JPanel();
        UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
        Arrays.stream(infos).forEach(lookAndFeelInfo -> {
            JButton button = new JButton(lookAndFeelInfo.getName());
            button.addActionListener(event -> {
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                    SwingUtilities.updateComponentTreeUI(frame);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            lookAndFeelPanel.add(button);
        });
        return lookAndFeelPanel;
    }

    public static void setWindowsLookAndFeel(JFrame frame){
        setLookAndFeel(frame, "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    }

    public static void setWindowsClassicLookAndFeel(JFrame frame){
        setLookAndFeel(frame, "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
    }

    public static void setMetalLookAndFeel(JFrame frame){
        setLookAndFeel(frame, "javax.swing.plaf.metal.MetalLookAndFeel");
    }

    public static void setNimbusLookAndFeel(JFrame frame){
        setLookAndFeel(frame, "javax.swing.plaf.nimbus.NimbusLookAndFeel");
    }

    public static void setMotifLookAndFeel(JFrame frame){
        setLookAndFeel(frame, "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    }

    private static void setLookAndFeel(JFrame frame, String className){
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

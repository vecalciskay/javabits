package tresenrayadecision;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.log4j.PropertyConfigurator;

import tresenrayadecision.gui.WinTresEnRaya;

import tresenrayadecision.objects.JuegoTresEnRaya;

public class TresEnRaya {
    public TresEnRaya() {
        JFrame frame = new WinTresEnRaya();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation( ( screenSize.width - frameSize.width ) / 2, ( screenSize.height - frameSize.height ) / 2 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String resource = "/auditoria.properties";
        URL configFileResource = JuegoTresEnRaya.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TresEnRaya();
    }
}

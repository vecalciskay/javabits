package laberinto;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.UIManager;

import laberinto.gui.VentanaLaberinto;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LaberintoEdd {
    
    private final static Logger logger = 
        Logger.getLogger(LaberintoEdd.class);
    
    public LaberintoEdd() {
        logger.info("Lanzando aplicacion de laberinto para estructura de datos");
        
        JFrame frame = new VentanaLaberinto();
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
        URL configFileResource = LaberintoEdd.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new LaberintoEdd();
    }
}

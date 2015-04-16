package exampledao;

import exampledao.gui.VentanaEjemplo;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.net.URL;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class EjemploDao {
    
    private static Logger logger = Logger.getLogger(EjemploDao.class);

    public EjemploDao() {
        JFrame frame = new VentanaEjemplo();
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
        URL configFileResource = EjemploDao.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Comienza aplicacion de test de DAO");
        new EjemploDao();
    }
}

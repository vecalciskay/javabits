package pixelAnimator;

import java.awt.EventQueue;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

import pixelAnimator.gui.JAnimatorFrame;

/**
 * Solamente la clase principal que se ejecuta para ver el programa. Esta clase lo unico que hace en su 
 * main es llamar al constructor del frame y ejecutar los comandos para pasar la mano a este
 * programa.
 * @author Vladimir
 *
 */
public class JAnimator {

	public static void main(String[] args) {
		String resource =
                "/auditoria.properties";
        URL configFileResource;
        configFileResource = JAnimator.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JAnimatorFrame theFrame = new JAnimatorFrame();
					theFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
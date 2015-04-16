package tableronet;

import java.awt.EventQueue;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

import tableronet.gui.TableroNetFrame;


public class TableroNet {

	public static void main(String[] args) {
		String resource =
                "/auditoria.properties";
        URL configFileResource;
        configFileResource = TableroNet.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableroNetFrame frame = new TableroNetFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

package main;

import gui.QuickSortPersonaFrame;

import java.awt.EventQueue;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

public class QSP {

	public static void main(String[] args) {
		String resource =
                "/auditoria.properties";
        URL configFileResource;
        configFileResource = QSP.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuickSortPersonaFrame frame = new QuickSortPersonaFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

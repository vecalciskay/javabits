package arbol;

import java.awt.EventQueue;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

import arbol.gui.OrganigramaArbolFrame;

public class OrganigramaArbol {

	public static void main(String[] args) {
		String resource =
                "/auditoria.properties";
        URL configFileResource;
        configFileResource = OrganigramaArbol.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrganigramaArbolFrame frame = new OrganigramaArbolFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

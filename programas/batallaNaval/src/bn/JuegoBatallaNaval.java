package bn;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

import bn.gui.VentanaTablero;

public class JuegoBatallaNaval {

	public static void main(String[] args) {
		String resource = "/auditoria.properties";
		URL configFileResource;
		configFileResource = JuegoBatallaNaval.class.getResource(resource);
		PropertyConfigurator.configure(configFileResource);
		
		VentanaTablero win = new VentanaTablero();
		win.setVisible(true);
	}

}

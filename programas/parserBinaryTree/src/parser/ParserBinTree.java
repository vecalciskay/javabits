package parser;

import java.awt.EventQueue;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;

import parser.gui.ParserFrame;

public class ParserBinTree {

	public static void main(String[] args) {
		String resource =
                "/auditoria.properties";
        URL configFileResource;
        configFileResource = ParserBinTree.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParserFrame frame = new ParserFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

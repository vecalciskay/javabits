package gui;

import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class VentanaFractal extends JFrame {

	
	private static Logger logger = Logger.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PanelFractal panel;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String resource = "/auditoria.properties";
		URL configFileResource;
		configFileResource = VentanaFractal.class.getResource(resource);
		PropertyConfigurator.configure(configFileResource);
		
		new VentanaFractal();
	}
	
	public VentanaFractal() {
		initGui();
	}

	public void initGui() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		logger.info("Crea el panel 800x 600");
		panel = new PanelFractal(800, 600);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel);
		
		this.setVisible(true);
		this.pack();
	}
}

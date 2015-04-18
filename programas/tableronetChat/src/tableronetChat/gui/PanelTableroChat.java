package tableronetChat.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tableronetChat.modelo.Tablero;


public class PanelTableroChat extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getRootLogger();
	private Tablero elTablero;

	public PanelTableroChat(Tablero t) {
		elTablero = t;
		logger.info("Creado el Panel Tablero");
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(600, 400);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (elTablero != null)
			elTablero.draw(g);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
}
package tableronet.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tableronet.modelo.Punto;
import tableronet.modelo.Tablero;
import tableronet.modelo.Trazo;

/**
 * Esta clase es el panel que controla los movimientos del mouse
 */
public class PanelTablero extends JPanel implements Observer, MouseListener, MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getRootLogger();
	private Tablero elTablero;

	public PanelTablero(Tablero t) {
		elTablero = t;
		logger.info("Creado el Panel Tablero");
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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
	public void mouseDragged(MouseEvent arg0) {
		if (elTablero.getActual() == null) 
			elTablero.setActual(new Trazo());
		
		Trazo objTrazo = elTablero.getActual();
		
		int x1 = arg0.getX();
		int y1 = arg0.getY();
		
		if (objTrazo.getPuntos().tamano() > 0) {
			int x0 = objTrazo.getPuntos().get(0).getX();
			int y0 = objTrazo.getPuntos().get(0).getY();
			
			// No guardamos todo, solamente cunado se ha alejado al menos 5 en valor absoluto
			logger.debug("Distancia del anterior punto es: " + ((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0)));
			if ((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0) > 25) {
				
				logger.debug("Inserta un nuevo punto en actual: " + x1 + "," + y1);
				elTablero.nuevoPuntoEnActual(x1,y1);
			}
		} else {
			elTablero.nuevoPuntoEnActual(x1,y1);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (elTablero.getActual() != null) {
			elTablero.insertarEnMisTrazos(elTablero.getActual());
			elTablero.setActual(null);
		}
	} 

	

	@Override
	public void mouseMoved(MouseEvent arg0) {;}

	@Override
	public void mouseClicked(MouseEvent arg0) {;}

	@Override
	public void mouseEntered(MouseEvent arg0) {;}

	@Override
	public void mouseExited(MouseEvent arg0) {;}

	@Override
	public void mousePressed(MouseEvent arg0) {;}
	
}

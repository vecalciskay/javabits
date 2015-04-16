package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import bll.Figure;
import bll.GameBoard;

public class GamePanel extends JPanel implements Observer, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getRootLogger();
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	
	private GameBoard theGame;
	
	/**
	 * Create the panel.
	 */
	public GamePanel() {
		initComponents();
	}

	private void initComponents() {
		theGame = null;
	}

	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		
		if (theGame == null)
			return;
		
		Iterator<Figure> iter = theGame.getFigures().iterator();
		while(iter.hasNext()) {
			Figure f = iter.next();
			f.draw((Graphics2D) gc);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof GameBoard))
			return;
		
		theGame = (GameBoard)o;
		if (theGame.getFigures().size() == 0)
			this.removeMouseListener(this);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		return;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		return;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		return;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		return;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();

		if (theGame == null)
			return;
		
		Figure touched = theGame.getFigureTouched(x, y);
		if (touched == null) {
			logger.info("Hizo clic en " + x + "x" + y + " pero no habia nada" );
			return;
		}
		
		try {
			theGame.getFigures().remove(touched);
		} catch (Exception e) {
			logger.error("Error al quitar una figura de la lista");
		}
	}
}
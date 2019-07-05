package dijkstra.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import dijkstra.edd.coord.GrafoCoordenada;

public class DijkstraPanel extends JPanel implements Observer {

	private GrafoCoordenada modelo;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public DijkstraPanel(GrafoCoordenada m) {
		this.modelo = m;
		this.modelo.addObserver(this);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		
		if (modelo == null)
			return;
		
		modelo.dibujar((Graphics2D)gc);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}

	public void removeListeners() {
		for(MouseListener m : this.getMouseListeners()) {
			this.removeMouseListener(m);
		}
		
		for(MouseMotionListener m : this.getMouseMotionListeners()) {
			this.removeMouseMotionListener(m);
		}
	}
}

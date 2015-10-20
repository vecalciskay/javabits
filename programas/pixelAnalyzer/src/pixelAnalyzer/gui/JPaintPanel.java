package pixelAnalyzer.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import pixelAnalyzer.obj.Dibujo;

public class JPaintPanel extends JPanel implements Observer, MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dibujo objDibujo;
	
	public JPaintPanel(Dibujo o) {
		objDibujo = o;
		
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}

	public Dimension getPreferredSize() {
		return new Dimension(400,400);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (objDibujo != null) {
			objDibujo.dibujar(g);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		objDibujo.hacerDrag(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		objDibujo.mover(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		objDibujo.hacerClic(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		objDibujo.soltarClic(arg0,getX(), arg0.getY());
	}
}

package mandelAnim.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import mandelAnim.obj.MandelModel;

public class MandelAnimPanel extends JPanel implements Observer, MouseListener {
	private static final Logger logger = LogManager.getRootLogger();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MandelModel modelo;

	public MandelAnimPanel(MandelModel m) {
		this.modelo = m;
		
		this.addMouseListener(this);
		
		logger.info("Se crea el panel y el listener al modelo ya está asignado");
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(modelo.getW(),modelo.getH());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (modelo != null) {
			
			modelo.dibujar(g);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		if (modelo.isRunning()) {
			logger.warn("No puede llamar a los eventso mientras está haciendo la animación");
			return;
		}
		modelo.zoomEn(arg0.getX(), arg0.getY());
		modelo.resetDibujo();
		modelo.setZn();
		modelo.comenzarMandel();
	}
}

package arbol.gui;

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

import arbol.obj.Arbol;
import arbol.obj.Persona;

public class ArbolPanel extends JPanel implements Observer, MouseListener,
		MouseMotionListener {

	private static final Logger logger = LogManager.getRootLogger();

	public static final int ANCHO = 2000;
	public static final int ALTO = 2000;

	private Arbol<Persona> model;
	private OrganigramaArbolFrame parent;

	public ArbolPanel(OrganigramaArbolFrame p) {
		model = null;
		parent = p;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public Dimension getPreferredSize() {
		return new Dimension(ANCHO, ALTO);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		model = (Arbol<Persona>) arg0;

		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (model != null)
			model.draw(g, 10, 10);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		logger.info("Mouse clicked en x:" + arg0.getX() + " y:" + arg0.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		logger.info("Mouse entered en x:" + arg0.getX() + " y:" + arg0.getY());
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		logger.info("Mouse exited en x:" + arg0.getX() + " y:" + arg0.getY());
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		logger.info("Mouse pressed en x:" + arg0.getX() + " y:" + arg0.getY());

		if (model != null) {
			model.clicEn(this.getGraphics(), arg0.getX(), arg0.getY());

			if (model.getSeleccionadoComando().equals(Arbol.MOVER)) {
				model.setSeleccionadoNodoXY(arg0.getX(), arg0.getY());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		logger.info("Mouse released en x:" + arg0.getX() + " y:" + arg0.getY());

		if (model != null) {

			if (model.getSeleccionadoNodo() != null &&
					model.getSeleccionadoComando().equals(Arbol.MOVER)) {
				logger.info("Suelta el nodo seleccionado");
				
				model.mueveNodoSeleccionadoA(this.getGraphics(), arg0.getX(), arg0.getY());
				
				model.setSeleccionadoNodo(null);
				model.setSeleccionadoComando("");
			} else {
				model.clicEn(this.getGraphics(), arg0.getX(), arg0.getY());

				if (model.getSeleccionadoComando().equals(Arbol.ELIMINAR)) {
					model.eliminarSeleccionado();
				}

				if (model.getSeleccionadoComando().equals(Arbol.NUEVOHIJO)) {
					parent.mnuFile_AddNodo_actionPerformed(null);
				}
				
				if (model.getSeleccionadoComando().equals(Arbol.EDITAR)) {
					parent.mnuFile_EditarNodo_actionPerformed(null);
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

		if (model != null && model.getSeleccionadoNodo() != null) {
			model.setSeleccionadoMovidaXY(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}
}

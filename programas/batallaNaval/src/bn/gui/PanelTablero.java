package bn.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import bn.model.Tablero;

/**
 * Esta clase sirve para mostrar ambos tableros, el nuestro y el del oponente
 * Cuando es nuestro no tiene ningun listener, pero cuando es el del oponente
 * permite elegir cual es el objetivo a dispara.
 * 
 * @author Vladimir
 *
 */
public class PanelTablero extends JPanel implements Observer {

	private static final Logger logger = Logger.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int w;
	private int h;
	private boolean tableroPropio;
	private Tablero elTablero;
	private Disparador auxiliarDisparos;

	private VentanaTablero parent;

	public PanelTablero(int width, int height, boolean miTablero,
			VentanaTablero parent) {
		w = width;
		h = height;
		tableroPropio = miTablero;
		auxiliarDisparos = null;

		if (!tableroPropio) {
			auxiliarDisparos = new Disparador();
		}

		this.parent = parent;
	}

	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	public void setElTablero(Tablero elTablero) {
		this.elTablero = elTablero;

		if (auxiliarDisparos != null)
			auxiliarDisparos.setElTablero(elTablero);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (tableroPropio)
			elTablero.drawMiTablero(g, w / 9, h / 7);
		else
			elTablero.drawSuTablero(g, w / 9, h / 7);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.repaint();

		if (tableroPropio)
			return;

		if (elTablero == null)
			return;

		if (elTablero.isTerminado()) {
			if (this.getMouseListeners().length > 0) {
				this.removeMouseListener(auxiliarDisparos);
				this.removeMouseMotionListener(auxiliarDisparos);
			}
			logger.debug("Se quitaron todos los eventos porque terminó el juego");
		} else {

			if (elTablero.isMiTurno()) {
				if (this.getMouseListeners().length == 0) {
					this.addMouseListener(auxiliarDisparos);
					this.addMouseMotionListener(auxiliarDisparos);
				}
				logger.debug("Escuchando eventos en el tablero");
			} else {
				if (this.getMouseListeners().length > 0) {
					this.removeMouseListener(auxiliarDisparos);
					this.removeMouseMotionListener(auxiliarDisparos);
				}
				logger.debug("No se escuchan eventos en el tablero");
			}
		}

		if (arg1 != null) {
			String mensajeParaUsuario = arg1.toString().substring(1);
			String queUsuario = arg1.toString().substring(0, 1);

			if (queUsuario.equals("0")) {
				JOptionPane.showMessageDialog(parent, "HA GANADO!!!");
				parent.setStatus("Fin del juego");
			}
			if (queUsuario.equals("1")) {
				JOptionPane.showMessageDialog(parent, mensajeParaUsuario);
				parent.setStatus("Esperando el tiro del oponente");
			}
			if (queUsuario.equals("2"))
				parent.setStatus("Le toca a usted");

			if (queUsuario.equals("3")) {

				JOptionPane.showMessageDialog(parent,
						"Ha perdido, el último barco ha sido hundido");
				parent.setStatus("Fin del juego");
			}
		}
	}
}

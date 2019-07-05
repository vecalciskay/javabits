package dijkstra.gui.controlador;

import java.awt.event.MouseEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dijkstra.edd.coord.Coordenada;
import dijkstra.edd.coord.GrafoCoordenada;

public class ControladorModoMover extends Controlador {
	private static final Logger logger = LogManager.getRootLogger();

	public ControladorModoMover(GrafoCoordenada modelo) {
		this.modelo = modelo;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		modelo.setNodoSeleccionado(null);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String nodoSeleccionado = modelo.getNodoEnPunto(e.getX(), e.getY());
		if (nodoSeleccionado == null)
			return;

		logger.info("Origen del arco en " + nodoSeleccionado);
		modelo.setNodoSeleccionado(nodoSeleccionado);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if (modelo.getNodoSeleccionado() == null)
			return;

		modelo.cambiarContenidoSeleccionado(new Coordenada(e.getX(), e.getY()));
	}
}

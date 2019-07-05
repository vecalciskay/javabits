package dijkstra.gui.controlador;

import java.awt.event.MouseEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dijkstra.edd.coord.Coordenada;
import dijkstra.edd.coord.GrafoCoordenada;

public class ControladorModoArco extends Controlador {
	private static final Logger logger = LogManager.getRootLogger();
	
	public ControladorModoArco(GrafoCoordenada modelo) {
		this.modelo = modelo;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (modelo.getNodoSeleccionado() == null)
			return;

		Coordenada c = new Coordenada(e.getX(), e.getY());
		modelo.setArcoPosible(c);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (modelo.getNodoSeleccionado() == null)
			return;

		String nodoDestino = modelo.getNodoEnPunto(e.getX(), e.getY());
		if (nodoDestino == null) {
			modelo.setNodoSeleccionado(null);
			modelo.setArcoPosible(null);
			return;
		}

		if (nodoDestino.equals(modelo.getNodoSeleccionado()))
			return;

		try {
			modelo.arco(modelo.getNodoSeleccionado(), nodoDestino, 0, true);
			modelo.calcularDistancias();
			modelo.setNodoSeleccionado(null);
			modelo.setArcoPosible(null);
		} catch (Exception q) {

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String nodoSeleccionado = modelo.getNodoEnPunto(e.getX(), e.getY());
		if (nodoSeleccionado == null)
			return;

		logger.info("Origen del arco en " + nodoSeleccionado);
		modelo.setNodoSeleccionado(nodoSeleccionado);
	}
}

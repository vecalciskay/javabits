package dijkstra.gui.controlador;

import java.awt.event.MouseEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dijkstra.edd.coord.Coordenada;
import dijkstra.edd.coord.GrafoCoordenada;

public class ControladorModoNodo extends Controlador {
	
	private static final Logger logger = LogManager.getRootLogger();

	private char nodoId;

	public ControladorModoNodo(GrafoCoordenada modelo) {

		this.modelo = modelo;
		this.nodoId = 65;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Coordenada c = new Coordenada(e.getX(), e.getY());
		String id = String.valueOf(nodoId);
		logger.info("Creando nodo en posicion (" + c.getX() + "," + c.getY() + ") con id " + id);
		this.modelo.nodo(id, c);
		nodoId++;
	}
}

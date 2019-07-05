package dijkstra.gui.controlador;

import java.awt.event.MouseEvent;

import dijkstra.edd.coord.GrafoCoordenada;

public class ControladorModoDijkstra extends Controlador {
	
	public ControladorModoDijkstra(GrafoCoordenada modelo) {
		this.modelo = modelo;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (modelo.getOrigenHelper() ==  null)
		{
			String origen = modelo.getNodoEnPunto(e.getX(), e.getY());
			if (origen != null) {
				modelo.setOrigenHelper(origen);
				return;
			}
		} else {
			String destinoHelper = modelo.getNodoEnPunto(e.getX(), e.getY());
			if (destinoHelper != null) {
				modelo.setOrigen(modelo.getOrigenHelper());
				modelo.setDestino(destinoHelper);
			} 
			modelo.setOrigenHelper(null);
		}
	}
}

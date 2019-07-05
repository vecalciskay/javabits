package dijkstra.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dijkstra.edd.coord.Coordenada;
import dijkstra.edd.coord.GrafoCoordenada;

public class DijkstraControlador implements MouseListener, MouseMotionListener {
	
	private static final Logger logger = LogManager.getRootLogger();
	
	private char nodoId;

	private GrafoCoordenada modelo;
	
	private boolean modoNodo;
	private boolean modoArco;
	private boolean modoMover;

	private boolean modoDijkstra;

	public DijkstraControlador(GrafoCoordenada modelo) {
		this.modelo = modelo;
		this.nodoId = 65;
	}
	
	public boolean isModoNodo() {
		return modoNodo;
	}
	
	public boolean isModoArco() {
		return modoArco;
	}
	
	public boolean isModoMover() {
		return modoMover;
	}

	public void setModoNodo() {
		this.modoNodo = true;
		this.modoArco = false;
		this.modoMover = false;
		this.modoDijkstra = false;
	}
	public void setModoArco() {
		this.modoNodo = false;
		this.modoArco = true;
		this.modoMover = false;
		this.modoDijkstra = false;
	}
	public void setModoMover() {
		this.modoNodo = false;
		this.modoArco = false;
		this.modoMover = true;
		this.modoDijkstra = false;
	}

	public void setModoDijkstra() {
		this.modoNodo = false;
		this.modoArco = false;
		this.modoMover = false;
		this.modoDijkstra = true;
	}


	@Override
	public void mouseDragged(MouseEvent e) { 
		// No funciona si estamos creando nodos
		if (modoNodo)
			return;
		
		if (modoArco) {
			if (modelo.getNodoSeleccionado() ==  null)
				return;
			
			Coordenada c = new Coordenada(e.getX(), e.getY());
			modelo.setArcoPosible(c);
		}
		
		if (modoMover) {
			if (modelo.getNodoSeleccionado() ==  null)
				return;
			
			modelo.cambiarContenidoSeleccionado(new Coordenada(e.getX(), e.getY()));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (modoNodo) {
			 
			Coordenada c = new Coordenada(e.getX(), e.getY());
			String id = String.valueOf(nodoId);
			logger.info("Creando nodo en posicion (" + c.getX() + "," + c.getY() + ") con id " + id);
			this.modelo.nodo(id, c);
			nodoId++;
			
			return;
		}
		
		if (modoArco) {
			if (modelo.getNodoSeleccionado() ==  null)
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
			} 
			catch(Exception q) 
			{
				
			}
		}
		
		if (modoMover) {
			modelo.setNodoSeleccionado(null);
		}
		
		if (modoDijkstra) {
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
	

	/**
	 * Se utiliza el mouseReleased
	 */

	@Override
	public void mousePressed(MouseEvent e) { 
		// No funciona si estamos creando nodos
		if (modoNodo)
			return;
		
		if (modoArco || modoMover) {
			String nodoSeleccionado = modelo.getNodoEnPunto(e.getX(), e.getY());
			if (nodoSeleccionado == null)
				return;
			
			logger.info("Origen del arco en " + nodoSeleccionado);
			modelo.setNodoSeleccionado(nodoSeleccionado);
		} 
	}


	@Override
	public void mouseClicked(MouseEvent e) { ; }
	
	@Override
	public void mouseEntered(MouseEvent e) { ; }

	@Override
	public void mouseExited(MouseEvent e) { ; }

	@Override
	public void mouseMoved(MouseEvent e) { ; }


}

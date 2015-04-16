package tableronet.modelo;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.Observable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tableronet.edd.Cadena;

public class Tablero extends Observable {
	private static final Logger logger = LogManager.getRootLogger();
	
	private Cadena<Trazo> misTrazos;
	private Cadena<Trazo> susTrazos;
	
	private Trazo actual;

	public Tablero() {
		misTrazos = new Cadena<Trazo>();
		susTrazos = new Cadena<Trazo>();
		
		actual = null;
	}
	
	public void insertarEnSusTrazos(Trazo t) {
		logger.debug("Tablero recibe en sus trazos un nuevo trazo " + t);
		susTrazos.insertar(t);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void insertarEnMisTrazos(Trazo t) {
		logger.debug("Tablero recibe en mis trazos un nuevo trazo " + t);
		misTrazos.insertar(t);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Solamente dibuja cada uno de los trazos de ambas cadenas y también el trazo actual si es dif de nulo
	 * @param gc
	 */
	public void draw(Graphics gc) {
		Iterator<Trazo> i = misTrazos.iterator();
		while(i.hasNext()) {
			Trazo t = i.next();
			t.draw(gc);
		}
		
		i = susTrazos.iterator();
		while(i.hasNext()) {
			Trazo t = i.next();
			t.draw(gc);
		}
		
		if (actual != null) {
			actual.draw(gc);
		}
	}

	public Cadena<Trazo> getMisTrazos() {
		return misTrazos;
	}

	public void setMisTrazos(Cadena<Trazo> misTrazos) {
		this.misTrazos = misTrazos;
	}

	public Cadena<Trazo> getSusTrazos() {
		return susTrazos;
	}

	public void setSusTrazos(Cadena<Trazo> susTrazos) {
		this.susTrazos = susTrazos;
	}

	public Trazo getActual() {
		return actual;
	}

	public void setActual(Trazo actual) {
		this.actual = actual;
	}

	public void nuevoPuntoEnActual(int x1, int y1) {
		actual.getPuntos().insertar(new Punto(x1, y1));
		
		this.setChanged();
		this.notifyObservers();
	} 
}

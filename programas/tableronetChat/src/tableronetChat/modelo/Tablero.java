package tableronetChat.modelo;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.Observable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tableronetChat.edd.Cadena;

public class Tablero extends Observable {
	private static final Logger logger = LogManager.getRootLogger();
	
	private Cadena<Mensaje> mensajes;

	public Tablero() {
		mensajes = new Cadena<Mensaje>();
	}
	
	public void insertarEnMensajes(Mensaje t) {
		logger.debug("Tablero recibe en mis trazos un nuevo trazo " + t);
		mensajes.insertar(t);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Solamente dibuja cada uno de los trazos de ambas cadenas y también el trazo actual si es dif de nulo
	 * @param gc
	 */
	public void draw(Graphics gc) {
		
		int y = 10;
		
		Iterator<Mensaje> i = mensajes.iterator();
		while(i.hasNext()) {
			Mensaje t = i.next();
			t.draw(gc, y);
			
			y = y + t.getAlto() + 10;
		}
	}

	public Mensaje getUltimoMensajeMio() {

		Iterator<Mensaje> i = mensajes.iterator();
		while(i.hasNext()) {
			Mensaje t = i.next();
			if (!t.isUsuarioRemoto())
				return t;
		}
		
		return null;
	}

}

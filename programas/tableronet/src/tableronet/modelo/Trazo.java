package tableronet.modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tableronet.edd.Cadena;

/**
 * Esta clase representa un trazo dibujado. Un trazo puede tener dos o más puntos
 * @author Vladimir
 *
 */
public class Trazo {
	
	private static final Logger logger = LogManager.getRootLogger();

	private Cadena<Punto> puntos;
	private int color;
	private boolean enviado;
	
	public Trazo() {
		puntos = new Cadena<Punto>();
		enviado = false;
		color = (new Color(0,0,0)).getRGB();
	}
	
	public Trazo(String nuevoTrazo) throws Exception {
		String[] losPuntosString = nuevoTrazo.split("\\|");
		
		puntos = new Cadena<Punto>();
		
		for(String unPuntoString : losPuntosString) {
			if (unPuntoString.isEmpty())
				continue;
			
			logger.debug("Leyendo el punto: " + unPuntoString);
			try {
				Punto nuevoPunto = new Punto(unPuntoString);
				puntos.insertar(nuevoPunto);
			} catch (Exception e) {
				logger.error("Formato del punto incorrecto", e);
				throw new Exception("Mal formato del trazo en el punto." + e.getMessage());
			}			
		}
		
		enviado = false;
		color = (new Color(0,0,0)).getRGB();
	}
	
	/** 
	 * Este metodo devuelve el trazo en forma de string. Este formato es el que se puede
	 * utilizar para crear un trazo a partir de un string
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		Iterator<Punto> i = puntos.iterator();
		while(i.hasNext()) {
			Punto pto = i.next();
			sb.append("|" + pto.toString());
		}
		
		return sb.substring(1);
	}

	public Cadena<Punto> getPuntos() {
		return puntos;
	}

	public void setPuntos(Cadena<Punto> puntos) {
		this.puntos = puntos;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public void draw(Graphics gc) {

		gc.setColor(new Color(color));
		
		if (puntos.vacia())
			return;
		
		if (puntos.tamano() == 1) {
			int x = puntos.get(0).getX();
			int y = puntos.get(0).getY();
			gc.setColor(new Color(color));
			gc.drawLine(x, y, x, y);
			return;
		}
		
		int x2, y2;
		int x1 = Integer.MIN_VALUE, y1 = Integer.MIN_VALUE;
		
		Iterator<Punto> i = puntos.iterator();
		while(i.hasNext()) {
			Punto pto = i.next();
			if (x1 == Integer.MIN_VALUE) {
				x1 = pto.getX();
				y1 = pto.getY();
				continue;
			}
			
			x2 = pto.getX();
			y2 = pto.getY();
			
			gc.drawLine(x1, y1, x2, y2);
			
			x1 = x2;
			y1 = y2;
		}
	}
}

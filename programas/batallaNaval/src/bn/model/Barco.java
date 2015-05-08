package bn.model;

import java.awt.Graphics;

/**
 * Esta clase es cualquier tipo de barco de mi tablero. Es abstracta ya que contempla todos los tipos
 * de barco que se deben implemntar en cada una de las subclases correspondientes.
 * @author Vladimir
 *
 */
public abstract class Barco {

	protected int tamano;
	protected int fila;
	protected int col;
	protected boolean vertical;
	protected int nroAciertos;
	
	/**
	 * SOlamente dibuja la embarcacion en el lugar fila, col del tamano que se le indica
	 * en el sentido en el cual se tiene que colocar.
	 * Si el nro de aciertos es igual o mayor al tamano entonces esta destruido
	 * @param g
	 */
	public abstract void draw(Graphics g, int anchoCelda, int altoCelda);
	
	
	protected String[] getPosiciones() {
		// LA primera posicion es siempre fila col
		String[] result = new String[tamano];
		int idx = 0;
		
		if (vertical) {
			for(int i=fila; i< (fila + tamano); i++) {
				result[idx++] = i + "," + col;
			}
		} else {
			for(int i=col; i< (col + tamano); i++) {
				result[idx++] = fila + "," + i;
			}
		}
		
		return result;
	}
	
	/**
	 * La respuesta es de tipo String y solamente puee tener tres valores:
	 * 1. FALLA
	 * 2. ACIERTO
	 * 3. DESTRUIDO
	 * @param f
	 * @param c
	 * @return
	 */
	public String getRespuestaParaTiroEn(int f, int c) {
		String[] posiciones = getPosiciones();
		for(String s : posiciones) {
			if (s.equals(f + "," + c)) {
				nroAciertos++;
				
				if (nroAciertos < tamano)
					return Tablero.ACERTO;
				else
					return Tablero.DESTRUIDO;
			}
		}
		
		return Tablero.FALLA;
	}

	public int getTamano() {
		return tamano;
	}

	public void setTamano(int tamano) {
		this.tamano = tamano;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public int getNroAciertos() {
		return nroAciertos;
	}

	public void setNroAciertos(int nroAciertos) {
		this.nroAciertos = nroAciertos;
	}
}

package bn.model;

import java.awt.Color;
import java.awt.Graphics;

public class Lancha extends Barco {

	public Lancha(int f, int c, boolean v) {
		fila = f;
		col = c;
		vertical = v;
		tamano = 2;
		nroAciertos = 0;
	}
	
	/**
	 * Una lancha puede estar dibujada simplemente \__/
	 * 
	 */
	@Override
	public void draw(Graphics g, int anchoCelda, int altoCelda) {
		if (!vertical) {
			
			int[] px = new int[4];
			px[0] = anchoCelda * (col + 1) + 10;
			px[1] = anchoCelda * (col + 3) - 10;
			px[2] = anchoCelda * (col + 3) - 20;
			px[3] = anchoCelda * (col + 1) + 20;
			int[] py = new int[4];
			py[0] = altoCelda * (fila + 1) + 10;
			py[1] = altoCelda * (fila + 1) + 10;
			py[2] = altoCelda * (fila + 2) - 10;
			py[3] = altoCelda * (fila + 2) - 10;
			
			g.setColor(Color.black);
			g.fillPolygon(px, py, 4);
			
		} else {
			
			int[] px = new int[4];
			px[0] = anchoCelda * (col + 1) + 14;
			px[1] = anchoCelda * (col + 1) + 14;
			px[2] = anchoCelda * (col + 2) - 10;
			px[3] = anchoCelda * (col + 2) - 10;
			int[] py = new int[4];
			py[0] = altoCelda * (fila + 1) + 10;
			py[1] = altoCelda * (fila + 3) - 10;
			py[2] = altoCelda * (fila + 3) - 20;
			py[3] = altoCelda * (fila + 1) + 20;
			
			g.setColor(Color.black);
			g.fillPolygon(px, py, 4);
		}
	}

	@Override
	public String toString() {
		return "Lancha (" + fila + "," + col + ")";
	}
}

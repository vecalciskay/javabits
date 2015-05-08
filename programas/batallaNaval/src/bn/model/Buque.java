package bn.model;

import java.awt.Color;
import java.awt.Graphics;

public class Buque extends Barco {
	
	public Buque(int f, int c, boolean v) {
		fila = f;
		col = c;
		vertical = v;
		tamano = 4;
		nroAciertos = 0;
	}

	@Override
	public void draw(Graphics g, int anchoCelda, int altoCelda) {
		if (!vertical) {
			
			int[] px = new int[4];
			px[0] = anchoCelda * (col + 1) + 10;
			px[1] = anchoCelda * (col + 5) - 10;
			px[2] = anchoCelda * (col + 5) - 20;
			px[3] = anchoCelda * (col + 1) + 20;
			int[] py = new int[4];
			py[0] = altoCelda * (fila + 1) + 17;
			py[1] = altoCelda * (fila + 1) + 17;
			py[2] = altoCelda * (fila + 2) - 7;
			py[3] = altoCelda * (fila + 2) - 7;
			
			g.setColor(Color.black);
			g.fillPolygon(px, py, 4);
			
			px = new int[3];
			px[0] = anchoCelda * (col + 1) + 30;
			px[1] = anchoCelda * (col + 3);
			px[2] = anchoCelda * (col + 5) - 30;
			py = new int[3];
			py[0] = altoCelda * (fila + 2) - 7;
			py[1] = altoCelda * (fila + 1) + 6;
			py[2] = altoCelda * (fila + 2) - 7;
			
			g.fillPolygon(px, py, 3);
			
		} else {
			
			int[] px = new int[4];
			px[0] = anchoCelda * (col + 1) + 16;
			px[1] = anchoCelda * (col + 1) + 16;
			px[2] = anchoCelda * (col + 2) - 10;
			px[3] = anchoCelda * (col + 2) - 10;
			int[] py = new int[4];
			py[0] = altoCelda * (fila + 1) + 10;
			py[1] = altoCelda * (fila + 5) - 10;
			py[2] = altoCelda * (fila + 5) - 20;
			py[3] = altoCelda * (fila + 1) + 20;
			
			g.setColor(Color.black);
			g.fillPolygon(px, py, 4);
			
			px = new int[3];
			px[0] = anchoCelda * (col + 2) - 10;
			px[1] = anchoCelda * (col + 1) + 5;
			px[2] = anchoCelda * (col + 2) - 10;
			py = new int[3];
			py[0] = altoCelda * (fila + 1) + 20;
			py[1] = altoCelda * (fila + 3);
			py[2] = altoCelda * (fila + 5) - 20;
			
			g.fillPolygon(px, py, 3);
		}
	}

	@Override
	public String toString() {
		return "Buque (" + fila + "," + col + ")";
	}
}

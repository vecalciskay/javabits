package pixelAnalyzer.obj.tool;

import java.awt.Color;
import java.awt.Graphics;

import pixelAnalyzer.obj.Dibujo;

public class Pintador extends Herramienta {
	
	private Dibujo elDibujo;
	private int colorOriginal;
	private int colorPintado;
	
	public Pintador() {
		colorOriginal = -1;
		elDibujo = null;
		nombre = "Pintador";
	}

	@Override
	public void clicEn(Dibujo obj, int x, int y) {
		elDibujo = obj;
		colorOriginal = obj.getPixel(x, y);
		Color c = obj.getContextoActual().getElColor();
		int cInt = c.getRed() * 256 * 256 + c.getGreen() * 256 + c.getBlue();
		if (cInt == colorOriginal)
			return;
		
		colorPintado = cInt;
		try {
		pintarEnDibujo(x, y);
		} catch(Throwable possibleStackOverflowError) {
			
			;
		}
		elDibujo.notificar();
	}

	private void pintarEnDibujo(int x, int y) throws StackOverflowError {
		int[][] pixeles = elDibujo.getPixeles();
		
		if (pixeles[x][y] != colorOriginal)
			return;
		
		pixeles[x][y] = colorPintado;
		if (elDibujo.getAlto() > (y+1))
			pintarEnDibujo(x, y+1);
		if ((y-1) >= 0)
			pintarEnDibujo(x, y-1);
		if (elDibujo.getAncho() > (x+1))
			pintarEnDibujo(x+1, y);
		if (x-1 >= 0)
			pintarEnDibujo(x-1, y);
	}

	@Override
	public void dragHasta(Dibujo obj, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mueveA(Dibujo obj, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dibujar(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void soltar(Dibujo dibujo, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}

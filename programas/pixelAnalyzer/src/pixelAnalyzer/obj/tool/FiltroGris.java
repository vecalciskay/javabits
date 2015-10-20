package pixelAnalyzer.obj.tool;

import java.awt.Graphics;

import pixelAnalyzer.obj.Dibujo;

public class FiltroGris extends Herramienta {

	@Override
	public void clicEn(Dibujo obj, int x, int y) {

		int[][] pixeles = obj.getPixeles();
		int w = obj.getAncho();
		int h = obj.getAlto();
		
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				pixeles[i][j] = convertirGris(pixeles[i][j]);
			}
		}
		
		obj.notificar();

	}
	
	
	private int convertirGris(int color) {
		
		int resto = color;
		
		int b = resto % 256;
		resto = resto - b;
		
		int g = resto % (256 * 256);
		resto = resto - g;
		g = g / 256;
		
		int r = resto / (256 * 256);
		
		int gris = (r + g + b)/3;
		
		return gris * 256*256 + gris * 256 + gris;
	}


	@Override
	public void dragHasta(Dibujo obj, int x, int y) {
		;
	}

	@Override
	public void mueveA(Dibujo obj, int x, int y) {
		;
	}

	@Override
	public void dibujar(Graphics g) {
		;
	}


	@Override
	public void soltar(Dibujo dibujo, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
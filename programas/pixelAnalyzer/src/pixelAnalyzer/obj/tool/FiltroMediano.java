package pixelAnalyzer.obj.tool;

import java.awt.Graphics;
import java.util.Arrays;

import pixelAnalyzer.obj.Dibujo;

public class FiltroMediano extends Herramienta {

	@Override
	public void clicEn(Dibujo obj, int x, int y) {

		int[][] pixeles = obj.getPixeles();
		int w = obj.getAncho();
		int h = obj.getAlto();
		
		int[][] result = new int[w][h];
		
		
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int[] arreglo9 = obtenerMascara9(pixeles, i,j,w,h,0);
				Arrays.sort(arreglo9);
				result[i][j] = arreglo9[4];
			}
		}
		
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				pixeles[i][j] = result[i][j];
			}
		}
		
		obj.notificar();

	}

	private int[] obtenerMascara9(int[][] img, int i, int j, int w, int h, int k) {
		
		int arr[] = new int[9];
		
		arr[0] = (i-1 >=0 && j-1 >= 0) ? img[i-1][j-1] : k;
		arr[1] = (j-1 >= 0) ? img[i][j-1] : k;
		arr[2] = (i+1 <w && j-1 >= 0) ? img[i+1][j-1] : k;
		
		arr[3] = (i-1 >=0) ? img[i-1][j] : k;
		arr[4] = img[i][j];
		arr[5] = (i+1 <w) ? img[i+1][j] : k;
		
		arr[6] = (i-1 >=0 && j+1 <h) ? img[i-1][j+1] : k;
		arr[7] = (j+1 <h) ? img[i][j+1] : k;
		arr[8] = (i+1 <w && j+1 <h) ? img[i+1][j+1] : k;
		
		return arr;
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

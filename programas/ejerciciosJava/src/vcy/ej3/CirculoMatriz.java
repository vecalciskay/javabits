package vcy.ej3;

public class CirculoMatriz {

	private int[][] matriz;
	private double centro;
	private double radio;

	public CirculoMatriz(int tamano) {
		matriz = new int[tamano][tamano];
		
		centro = (double)tamano / 2.0;
		radio = (double)tamano / 2.0;
	}

	public boolean puntoDentroDeCirculo(int i, int j) {
		// x*x + y*y = r*r
		double x_x_plus_y_y = ((double)i-centro) * ((double)i-centro) +
				((double)j-centro) * ((double)j-centro);
		
		if (x_x_plus_y_y < (radio * radio))
			return true;
		
		return false;
	}

	public void cambiarA(int val, int i, int j) {
		matriz[i][j] = val;
	}

	public String matrizComoString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				sb.append(matriz[i][j]);
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}

}

package vcy.ej3;

public class Ej3Main {

	public static void main(String[] args) {
		
		int tamano = 10;
		
		CirculoMatriz cm = new CirculoMatriz(tamano);
		
		for(int i=0; i<tamano; i++) {
			for (int j = 0; j < tamano; j++) {
				if (cm.puntoDentroDeCirculo(i,j)) {
					cm.cambiarA(1, i, j);
				}
			}
		}
		
		System.out.println(cm.matrizComoString());
	}
}

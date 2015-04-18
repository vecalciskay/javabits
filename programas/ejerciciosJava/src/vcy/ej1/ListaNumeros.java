package vcy.ej1;

public class ListaNumeros {

	public ListaNumeros(int tamano) {
		arreglo = new int[tamano];
	}

	private int[] arreglo;

	public void colocarAlAzar_maxNumero(int max) {
		for(int i=0; i<arreglo.length; i++) {
			
			arreglo[i] = (int)(Math.random()*(double)max);
		}
	}

	public int contarCuantosHay(int n) {
		int resultado = 0;
		
		for(int i=0; i<arreglo.length; i++) {
			if (arreglo[i] == n)
				resultado++;
		}
		
		return resultado;
	}
}

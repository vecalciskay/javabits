package server.algo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlgoritmoOrdenarQuickSort extends AlgoritmoOrdenar {

	private static final Logger log = LogManager.getLogger();

	@Override
	public void ordenar(int[] lista) {
		log.info("Ordenando con el algoritmo del quicksort");
		
		quicksort(lista, 0, lista.length - 1);
	}

	private void quicksort(int[] lista, int izq, int der) {
		int i = izq;
		int j = der;
		int pivote = lista[(izq + der) / 2];
		do {
			while (lista[i] < pivote)
				i++;

			while (lista[j] > pivote)
				j--;

			if (i <= j) {
				int aux = lista[i];
				lista[i] = lista[j];
				lista[j] = aux;

				i++;
				j--;
			}
		} while (i <= j);
		if (izq < j)
			quicksort(lista, izq, j);
		if (der > i)
			quicksort(lista, i, der);
	}
}

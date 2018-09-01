package server.algo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlgoritmoOrdenarSimple extends AlgoritmoOrdenar {

	private static final Logger log = LogManager.getLogger();
	
	@Override
	public void ordenar(int[] lista) {
		log.info("Ordenando con el algoritmo simple");
		for (int i = 0; i < lista.length - 1; i++) {
			for (int j = i + 1; j < lista.length; j++) {
				if (lista[i] >= lista[j]) {
					int aux = lista[i];
					lista[i] = lista[j];
					lista[j] = aux;
				}
			}
		}
	}
}

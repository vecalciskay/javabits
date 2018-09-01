package server.algo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlgoritmoOrdenarQuickSort extends AlgoritmoOrdenar {

	private static final Logger log = LogManager.getLogger();
	
	@Override
	public void ordenar(int[] lista) {
		log.info("Ordenando con el algoritmo de quicksort");
	}

}

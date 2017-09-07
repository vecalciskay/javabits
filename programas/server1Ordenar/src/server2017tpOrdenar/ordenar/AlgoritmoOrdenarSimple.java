package server2017tpOrdenar.ordenar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AlgoritmoOrdenarSimple extends AlgoritmoOrdenar {

	private static Logger logger = LogManager.getRootLogger();
	
	@Override
	public void ordenar(int[] arreglo) {

		logger.info("Se ordenara el arreglo de manera simple (" + arreglo.length + " elementos)");
		for (int i = 0; i < arreglo.length - 1; i++) {
			for (int j = i+1; j < arreglo.length; j++) {
				if (arreglo[i] > arreglo[j]) {
					int aux = arreglo[i];
					arreglo[i] = arreglo[j];
					arreglo[j] = aux;
				}
			}
		}
		
		
	}

}

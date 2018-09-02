package server.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.algo.AlgoritmoOrdenar;
import server.algo.AlgoritmoOrdenarQuickSort;
import server.algo.AlgoritmoOrdenarSimple;
import server.stat.MensajeTipo;
import server.stat.MensajeTipo.Tipo;

/**
 * La clase del Servicio ORDENAR. Esta clase debe implementar el protocolo
 * indicado obedeciendo a los metodos abstractos de la superclase.
 * 
 * @author Vladimir
 *
 */
public class ServicioOrdenar extends Servicio {

	private static final String CMD_HOLA = "HOLA";
	private static final String CMD_OK = "OK";
	private static final String CMD_LISTA = "LISTA";
	private static final String CMD_ORDENAR = "ORDENAR";
	private static final String CMD_SIMPLE = "SIMPLE";
	private static final String CMD_QUICKSORT = "QUICKSORT";

	private static final Logger log = LogManager.getLogger();

	private int[] lista;

	@Override
	public void configurar(Socket clt) throws IOException {
		super.configurar(clt);

		clienteOutput = new PrintWriter(clt.getOutputStream(), true);
	}

	/**
	 * El protocolo de este servicio es el siguiente: -CLIENT- HOLA -SERVER- OK
	 * -CLIENT- LISTA 4,7,3,... -SERVER- OK [CANT] -CLIENT- ORDENAR
	 * (SIMPLE|QUICKSORT) -SERVER- OK 1,2,....
	 */
	@Override
	public void servir() {

		try {
			// Lee HOLA
			String linea = leer();

			if (!linea.endsWith(CMD_HOLA)) {
				log.error("No coloco " + CMD_HOLA + " primero");
				enviarError("Comando HOLA primero", true);
				return;
			}

			escribir(CMD_OK);

			// Lee LISTA
			boolean faltaLista = true;
			while (faltaLista) {
				linea = leer();

				if (!linea.startsWith(CMD_LISTA)) {
					log.error("No coloco " + CMD_LISTA + " con numeros despues");
					enviarError("Comando LISTA unicamente de la forma LISTA a1,a2,a3,... donde aX son enteros", false);
					continue;
				}

				String numeros = linea.substring(CMD_LISTA.length()).trim();

				int cantidadNumeros = numerosCorrectos(numeros);
				if (cantidadNumeros <= 0) {
					log.error("La lista de numeros no es correcta " + numeros);
					enviarError("Los números deben ser enteros separados por coma sin espacios entre ellos", false);
					continue;
				}

				crearListaNumeros(numeros, cantidadNumeros);
				escribir(CMD_OK + " " + cantidadNumeros);

				faltaLista = false;
			}

			// Lee algoritmo
			boolean faltaAlgoritmo = true;
			while (faltaAlgoritmo) {
				linea = leer();

				if (!linea.startsWith(CMD_ORDENAR)) {
					log.error("No coloco " + CMD_ORDENAR + " con el algoritmo despues");
					enviarError("Comando ORDENAR unicamente de la forma ORDENAR SIMPLE|QUICKSORT", false);
					continue;
				}

				String algoritmo = linea.substring(CMD_ORDENAR.length()).trim();

				AlgoritmoOrdenar objAlgoritmo = crearAlgoritmoOrdenar(algoritmo);
				if (objAlgoritmo == null) {
					log.error("El algoritmo pasado en parametro no es correcto " + algoritmo);
					enviarError("El algoritmo despues de ORDENAR debe ser o SIMPLE o QUICKSORT", false);
					continue;
				}

				objAlgoritmo.ordenar(lista);
				enviarLista();

				faltaAlgoritmo = false;
			}

			cerrar();
			log.info("Se cierra el cliente");

		} catch (IOException e) {
			log.error("Error de lectura en la entrada", e);
			cerrar();
		}
	}

	private void enviarLista() {
		StringBuilder msg = new StringBuilder(CMD_OK + " ");
		String separador = "";
		for (int i = 0; i < lista.length; i++) {
			msg.append(separador).append(lista[i]);
			separador = ",";
		}
		escribir(msg.toString());
	}

	private AlgoritmoOrdenar crearAlgoritmoOrdenar(String algoritmo) {
		if (algoritmo.equals(CMD_SIMPLE))
			return new AlgoritmoOrdenarSimple();
		if (algoritmo.equals(CMD_QUICKSORT))
			return new AlgoritmoOrdenarQuickSort();

		return null;
	}

	private int numerosCorrectos(String numeros) {
		String patternString = "^([0-9]+,)*([0-9]+)$";

		if (!numeros.matches(patternString))
			return -1;
		
		String[] numerosString = numeros.split(",");

		return numerosString.length;
	}

	private void crearListaNumeros(String numeros, int cantidad) {
		lista = new int[cantidad];
		StringTokenizer st = new StringTokenizer(numeros, ",");

		int i = 0;
		while (st.hasMoreTokens()) {
			lista[i] = Integer.parseInt(st.nextToken());
			i++;
		}
	}

	private void enviarError(String msg, boolean cerrarSocket) {
		escribir(msg);
		
		if (cerrarSocket) {
			cerrar();
		}
	}
}

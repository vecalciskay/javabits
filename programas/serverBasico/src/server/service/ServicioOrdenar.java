package server.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.algo.AlgoritmoOrdenar;
import server.algo.AlgoritmoOrdenarQuickSort;
import server.algo.AlgoritmoOrdenarSimple;

/**
 * La clase del Servicio ORDENAR. Esta clase debe implementar el protocolo indicado 
 * obedeciendo a los metodos abstractos de la superclase.
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
		
		clienteOutput = new PrintWriter(clt.getOutputStream());
	}
	
	/**
	 * El protocolo de este servicio es el siguiente:
	 * -CLIENT- HOLA
	 * -SERVER- OK
	 * -CLIENT- LISTA 4,7,3,...
	 * -SERVER- OK [CANT]
	 * -CLIENT- ORDENAR (SIMPLE|QUICKSORT)
	 * -SERVER- OK 1,2,....
	 */
	@Override
	public void servir() {
		
		try {
			// Lee HOLA
			String linea = "";
			linea = clienteInput.readLine();
			log.info("-CLIENTE- " + linea);
			
			if (linea != CMD_HOLA)
			{
				log.error("No coloco " + CMD_HOLA + " primero");
				enviarError("Comando HOLA primero", true);
				return;
			}
			
			clienteOutput.println(CMD_OK);
			log.info("-SERVER- OK");
			
			// Lee LISTA
			boolean faltaLista = true;
			while(faltaLista) {
				linea = clienteInput.readLine();
				log.info("-CLIENTE- " + linea);
				
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
				clienteOutput.println(CMD_OK + " " + cantidadNumeros);
				log.info("-SERVER- " + CMD_OK + " " + cantidadNumeros);
				
				faltaLista = false;
			}
			
			// Lee algoritmo
			boolean faltaAlgoritmo = true;
			while(faltaAlgoritmo) {
				linea = clienteInput.readLine();
				log.info("-CLIENTE- " + linea);
				
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
		clienteOutput.println(msg);
		log.info("-SERVER- " + msg);
	}

	private AlgoritmoOrdenar crearAlgoritmoOrdenar(String algoritmo) {
		if (algoritmo == CMD_SIMPLE)
			return new AlgoritmoOrdenarSimple();
		if (algoritmo == CMD_QUICKSORT)
			return new AlgoritmoOrdenarQuickSort();
		
		return null;
	}

	private int numerosCorrectos(String numeros) {
		
		return 1;
	}

	private void crearListaNumeros(String numeros, int cantidad) {
		lista= new int[cantidad];
		StringTokenizer st = new StringTokenizer(numeros,",");
		
		int i=0;
		while(st.hasMoreTokens()) {
			lista[i] = Integer.parseInt(st.nextToken());
			i++;
		}
	}

	private void enviarError(String msg, boolean cerrarSocket) {
		clienteOutput.println(msg);
		clienteOutput.flush();
		
		if (cerrarSocket) {
			cerrar();
		}
	}

	private void cerrar() {
		clienteOutput.close();
		try {
			clienteInput.close();
		} catch (IOException e) {
			log.error("No se pudo cerrar el stream cliente de entrada");
		}
		try {
			cliente.close();
			log.info("Se cierra el socket y la comunicación con el cliente luego de mensaje");
		} catch (IOException e) {
			log.error("No se pudo cerrar el socket cliente");
		}
	}

}

package server2017tpOrdenar;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import server2017tpOrdenar.ordenar.AlgoritmoOrdenar;
import server2017tpOrdenar.ordenar.AlgoritmoOrdenarSimple;

public class ServicioHttp implements Runnable {

	private static final Logger logger = LogManager.getRootLogger();

	private Socket socketClt;

	private ServerOrdenar padre;

	public ServicioHttp(Socket unCliente, ServerOrdenar padre) {
		socketClt = unCliente;
		this.padre = padre;
	}

	@Override
	public void run() {

		BufferedReader bufIn = null;
		PrintWriter bufOut = null;

		try {
			bufIn = new BufferedReader(new InputStreamReader(socketClt.getInputStream()));
			bufOut = new PrintWriter(socketClt.getOutputStream());
		} catch (Exception e) {
			logger.error("No se pudo crear el socket con el cliente", e);
			return;
		}

		try {
			String line = bufIn.readLine();
			if (line == null)
				return;
			logger.info("<--- " + line);
			while (bufIn.ready()) {
				String otrasLineas = bufIn.readLine();
				logger.info("<--- " + otrasLineas);
			}
			logger.info("Del cliente se leyó la linea del protocolo Http: " + line);
			StringTokenizer st = new StringTokenizer(line);
			String argumento1 = st.nextToken();
			argumento1 = st.nextToken();

			logger.info("Se trata de interpretar el comando " + argumento1);

			if (argumento1.equals(ServerOrdenar.STOP)) {
				this.padre.setPleaseStop(true);
			} else if (argumento1.equals("/ordenado")) {
				crearPaginaHtml(true, bufOut);
			} else if (argumento1.equals("/desordenado")) {
				crearPaginaHtml(false, bufOut);
			} else {
				enviarArchivo(argumento1, bufOut);
			}

		} catch (Exception e) {
			logger.error("No se pudo crear la respuesta para el cliente", e);
		} finally {

			try {
				bufOut.flush();
				bufOut.close();
				bufIn.close();
				socketClt.close();
			} catch (Exception q) {
				;
			}
		}

		return;
	}

	private void enviarArchivo(String argumento1, PrintWriter bufOut) {
		String pathCompleto = this.padre.getDirectorioBase() + argumento1;
		File f = new File(pathCompleto);
		if (f.exists()) {
			try {
				bufOut.println("HTTP/1.1 200 OK");
				bufOut.println();
				// de
				// http://www.java2s.com/Tutorials/Java/java.nio.file/Files/Java_Files_readAllLines_Path_path_Charset_cs_.htm
				Charset charset = Charset.forName("ISO-8859-1");
				Path objPath = Paths.get(f.getAbsolutePath());
				List<String> lineas = Files.readAllLines(objPath, charset);
				logger.info("Se leyeron todas las lineas del archivo " + pathCompleto);

				for (String unaLinea : lineas)
					bufOut.println(unaLinea);

				logger.info("Se escribieron en el socket todas las lineas del archivo " + pathCompleto);

			} catch (Exception e) {
				enviarPaginaError(bufOut);
			}

		} else {
			logger.error("El archivo pedido " + argumento1 + " no existe");
			enviarPaginaError(bufOut);
		}
	}

	private void enviarPaginaError(PrintWriter bufOut) {
		bufOut.println("HTTP/1.1 404 Not Found");
		bufOut.println();
		bufOut.println("<html><head><title>Error</title></head>");
		bufOut.println("<body>No se encuentra el archivo</body></html>");
	}

	private void crearPaginaHtml(boolean ordenado, PrintWriter bufOut) {
		int[] arreglo = new int[100];

		for (int i = 0; i < arreglo.length; i++) {
			arreglo[i] = (int) (Math.random() * 1000.0);
		}

		if (ordenado) {
			AlgoritmoOrdenar algo = new AlgoritmoOrdenarSimple();
			algo.ordenar(arreglo);
		}

		bufOut.println("HTTP/1.1 200 OK");
		bufOut.println();
		bufOut.println("<html><head><title>Arreglo " + (ordenado ? "Ordenado" : "Desordenado") + "</title></head>");
		bufOut.println("<body>");

		bufOut.println("<h1>Arreglo</h1><div>");
		for (int i = 0; i < arreglo.length; i++) {
			bufOut.println(String.valueOf(arreglo[i]) + " ");
			if (i + 1 % 10 == 0)
				bufOut.println("<br />");
		}

		bufOut.println("</body></html>");
	}

	public void brindarServicio() {
		Thread worker = new Thread(this);
		worker.start();
	}

}

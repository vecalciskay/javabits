package server2017tpImagen;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServicioHttp implements Runnable {
	
	private static final Logger logger = LogManager.getRootLogger();

	public static final String CRLF = "\r\n";
	private Socket socketClt;

	private ServerImagen padre;

	public ServicioHttp(Socket unCliente, ServerImagen padre) {
		socketClt = unCliente;
		this.padre = padre;
	}

	@Override
	public void run() {

		BufferedReader bufIn = null;
		DataOutputStream bufOut = null;

		try {
			bufIn = new BufferedReader(new InputStreamReader(socketClt.getInputStream()));
			bufOut = new DataOutputStream(socketClt.getOutputStream());
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

			if (argumento1.equals(ServerImagen.STOP)) {
				this.padre.setPleaseStop(true);
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

	private void enviarArchivo(String argumento1, DataOutputStream out) {
		
		ImagenRecursiva obj = null;
		try {
			obj = ImagenRecursiva.crearImagenRecursiva(argumento1);
		} catch(Exception e) {
			obj = null;
			logger.warn("No se ejecuta el handler de la imagen, se busca el archivo directamente");
		}
		
		if (obj != null) {
			try {
				String archivo = obj.grabarImagen(this.padre.getDirectorioBase());
				logger.info("Se ha grabado el archivo " + archivo + " con la imagen pedida");
			} catch(Exception e) {
				logger.error("No se pudo grabar el archivo " + argumento1, e);
			}
		}
		
		String pathCompleto = this.padre.getDirectorioBase() + argumento1;
		File f = new File(pathCompleto);
		if (f.exists()) {
			try {
				String statusLine = "HTTP/1.1 200 OK";
				String contentType = "Content-Type: " + getMimeType(argumento1);
								
				logger.info("---> " + statusLine);
				logger.info("---> " + contentType);
				
				out.writeBytes(statusLine);
				out.writeBytes(CRLF);
				out.writeBytes(contentType);
				out.writeBytes(CRLF);
				
				// Empieza el archivo aca
				out.writeBytes(CRLF);
				
				sendBytes(f, out);
				
			} catch (Exception e) {
				enviarPaginaError(out);
			}

		} else {
			logger.error("El archivo pedido " + argumento1 + " no existe");
			enviarPaginaError(out);
		}
	}

	private void sendBytes(File f, DataOutputStream out) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		byte[] buffer = new byte[4096];
		int bytes = 0;
		while((bytes = fis.read(buffer)) != -1) {
			out.write(buffer,0, bytes);
			logger.info("---> Se envían " + bytes + " bytes" );
		}
		fis.close();
	}

	private String getMimeType(String argumento1) {
		String extension = argumento1.substring(argumento1.lastIndexOf("."));
		
		switch(extension) {
		case ".gif":
			return "image/gif";
		case ".jpg":
		case ".jpeg":
			return "image/jpeg";
		case ".png":
			return "image/png";
		case ".htm":
		case ".html":
			return "text/html";
		case ".css":
			return "text/css";
		case ".js":
			return "application/javascript";
		default:
			return "application/octet-stream";
		}
		
	}

	private void enviarPaginaError(DataOutputStream out) {
		PrintWriter bufOut = new PrintWriter(out);
		bufOut.println("HTTP/1.1 404 Not Found");
		bufOut.println();
		bufOut.println("<html><head><title>Error</title></head>");
		bufOut.println("<body>No se encuentra el archivo</body></html>");
		bufOut.flush();
		bufOut.close();
	}

	public void brindarServicio() {
		Thread worker = new Thread(this);
		worker.start();
	}

}

package bn.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * El que se conecta comienza primero.
 * @author Vladimir
 *
 */
public class ConexionBatallaNaval {
	
	private static final Logger logger = LogManager.getRootLogger();
	
	private Socket sck;
	private PrintWriter out;
	private BufferedReader in;

	public ConexionBatallaNaval(String ip, int p) throws IOException {
		
		try {
			sck = new Socket(ip, p);
		} catch (IOException e) {
			logger.error("Error al crear la conexión", e);
			throw e;
		}
		
		logger.info("Se conectó a " + ip + ":" + p);
		
		try {
			out = new PrintWriter(new java.io.OutputStreamWriter(sck.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(sck.getInputStream()));
		} catch (IOException e) {
			logger.error("No pudo crear el writer para el socket");
			throw e;
		}
		
		logger.info("Es nuestro turno ahora");
	}
	
	public ConexionBatallaNaval(Socket s) throws IOException {
		sck = s;
		try {
			out = new PrintWriter(new java.io.OutputStreamWriter(sck.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(sck.getInputStream()));
		} catch (IOException e) {
			logger.error("No pudo crear el writer para el socket");
			throw e;
		}
		
		logger.info("Se creo la conexion, es el turno del otro");
	}
	
	public void cerrarConexion() {
		if (sck != null)
			try {
				sck.close();
			} catch (IOException q) {
				logger.warn("No se pudo cerrar la conexión, ver si era porque ya estaba cerrada.", q);
			}
	}

	public Socket getSck() {
		return sck;
	}

	public void setSck(Socket sck) {
		this.sck = sck;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
	public BufferedReader getIn() {
		return in;
	}
	
	public void setIn(BufferedReader in) {
		this.in = in;
	}
}

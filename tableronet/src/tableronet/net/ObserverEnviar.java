package tableronet.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tableronet.modelo.Tablero;
import tableronet.modelo.Trazo;

public class ObserverEnviar implements Observer {
	
	private static final Logger logger = LogManager.getRootLogger(); 
	
	private Socket sck;
	private PrintWriter out;
	
	public ObserverEnviar(String ip, int p) throws IOException {
		
		try {
			sck = new Socket(ip, p);
		} catch (IOException e) {
			logger.error("Error al crear la conexión", e);
			throw e;
		}
		
		logger.info("Se conectó a " + ip + ":" + p);
		
		try {
			out = new PrintWriter(new java.io.OutputStreamWriter(sck.getOutputStream()));
		} catch (IOException e) {
			logger.error("No pudo crear el writer para el socket");
			throw e;
		}
	}
	
	public ObserverEnviar(Socket s) throws IOException {
		sck = s;
		try {
			out = new PrintWriter(new java.io.OutputStreamWriter(sck.getOutputStream()));
		} catch (IOException e) {
			logger.error("No pudo crear el writer para el socket");
			throw e;
		}
		
		logger.info("Se creo el objeto para enviar a partir del socket");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Tablero tab = (Tablero)arg0;
		
		Trazo ultimo = null;
		try {
			ultimo = tab.getMisTrazos().get(0);
		} catch(IndexOutOfBoundsException e) {
			logger.error("No existe trazo para enviar en mis trazos", e);
			return;
		}
		
		if (ultimo.isEnviado())
			return;
		
		out.println(ultimo.toString());
		out.flush();
		
		logger.info(" ---> Enviado el trazo: " + ultimo);
		
		ultimo.setEnviado(true);
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
}

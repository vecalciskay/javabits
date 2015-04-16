package tableronet.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import tableronet.modelo.Tablero;
import tableronet.modelo.Trazo;

public class ThreadRecibir implements Runnable {
	
	private static final Logger logger = LogManager.getRootLogger(); 
	
	private Tablero elTablero;
	private Socket clt;
	private BufferedReader in;

	/**
	 * Creamos todos los objetos a partir de abrir una conexión enel puerto indicado.
	 * @param port
	 * @param t
	 * @throws IOException
	 */
	public ThreadRecibir(int port, Tablero t) throws IOException {
		ServerSocket srv = null;
		clt = null;
		try {
			srv = new ServerSocket(port);
		} catch (IOException e) {
			logger.error("Error al crear la conexión", e);
			throw e;
		}
		
		logger.info("Esperando conexion en el puerto " + port);
		
		try {
			clt = srv.accept();
		} catch (Exception e) {
			logger.error("Error al esperar la conexión", e);
			throw e;
		} finally {
			try {
				srv.close();
			} catch (IOException e) {
				;
			}
		}
		
		logger.info("Se acaban de conectar a este servidor");
		
		in = new BufferedReader(new InputStreamReader(clt.getInputStream()));
		elTablero = t;
	}
	
	public ThreadRecibir(Socket sck, Tablero t) throws IOException {
		clt = sck;
			in = new BufferedReader(new InputStreamReader(clt.getInputStream()));
		elTablero = t;
	}

	/**
	 * Lo unico que hacemos es escuchar indefinidamente por cada mensaje que llegue.
	 */
	@Override
	public void run() {
		try {
			while(!clt.isClosed()) {
				String nuevoTrazo = in.readLine();
				
				if (nuevoTrazo.equals("TEST")) {
					System.out.println(" [RECIBIDO] TEST");
					continue;
				}
				
				logger.info(" <---- Recibido el trazo " + nuevoTrazo);
				
				Trazo t = new Trazo(nuevoTrazo);
				
				elTablero.insertarEnSusTrazos(t);
			}
		} catch (IOException e) {
			logger.error("Error al recibir un trazo", e);
		} catch(Exception e2) {
			logger.error("Error al leer un trazo", e2);
		} finally {
			try {
				clt.close();
			} catch (IOException e) { ; }
		}
	}

	public Tablero getElTablero() {
		return elTablero;
	}

	public void setElTablero(Tablero elTablero) {
		this.elTablero = elTablero;
	}

	public Socket getClt() {
		return clt;
	}

	public void setClt(Socket clt) {
		this.clt = clt;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}
}

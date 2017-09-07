package server2017tpImagen;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ServerImagen {

	private static Logger logger = LogManager.getRootLogger();
	
	public static final int PUERTO = 3232;
	public static final String DIR_BASE = "D:/Temp/server";
	public static final String STOP = "/STOP";
	private int puerto;

	private ServerSocket socketSrv;
	private boolean pleaseStop;

	private String directorioBase;

	public ServerImagen(int puerto2, String carpeta) {
		this.puerto = puerto2;
		this.directorioBase = carpeta; 
		pleaseStop = false;
	}

	public static void main(String[] args) {
		String resource = "/auditoria.properties";
		URL configFileResource;
		configFileResource = ServerImagen.class.getResource(resource);
		PropertyConfigurator.configure(configFileResource);
		
		ServerImagen srv = new ServerImagen(PUERTO, DIR_BASE);
		srv.comenzar();
	}

	private void comenzar() {
		logger.info("Comienza el servidor web en el puerto " + this.puerto);
		
		try {
			socketSrv = new ServerSocket(puerto);
		} catch(Exception q) {
			logger.error("No se pudo crear el socket del servidor", q);
			return;
		}
		
		while(!pleaseStop) {
			Socket unCliente = null;
			
			try {
				unCliente = socketSrv.accept();
				if (pleaseStop)
					throw new Exception("Ya se mandó parar el servidor");
			} catch (Exception e) {
				logger.error("No se pudo conectar correctamente al cliente, hace caer la conexión, cliente debe intentar de nuevo", e);
				continue;
			} 
			
			ServicioHttp servicioAlCliente = new ServicioHttp(unCliente, this);
			servicioAlCliente.brindarServicio();
		}
		
		try {
			socketSrv.close();
			logger.info("El socket del servidor fue cerrado");
		} catch (IOException e) {
			;
		}
	}

	public int getPuerto() {
		return puerto;
	}

	public String getDirectorioBase() {
		return directorioBase;
	}

	public void setPleaseStop(boolean b) {
		pleaseStop = b;
	}
}

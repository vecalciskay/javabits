package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.conf.Configuracion;
import server.exception.ServerException;
import server.net.Servidor;

public class ServidorMain {
	
	private static final Logger log = LogManager.getLogger();

	public static void main(String[] args) {

		Servidor srv = null;
		
		try {
			srv = new Servidor(Configuracion.getOrCreate());
		} catch (ServerException e) {
			log.error("Error al comenzar: " + e.getMessage());
			return;
		}
		
		try {
			srv.comenzar();
		} catch (ServerException e) {
			log.error("Error al comenzar el servidor", e);
		}

	}

}

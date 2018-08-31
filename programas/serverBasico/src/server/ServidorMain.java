package server;

import server.conf.Configuracion;
import server.exception.ServerException;
import server.net.Servidor;

public class ServidorMain {

	public static void main(String[] args) {

		Servidor srv = null;
		
		try {
			srv = new Servidor(Configuracion.getOrCreate());
		} catch (ServerException e) {
			System.out.println("Error al comenzar: " + e.getMessage());
			return;
		}
		
		try {
			srv.comenzar();
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

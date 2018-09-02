package server.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.conf.Configuracion;
import server.net.Servidor;

public class ServicioComando extends Servicio {

	private static final Logger log = LogManager.getLogger();

	private Servidor objServidor;

	public ServicioComando(Servidor srv) {
		this.objServidor = srv;
	}

	@Override
	public void servir() {
		Configuracion conf = Configuracion.getOrCreate();

		try {
			String linea = leer();
			if (linea.endsWith(conf.getComandoFinalizar())) {
				cmdFinalizar();
			}
			if (linea.endsWith(conf.getComandoEstadisticas())) {
				cmdEstadisticas();
			}
		} catch (Exception e) {
			log.error("Error al dar el servicio de comando ", e);
		}
	}

	private void cmdEstadisticas() {
		String msg = objServidor.getStats().toString();
		escribir(msg);
	}

	@Override
	public void configurar(Socket clt) throws IOException {
		super.configurar(clt);

		clienteOutput = new PrintWriter(clt.getOutputStream(), true);
	}

	public void cmdFinalizar() {
		cerrar();
		objServidor.parar();
	}
}

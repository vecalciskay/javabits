package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import server.conf.Configuracion;

public class FinalizarServidor {

	public static void main(String[] args) throws UnknownHostException, IOException {

		Configuracion conf = Configuracion.getOrCreate();
		
		Socket clt = new Socket("127.0.0.1",conf.getPuertoComando());
		
		PrintWriter out = new PrintWriter(clt.getOutputStream(), true);
		
		out.println(conf.getComandoFinalizar());
		
		out.close();
		clt.close();
	}

}

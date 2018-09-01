package server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Servicio implements Runnable {
	
	protected Socket cliente;
	protected BufferedReader clienteInput;
	protected PrintWriter clienteOutput;
	
	public static Servicio construirServicio(String nombre) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Servicio resultado = (Servicio) (Class.forName("server.service.Servicio" + nombre).newInstance());
		return resultado;
	}

	public void configurar(Socket clt) throws IOException {
		this.cliente = clt;
		this.clienteInput = new BufferedReader(new InputStreamReader(clt.getInputStream()));
	}
	
	public void run() {
		servir();
	}

	public abstract void servir() ;
}

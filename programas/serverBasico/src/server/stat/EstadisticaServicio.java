package server.stat;

import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.stat.MensajeTipo.Tipo;

public class EstadisticaServicio implements Observer {
	
	private static final Logger log = LogManager.getLogger();
	
	private int idServicio;
	private int bytesEntrantes;
	private int bytesSalientes;

	public EstadisticaServicio(int id) {
		idServicio = id;
	}

	public int getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}

	public int getBytesEntrantes() {
		return bytesEntrantes;
	}

	public void setBytesEntrantes(int bytesEntrantes) {
		this.bytesEntrantes = bytesEntrantes;
	}

	public int getBytesSalientes() {
		return bytesSalientes;
	}

	public void setBytesSalientes(int bytesSalientes) {
		this.bytesSalientes = bytesSalientes;
	}
	
	public String toString() {
		return "Estadistica servicio " + idServicio + ": Entrada " + bytesEntrantes + " / Salida " + bytesSalientes;
	}

	@Override
	public void update(Observable arg0, Object msgTipoSinCast) {
		if (!(msgTipoSinCast instanceof MensajeTipo)) {
			log.error("El objeto del observer en update no es mensaje tipo, es " + msgTipoSinCast.toString());
			return;
		}
		
		MensajeTipo msgTipo = (MensajeTipo)msgTipoSinCast;
		int cantidadBytes = msgTipo.getMensajeLength();
		if (cantidadBytes <= 0) 
			cantidadBytes = msgTipo.getMensaje().length();
		
		if (msgTipo.getTipo() == Tipo.ENTRADA) {
			bytesEntrantes += cantidadBytes;
			log.debug("Se suman " + cantidadBytes + " a los bytes de entrada de la estadistica " + idServicio);
		}
		if (msgTipo.getTipo() == Tipo.SALIDA) {	
			bytesSalientes += cantidadBytes;
			log.debug("Se suman " + cantidadBytes + " a los bytes de salida de la estadistica " + idServicio);
		}
	}
}

package server.stat;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.service.Servicio;

public class EstadisticaServidor {
	
	private static final Logger log = LogManager.getLogger();

	private HashMap<Integer,EstadisticaServicio> servicios;
	
	public EstadisticaServidor() {
		servicios = new HashMap<Integer,EstadisticaServicio>();
	}
	
	public void nuevaEstadisticaServicio(Servicio objServicio) {
		log.info("Se engancha el observer de la estadistica al servicio con id " + objServicio.getId());
		EstadisticaServicio objStat = new EstadisticaServicio(objServicio.getId());
		objServicio.addObserver(objStat);
		
		log.info("Se añade la estadistica servicio " + objServicio.getId() + " a la lista de estadisticas");
		servicios.put(new Integer(objServicio.getId()), objStat);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Estadisticas\n");
		
		for(EstadisticaServicio statClt : servicios.values()) {
			result.append(statClt.toString()).append("\n");
		}
		
		return result.toString();
	}
}

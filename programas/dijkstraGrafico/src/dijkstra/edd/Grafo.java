package dijkstra.edd;

import java.util.HashMap;
import java.util.Observable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dijkstra.edd.coord.Coordenada;

public class Grafo<E> extends Observable {
	
	private static final Logger log = LogManager.getRootLogger();

	protected HashMap<String, Nodo<E>> nodos;
	
	public Grafo() {
		nodos = new HashMap<String, Nodo<E>>();
	}
	
	
	public Pila<String> caminoMasCorto(String origen, String destino) throws Exception {
		
		if (origen == null || destino == null)
			throw new Exception("Ni el origen ni el destino pueden ser nulos");
		
		if (!nodos.containsKey(origen) || !nodos.containsKey(destino))
			throw new Exception("Ya sea el origen [" + origen + "] o el destino [" + destino + "] no existen");
		
		HashMap<String,Double> distancias = new HashMap<String, Double>();
		HashMap<String,String> anterior = new HashMap<String, String>();
		CadenaOrdenada<IdDistancia> porAnalizar = new CadenaOrdenada<IdDistancia>();
		
		//-------------- Inicializacion-----------------
		for(Nodo<E> n : nodos.values()) {
			distancias.put(n.getId(), Double.MAX_VALUE);
			anterior.put(n.getId(), "");
			
			IdDistancia objDist = new IdDistancia(n.getId(), Double.MAX_VALUE);
			porAnalizar.insertar(objDist);
		}
		
		distancias.put(origen, 0.0);
		
		//----------------------------------------------
		
		porAnalizar = ordenarPorDistancia(distancias, porAnalizar);
		
		while(!porAnalizar.estaVacia()) {
			
			String id = porAnalizar.obtener(0).getId();
			porAnalizar.eliminar(0);
			
			if (id.equals(destino)) {
				log.info("Encontramos a " + destino + " formamos ahora el camino");
				return formarCaminoConAnterior(anterior, origen, destino);
			}
			
			log.debug("Se analizan los vecinos de " + id);
			nodos.get(id).getSalientes().values().forEach(a -> {
				Nodo<E> vecino = a.getA();
				double alt = distancias.get(id) + a.getPeso();
				double distanciaAnterior = distancias.get(vecino.getId());
				
				if (alt < distanciaAnterior) {
					distancias.put(vecino.getId(), alt);
					anterior.put(vecino.getId(), id);
				}
			});
			
			porAnalizar = ordenarPorDistancia(distancias, porAnalizar);
		}
		 
		log.info("No encontramos a " + destino + " en la componente conexa de este grafo");
		return null;
	}
	
	private Pila<String> formarCaminoConAnterior(HashMap<String, String> anterior, 
			String origen, String destino) {
		Pila<String> result = new Pila<String>();
		
		String unoAnterior = destino;
		while(!unoAnterior.equals(origen)) {
			result.push(unoAnterior);
			
			unoAnterior = anterior.get(unoAnterior);
		}
		
		result.push(unoAnterior);
		
		return result;
	}


	private CadenaOrdenada<IdDistancia> ordenarPorDistancia(
			HashMap<String, Double> distancias, 
			CadenaOrdenada<IdDistancia> porAnalizar) {
		
		CadenaOrdenada<IdDistancia> result = new CadenaOrdenada<>();
		
		for(IdDistancia obj : porAnalizar) {
			obj.setDistancia(distancias.get(obj.getId()));
			result.insertar(obj);
		}
		return result;
	}

	public void nodo(String id, E o) {
		
		if (nodos.containsKey(id))
			return;
		
		Nodo<E> nuevo = new Nodo<E>(id, o);
		nodos.put(id,  nuevo);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void arco(String de, String a, double w, boolean deIdaYVuelta) throws Exception {
		
		if (!nodos.containsKey(de) || !nodos.containsKey(a))
			throw new Exception("No se encuentra el nodo " + de + " o el nodo " + a + " en este grafo");
		
		Nodo<E> origen = nodos.get(de);
		Nodo<E> destino = nodos.get(a);
		
		// actualiza el peso solamente
		if (origen.getSalientes().containsKey(a)) {
			Arco<E> objArco = origen.getSalientes().get(a);
			
			if (w == objArco.getPeso())
				return;
			
			objArco.setPeso(w);
			log.debug("Se actualizo el peso del arco entre " + de + " y " + a);
		} else {
			Arco<E> objArco = new Arco<E>(origen, destino, w);
			origen.getSalientes().put(a, objArco);
			
			if (deIdaYVuelta) {
				objArco = new Arco<E>(destino, origen, w);
				destino.getSalientes().put(de, objArco);
			}
			
			log.debug("Se creo un nuevo arco entre " + de + " y " + a);
			
		}
		this.setChanged();
		this.notifyObservers();
		
	}

	public static class IdDistancia implements Comparable<IdDistancia> {
		
		protected String id;
		protected double distancia;
		
		public IdDistancia(String id, double dist) {
			this.id = id;
			this.distancia = dist;
		}
		
		public String getId() {
			return id;
		}
		public double getDistancia() {
			return distancia;
		}
		
		public void setDistancia(double distancia) {
			this.distancia = distancia;
		}

		@Override
		public int compareTo(IdDistancia o) {
			if (distancia < o.getDistancia())
				return -1;
			if (distancia > o.getDistancia())
				return 1;
			return 0;
		}
		
	}
	
	public static class Nodo<E> {
		protected HashMap<String, Arco<E>> salientes;
		protected E contenido;
		protected String id;
		
		public Nodo(String id, E obj) {
			this.id = id;
			this.contenido = obj;
			salientes = new HashMap<String, Arco<E>>();
		}
		
		public String getId() {
			return id;
		}
		public HashMap<String, Arco<E>> getSalientes() {
			return salientes;
		}
		public E getContenido() {
			return contenido;
		}

		public void setContenido(E c) {
			this.contenido = c;
		}
	}
	
	public static class Arco<E> {
		protected Nodo<E> de;
		protected Nodo<E> a;
		protected double peso;
		
		public Arco(Nodo<E> de, Nodo<E> a, double w) {
			this.de = de;
			this.a = a;
			this.peso = w;
		}
		
		public Nodo<E> getA() {
			return a;
		}
		public void setA(Nodo<E> a) {
			this.a = a;
		}
		
		public Nodo<E> getDe() {
			return de;
		}
		public void setDe(Nodo<E> de) {
			this.de = de;
		}
		
		public double getPeso() {
			return peso;
		}
		public void setPeso(double peso) {
			this.peso = peso;
		}
	}

}

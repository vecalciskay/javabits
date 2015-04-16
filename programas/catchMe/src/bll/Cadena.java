package bll;

import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Estrutura de datos de cadena. Estructura dinamca lineal.
 *  
 * @author vladimir
 *
 * @param <T>
 */
public class Cadena<T> {
	
	private static final Logger logger = Logger.getRootLogger();
	
	/**
	 * Nodo de la cadena dinamica
	 * 
	 * @author vladimir
	 *
	 * @param <E>
	 */
	class Nodo<E> {
		private Nodo<E> siguiente;
		private E contenido;
		
		public Nodo(E c) {
			contenido = c;
			siguiente = null;
		}

		public Nodo<E> getSiguiente() {
			return siguiente;
		}

		public void setSiguiente(Nodo<E> siguiente) {
			this.siguiente = siguiente;
		}

		public E getContenido() {
			return contenido;
		}

		public void setContenido(E contenido) {
			this.contenido = contenido;
		}
	}
	
	/**
	 * Iterador para la cadena
	 * 
	 * @author vladimir
	 *
	 * @param <T>
	 */
	class IteratorCadena<M> implements Iterator<M> {
		
		private Nodo<M> actual;
		
		public IteratorCadena(Nodo<M> raiz) {
			actual = raiz;
		}

		@Override
		public boolean hasNext() {
			return (actual != null);
		}

		@Override
		public M next() {
			M obj = actual.getContenido();
			actual = actual.getSiguiente();
			return obj;
		}

		@Override
		public void remove() {
			return;
		}
		
	}

	private Nodo<T> raiz;
	
	public Cadena() {
		raiz = null;
	}
	
	public void insert(T c) throws Exception {
		if (c == null) {
			throw new Exception("Cannot insert null");
		}
		
		logger.debug("Crea el nodo con " + c.toString());
		Nodo<T> n = new Nodo<T>(c);
		
		n.setSiguiente(raiz);
		raiz = n;
	}

	public int size() {
		int res = 0;
		Nodo<T> actual = raiz;
		while(actual != null) {
			res++;
			actual = actual.getSiguiente();
		}
		return res;
	}
	
	public void remove(T c) throws Exception {
		if (raiz == null)
			throw new Exception("CAdena esta vacia");
		
		if (raiz.getContenido() == c) {
			logger.debug("Eliminando " + c.toString() + " de la cadena cuando esta al inicio");
			raiz = raiz.getSiguiente();
			return;
		}		
		
		Nodo<T> actual = raiz;
		while(actual.getSiguiente() != null) {
			if (actual.getSiguiente().getContenido() == c) {
				logger.debug("Eliminando " + c.toString() + " de la cadena");
				actual.setSiguiente(actual.getSiguiente().getSiguiente());
				break;
			}
			actual = actual.getSiguiente();
		}

		throw new Exception("No encontro el objeto " + c.toString() + "en la cadena");
	}
	
	public Iterator<T> iterator() {
		return new IteratorCadena<T>(raiz);
	}

	public Nodo<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo<T> raiz) {
		this.raiz = raiz;
	}
}

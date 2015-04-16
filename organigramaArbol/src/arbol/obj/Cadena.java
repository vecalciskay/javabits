package arbol.obj;

import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Cadena<T> {

	private static final Logger logger = LogManager.getRootLogger();
	
	private Nodo<T> raiz;
	
	public Cadena() {
		raiz = null;
	}
	
	public void insertar(T obj) {
		if (raiz == null) {
			raiz = new Nodo<T>(obj);
			return;
		}
		
		Nodo<T> nuevo = new Nodo<T>(obj);
		nuevo.setSiguiente(raiz);
		raiz = nuevo;
	}
	
	public Iterator<T> iterator() {
		return new Iterador<T>(raiz);
	}
	
	class Nodo<E> {
		
		private Nodo<E> siguiente;
		private E contenido;

		public Nodo(E obj) {
			contenido = obj;
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
	
	class Iterador<M> implements Iterator<M> {

		private Nodo<M> actual;
		
		public Iterador(Nodo<M> r) {
			actual = r;
		}
		
		@Override
		public boolean hasNext() {
			return actual != null;
		}

		@Override
		public M next() {
			M respuesta = actual.getContenido();
			actual = actual.getSiguiente();
			return respuesta;
		}

		@Override
		public void remove() {
			
		}
		
	}

	public boolean vacia() {
		return raiz == null;
	}

	public int tamano() {
		int resultado = 0;
		Nodo<T> actual = raiz;
		while(actual != null) {
			actual = actual.getSiguiente();
			resultado++;
		}
		return resultado;
	}

	public Nodo<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo<T> raiz) {
		this.raiz = raiz;
	}

	public T get(int i) {
		if (raiz == null)
			throw new IndexOutOfBoundsException();
		
		if (i == 0)
			return raiz.getContenido();
		
		int resultado = 0;
		Nodo<T> actual = raiz;
		while(actual != null && resultado < i) {
			actual = actual.getSiguiente();
			resultado++;
		}
		if (resultado == i)
			return actual.getContenido();
		
		logger.error("Se busco la posicion " + i + " pero antes la cadena es nula");
		
		throw new IndexOutOfBoundsException();
	}
}
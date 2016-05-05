package arbol.obj;

import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Cadena<T> implements Iterable<T> {

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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (T o : this) {
			sb.append(o + " -> ");
		}
		return sb.toString();
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

		public boolean moverAPosicionCorrecta() {
			if (!(contenido instanceof Comparable))
				return false;

			if (this.getSiguiente() == null)
				return false;

			Comparable<E> obj = (Comparable<E>) contenido;
			Nodo<E> nodoAComparar = this.getSiguiente();

			// Si solamente hay dos nodos
			// ----------------------------
			if (nodoAComparar.getSiguiente() == null) {

				if (obj.compareTo(nodoAComparar.getContenido()) > 0) {
					E aux = contenido;
					contenido = nodoAComparar.getContenido();
					nodoAComparar.setContenido(aux);

					return true;
				}
				return false;
			}
			nodoAComparar = this;

			while (nodoAComparar.getSiguiente() != null
					&& (obj.compareTo(nodoAComparar.getSiguiente()
							.getContenido()) >= 0)) {
				nodoAComparar = nodoAComparar.getSiguiente();
			}

			// Llegó al final
			if (nodoAComparar.getSiguiente() == null) {
				if (obj.compareTo(nodoAComparar.getContenido()) >= 0) {
					E aux = contenido;
					contenido = nodoAComparar.getContenido();
					nodoAComparar.setContenido(aux);

					return true;
				}
				return false;
			}

			if (this != nodoAComparar &&
					obj.compareTo(nodoAComparar.getContenido()) > 0) {
				E aux = contenido;
				contenido = nodoAComparar.getContenido();
				nodoAComparar.setContenido(aux);
				return true;
			}
			return false;
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
		while (actual != null) {
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
		while (actual != null && resultado < i) {
			actual = actual.getSiguiente();
			resultado++;
		}
		if (resultado == i)
			return actual.getContenido();

		logger.error("Se busco la posicion " + i
				+ " pero antes la cadena es nula");

		throw new IndexOutOfBoundsException();
	}

	public void sort() {
		Nodo<T> pivot = raiz;

		while (pivot != null) {
			boolean seMovio = pivot.moverAPosicionCorrecta();
			if (seMovio) {
				pivot = raiz;
				System.out.println(this);
			}
			else
				pivot = pivot.getSiguiente();
		}
	}
}
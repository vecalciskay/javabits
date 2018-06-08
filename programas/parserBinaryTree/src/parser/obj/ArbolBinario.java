package parser.obj;

import java.util.Observable;

public class ArbolBinario<T> extends Observable {
	
	private Nodo<T> raiz;
	
	public ArbolBinario() {
		raiz = null;
	}
	
	public String toString() {
		if (raiz == null)
			return "[]";
		
		return raiz.toString();
	}

	public Nodo<T> getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo<T> raiz) {
		this.raiz = raiz;
	}
	
	public void setChangedAndNotify() {
		this.setChanged();
		this.notifyObservers();
	}

	public static class Nodo<E> {
		private E contenido;
		private Nodo<E> izquierda;
		private Nodo<E> derecha;
		private Nodo<E> padre;
		
		public Nodo(E c) {
			this.contenido = c;
		}

		public E getContenido() {
			return contenido;
		}

		public void setContenido(E contenido) {
			this.contenido = contenido;
		}

		public Nodo<E> getIzquierda() {
			return izquierda;
		}

		public void setIzquierda(Nodo<E> izquierda) {
			this.izquierda = izquierda;
			if (izquierda != null)
				izquierda.setPadre(this);
		}

		public Nodo<E> getDerecha() {
			return derecha;
		}

		public void setDerecha(Nodo<E> derecha) {
			this.derecha = derecha;
			if (derecha != null)
				derecha.setPadre(this);
		}
		
		public Nodo<E> getPadre() {
			return padre;
		}

		public void setPadre(Nodo<E> padre) {
			this.padre = padre;
		}
		
		public Nodo<E> addDerecha(E obj) {
			Nodo<E> nuevo = new Nodo<E>(obj);
			derecha = nuevo;
			return derecha;
		}
		
		public Nodo<E> addIzquierda(E obj) {
			Nodo<E> nuevo = new Nodo<E>(obj);
			izquierda = nuevo;
			return izquierda;
		}
		
		public String toString() {
			if (izquierda == null && derecha == null) 
				return contenido.toString();
			
			return "(" + 
				(izquierda == null ? "X" : izquierda.toString()) + 
				contenido.toString() + 
				(derecha == null ? "X" : derecha.toString()) +
				")";
		}
	}
}

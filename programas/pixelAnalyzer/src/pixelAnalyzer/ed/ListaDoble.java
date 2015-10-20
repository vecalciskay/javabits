package pixelAnalyzer.ed;

import java.util.Iterator;

public class ListaDoble<E> implements Iterable<E> {

	@SuppressWarnings("hiding")
	class Recipiente<E> {
		private E contenido;
		private Recipiente<E> siguiente;
		private Recipiente<E> anterior;
		
		public Recipiente(E o) {
			contenido = o;
			siguiente = null;
			anterior = null;
		}

		public E getContenido() {
			return contenido;
		}

		public void setContenido(E contenido) {
			this.contenido = contenido;
		}

		public Recipiente<E> getSiguiente() {
			return siguiente;
		}

		public void setSiguiente(Recipiente<E> siguiente) {
			this.siguiente = siguiente;
		}

		public Recipiente<E> getAnterior() {
			return anterior;
		}

		public void setAnterior(Recipiente<E> anterior) {
			this.anterior = anterior;
		}
	}
	
	@SuppressWarnings("hiding")
	class IteradorDoble<E> implements Iterator<E> {

		private Recipiente<E> actual;

		public IteradorDoble(Recipiente<E> o) {
			actual = o;
		}
		
		@Override
		public boolean hasNext() {
			return actual != null;
		}

		@Override
		public E next() {
			E resultado = actual.getContenido();
			actual = actual.getSiguiente();
			return resultado;
		}

		@Override
		public void remove() {
			;
		}
		
	}
	
	private Recipiente<E> inicio;
	
	public ListaDoble() {
		inicio = null;
	}
	
	public ListaDoble<E> insertar(E o) {
		
		Recipiente<E> nuevo = new Recipiente<E>(o);
		
		if (inicio == null)
		{
			inicio = nuevo;
			return this;
		}
		
		inicio.setAnterior(nuevo);
		nuevo.setSiguiente(inicio);
		inicio = nuevo;
		
		return this;
	}
	
	public int buscar(E o) {
		
		if (o == null)
			return -1;
		
		int res = 0;
		
		for(E obj:this) {
			if (obj.equals(o)) 
				return res;
			
			res++;
		}
		return -1;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new IteradorDoble<E>(inicio);
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		if (inicio == null) return "[]";
		
		for(E obj:this) {
			res.append("[").append(obj).append("]-->");
		}
		
		return res.toString();
	}

	public void eliminar(int pos) {
		Recipiente<E> actual = inicio;
		int i = 0;
		
		if (tamano() == 1) {
			if (pos == 0) {
			inicio = null;
			return;
			} else {
				
				throw new IndexOutOfBoundsException("Estructura tiene solamente 1 elemento");
			}
		}
		
		while(actual != null && i < pos) {
			actual = actual.getSiguiente();
			i++;
		}
		
		if (actual != null && i == pos) {
			Recipiente<E> anteriorDeActual = actual.getAnterior();
			Recipiente<E> siguienteDeActual = actual.getSiguiente();
			
			if (anteriorDeActual != null) {
				anteriorDeActual.setSiguiente(siguienteDeActual);
			}
			
			if (siguienteDeActual != null) {
				siguienteDeActual.setAnterior(anteriorDeActual);
			}
		} else {
			throw new IndexOutOfBoundsException("No hay elemento en la posición " + pos);
		}
	}

	private int tamano() {
		int r = 0;
		for(E o : this)  r++;
			
		return r;
	}

}

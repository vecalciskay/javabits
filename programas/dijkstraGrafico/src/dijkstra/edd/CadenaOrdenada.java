package dijkstra.edd;

public class CadenaOrdenada<E> extends Cadena<E> {

	/**
	 * Si el objeto se puede ordenar entonces se lo inserta donde debe ser, sino, se utiliza
	 * el de la super clase
	 */
	public void insertar(E obj) {
		if (!(obj instanceof Comparable)) {
			super.insertar(obj);
			return;
		}
		
		if (raiz == null) {
			super.insertar(obj);
			return;
		}
		
		Comparable<E> c = (Comparable<E>)obj;
		
		// Primero testeamos si no va a la raiz
		if (c.compareTo(raiz.getValor()) < 0) {
			Nodo<E> nuevo = new Nodo<E>(obj);
			nuevo.setSiguiente(raiz);
			raiz.setAnterior(nuevo);
			raiz = nuevo;
			return;
		}
		
		// este ees el caso general insertar e.g. insertar F en A, C, E, M
		Nodo<E> proximo = raiz;
		
		while(proximo != null && c.compareTo(proximo.getValor()) >= 0) {
			proximo = proximo.getSiguiente();
		}
		
		// Entonces va a l final
		if (proximo == null) {
			Nodo<E> nuevo = new Nodo<E>(obj);
			nuevo.setAnterior(cola);
			cola.setSiguiente(nuevo);
			cola = nuevo;
			
			return;
		}
		
		// Va antes de proximo
		Nodo<E> nuevo = new Nodo<E>(obj);
		Nodo<E> anterior = proximo.getAnterior();
		Nodo<E> siguiente = proximo;
		
		nuevo.setAnterior(anterior);
		anterior.setSiguiente(nuevo);
		
		nuevo.setSiguiente(siguiente);
		siguiente.setAnterior(nuevo);
		
	}
}

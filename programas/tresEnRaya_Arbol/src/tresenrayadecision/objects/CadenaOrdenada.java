package tresenrayadecision.objects;

public class CadenaOrdenada<E> extends Cadena<E> {

    public CadenaOrdenada() {
        super();
    }

    public void insertar(E v) {
        if (!(v instanceof Comparable) || raiz == null) {
            super.insertar(v);
            return;
        }
        
        Nodo<E> actual = raiz;
        Comparable objNuevo = (Comparable)v;
        Nodo<E> objNuevoNodo = new Nodo<E>(v);

        // Busca y coloca en el lugar que corresponda o va hasta el final
        while (actual.getSiguiente() != null) {
            if (objNuevo.compareTo(actual.getValor()) < 0) {
                actual.colocarAntes(objNuevoNodo);
                if (actual == raiz) {
                    raiz = objNuevoNodo;
                }
                return;
            }
            actual = actual.getSiguiente();
        }

        // Si llega hasta el final lo coloca
        if (objNuevo.compareTo(actual.getValor()) < 0) {
            actual.colocarAntes(objNuevoNodo);
            if (actual == raiz) {
                raiz = objNuevoNodo;
            }
        } else {
            actual.colocarDespues(objNuevoNodo);
            cola = objNuevoNodo;
        }
    }
}
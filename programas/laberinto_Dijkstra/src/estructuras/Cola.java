package estructuras;

import java.util.EmptyStackException;

public class Cola<E> extends Cadena<E> {

    public Cola() {
        super();
    }

    public void push(E obj) {
        this.insertar(obj);
    }

    public E pull() throws EmptyStackException {
        if (cola == null)
            throw new EmptyStackException();

        Nodo<E> n = cola;
        cola = cola.getAnterior();
        if (cola != null)
            cola.setSiguiente(null);
        else
            raiz = cola;

        return n.getValor();
    }
}

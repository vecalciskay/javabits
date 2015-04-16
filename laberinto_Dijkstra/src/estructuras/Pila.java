package estructuras;

import java.util.EmptyStackException;

public class Pila<E> extends Cadena<E> {

    public Pila() {
        super();
    }

    public void push(E obj) {
        this.insertar(obj);
    }

    public E pop() throws EmptyStackException {
        if (raiz == null)
            throw new EmptyStackException();
        
        Nodo<E> n = raiz;
        raiz = raiz.getSiguiente();
        if (raiz != null)
            raiz.setAnterior(null);
        else
            cola = raiz;

        return n.getValor();
    }
}

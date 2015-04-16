package tresenrayadecision.objects;

import java.util.Iterator;

public class Cadena<E> {

    protected Cadena.Nodo<E> raiz;

    protected Cadena.Nodo<E> cola;

    public Cadena() {
        raiz = null;
        cola = null;
    }

    /**
     * Elimina un nodo de la cadena por el contenido que se pasa e nparametro.
     * @param v
     */
    public boolean eliminarContenido(E v) {
        if (raiz == null) {
            return false;
        }
        
        Cadena.Nodo<E> actual = raiz;
        // Mientras no llegue al final y no sea igual a v
        while (actual != null && actual.getValor() != v) {
            actual = actual.getSiguiente();
        }
        
        // Llego al final y no encontro al contenido
        if (actual == null)
            return false;
        
        Cadena.Nodo<E> anterior = actual.getAnterior();
        if (anterior != null)
            anterior.setSiguiente(actual.getSiguiente());
        else
            raiz = actual.getSiguiente();
        
        Cadena.Nodo<E> siguiente = actual.getSiguiente();
        if (siguiente != null)
            siguiente.setAnterior(anterior);
        else
            cola = anterior;
        
        return true;
    }

    /**
     * Inserta un nuevo nodo en el principio de la cadena.
     * @param v
     */
    public void insertar(E v) {
        Cadena.Nodo<E> n = new Cadena.Nodo<E>(v);
        if (raiz == null) {
            raiz = n;
            cola = n;
            return;
        }
        n.setSiguiente(raiz);
        raiz.setAnterior(n);
        raiz = n;
    }
    
    public void insertarAlFinal(E v) {
        Cadena.Nodo<E> n = new Cadena.Nodo<E>(v);
        if (cola == null) {
            raiz = n;
            cola = n;
            return;
        }
        
        n.setAnterior(cola);
        cola.setSiguiente(n);
        cola = n;
    }
    
    public E obtener(int idx) throws IndexOutOfBoundsException {
        if (idx < 0) 
            throw new IndexOutOfBoundsException("Indice no puede ser negativo: " + idx);
        if (idx >= this.tamano())
            throw new IndexOutOfBoundsException("Tamano Cadena: " + this.tamano() + " <= " + idx);
        
        int idxActual = 0;
        Cadena.Nodo<E> actual = this.raiz;
        while(idxActual < idx) {
            actual = actual.getSiguiente();
            idxActual++;
        }
        
        return actual.getValor();
    }

    public int tamano() {
        if (raiz == null)
            return 0;
        int tam = 0;
        Cadena.Nodo<E> actual = raiz;
        while (actual != null) {
            actual = actual.getSiguiente();
            tam++;
        }
        return tam;
    }

    public void imprimir() {
        System.out.println("--->--->--->--->--->--->--->--->--->--->----");
        Cadena.Nodo<E> actual = raiz;
        while (actual != null) {
            System.out.print(actual.toString() + " - ");
            actual = actual.getSiguiente();
        }
        System.out.println();
    }

    public void imprimirReversa() {
        System.out.println("---<---<---<---<---<---<---<---<---<---<----");
        Cadena.Nodo<E> actual = cola;
        while (actual != null) {
            System.out.print(actual.toString() + " - ");
            actual = actual.getAnterior();
        }
        System.out.println();
    }

    public Iterator<E> iterator() {
        Cadena.IteradorCadena<E> iterador;
        iterador = new Cadena.IteradorCadena<E>(this.raiz);
        return iterador;
    }

    public boolean estaVacia() {
        return raiz == null;
    }
    
    public E primero() throws IndexOutOfBoundsException {
        return this.obtener(0);
    }
                       
    public E ultimo() throws IndexOutOfBoundsException {
        if (raiz == null)
            throw new IndexOutOfBoundsException("Vacia");
        return cola.getValor();
    }

    /**
     * Nos indica si el objeto existe en la cadena.
     * @param obj
     * @return
     */
    public boolean existe(E obj) {
        Cadena.Nodo<E> actual = raiz;
        while (actual != null) {
            if (actual.getValor().equals(obj))
                return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    
    static class Nodo<E> {

        private E valor;

        private Nodo<E> siguiente;

        private Nodo<E> anterior;

        /**
         * Constructor de un nodo
         */
        public Nodo(E v) {
            siguiente = null;
            anterior = null;
            valor = v;
        }

        /**
         * @return Returns the anterior.
         */
        public Nodo<E> getAnterior() {
            return anterior;
        }

        /**
         * @param anterior The anterior to set.
         */
        public void setAnterior(Nodo<E> anterior) {
            this.anterior = anterior;
        }

        /**
         * @return Returns the siguiente.
         */
        public Nodo<E> getSiguiente() {
            return siguiente;
        }

        /**
         * @param siguiente The siguiente to set.
         */
        public void setSiguiente(Nodo<E> siguiente) {
            this.siguiente = siguiente;
        }

        /**
         * @return Returns the valor.
         */
        public E getValor() {
            return valor;
        }

        public String toString() {
            return this.hashCode() + "-> " + valor.toString();
        }

        public void colocarDespues(Nodo<E> n) {
            Nodo<E> sgte = this.getSiguiente();
            this.setSiguiente(n);
            n.setSiguiente(sgte);

            if (sgte != null)
                sgte.setAnterior(n);
            n.setAnterior(this);
        }

        public void colocarAntes(Nodo<E> n) {
            Nodo<E> ante = this.getAnterior();
            this.setAnterior(n);
            n.setAnterior(ante);

            if (ante != null)
                ante.setSiguiente(n);
            n.setSiguiente(this);
        }
    }
    
    static class IteradorCadena<E> implements Iterator<E> {

        private Cadena.Nodo<E> actual;

        public IteradorCadena(Cadena.Nodo<E> r) {
            actual = r;
        }

        /**
         * Retorna verdadero si es que actual tiene siguiente
         */
        public boolean hasNext() {
            return actual != null;
        }

        /**
         * 
         */
        public E next() {
            Cadena.Nodo<E> anterior = actual;
            actual = actual.getSiguiente();

            return anterior.getValor();
        }

        public void remove() {
            if (actual.getAnterior() != null) {
                actual.getAnterior().setSiguiente(actual.getSiguiente());
            }
            if (actual.getSiguiente() != null) {
                actual.getSiguiente().setAnterior(actual.getAnterior());
            }
        }
    }
}
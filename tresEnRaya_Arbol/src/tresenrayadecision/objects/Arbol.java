package tresenrayadecision.objects;

import java.util.Iterator;

/**
 * Clase que genericamente impmlementa un arbol.
 */
public class Arbol<E> {

    Nodo<E> raiz;

    public Arbol() {
        raiz = null;
    }
    
    /**
     * Devuelve la altura del arbol, es decir la catnidad maxima de saltos entre
     * la raiz y el extremo hoja.
     * @return
     */
    public int altura() {
        if (raiz == null)
            return 0;
        return raiz.altura();
    }
    
    /**
     * Devuelve el grado del arbol, es decir la cantidad maxima de hijos que 
     * tiene el arbol.
     * @return
     */
    public int grado() {
        if (raiz == null)
            return 0;
        return raiz.grado();
    }

    /**
     * Inserta un nodo en el arbol y devuelve el nodo que se acaba de insertar
     * ya en la jerarquia del arbol. El nodo que se pasa en parametro es el nodo
     * padre del nodo que se quiere insertar.
     * @param padre
     * @param v
     * @return
     */
    public Arbol.Nodo<E> insertarHijo(Nodo<E> padre, E v) {
        Arbol.Nodo<E> nuevo = new Arbol.Nodo<E>(v);

        if (padre == null) {
            raiz = nuevo;
        } else {
            padre.insertarHijo(nuevo);
        }
        return nuevo;
    }

    public String toString() {
        if (raiz == null)
            return "[ARBOL VACIO]";
        return raiz.toString();
    }
    
    public Nodo<E> getRaiz() {
        return raiz;
    }

    /**
     * Retorna la cantidad de nodos del arbol.
     */
    public int cantidadNodos() {
        return raiz.cantidadNodos();
    }

    /**
     * Esta clase es el nodo del arbol.
     */
    public static class Nodo<E> {
        private E valor;

        private Cadena<Nodo<E>> hijos;

        private Nodo<E> padre;

        /**
         * Constructor de un nodo
         */
        public Nodo(E v) {
            valor = v;
            hijos = new Cadena<Nodo<E>>();
            padre = null;
        }

        public void insertarHijo(Nodo<E> n) {
            n.setPadre(this);
            hijos.insertar(n);
        }
        
        /**
         * Obtiene la altura de 1 arbol a partir de este nodo
         * @return
         */
        public int altura() {
            if (hijos.estaVacia())
                return 1;

            CadenaOrdenada<Integer> res = new CadenaOrdenada<Integer>();

            for(Iterator<Nodo<E>> i = hijos.iterator(); i.hasNext();) {
                Nodo<E> n = i.next();
                res.insertar(new Integer(1 + n.altura()));
            }

            return (res.ultimo()).intValue();
        }
        
        /**
         * Obtiene el grado del arbol a partir de este nodo
         * @return
         */
        public int grado() {
            if (hijos.estaVacia())
                return 0;
            
            CadenaOrdenada<Integer> res = new CadenaOrdenada<Integer>();
            res.insertar(new Integer(hijos.tamano()));
            for(Iterator<Nodo<E>> i = hijos.iterator(); i.hasNext();) {
                Nodo<E> n = i.next();
                res.insertar(new Integer(n.grado()));
            }
            
            return res.ultimo().intValue();
        }
        
        /**
         * Devuelve la cantidad de nodos contando desde el nodo actual hasta el 
         * ultimo nodo en la jerarquia
         * @return
         */
        public int cantidadNodos() {
            if (hijos.estaVacia())
                return 1;
            
            int cantidad = 0;
            Iterator<Nodo<E>> iter = hijos.iterator();
            while(iter.hasNext()) {
                Nodo<E> hijo = iter.next();
                cantidad += hijo.cantidadNodos();
            }
            
            return cantidad;
        }

        /**
         * @return Returns the valor.
         */
        public Nodo<E> getPadre() {
            return this.padre;
        }

        /**
         * @return Returns the valor.
         */
        public void setPadre(Nodo<E> n) {
            this.padre = n;
        }

        /**
         * @return Returns the valor.
         */
        public Cadena<Nodo<E>> getHijos() {
            return this.hijos;
        }

        /**
         * @return Returns the valor.
         */
        public void setHijos(Cadena<Nodo<E>> nuevos) {
            this.hijos = nuevos;
        }

        /**
         * @return Returns the valor.
         */
        public E getValor() {
            return valor;
        }

        public String toString() {
            StringBuffer resultado = new StringBuffer();
            if (hijos.estaVacia()) {
                resultado.append(this.valor.toString());
                return resultado.toString();
            }

            resultado.append(valor.toString() + " -> (");
            for (Iterator<Nodo<E>> i = hijos.iterator(); i.hasNext(); ) {
                Nodo<E> objHijo = i.next();
                resultado.append(objHijo.toString() + ", ");
            }
            int largo = resultado.length();
            resultado.delete(resultado.length() - 2, resultado.length());

            resultado.append(")");

            return resultado.toString();
        }
        
        /**
         * Este metodo sirve para unir dos abroles. Solamente se colocan los hijos
         * de la raiz del arbol en el nodo actual. Asumiendo que el nodo actual
         * seria la nueva raiz del arbol que se esta colocando.
         * @param objArbol
         */
        public void colocarHijosDeRaizDeArbol(Arbol<E> objArbol) {
            hijos = new Cadena<Arbol.Nodo<E>>();
            
            Arbol.Nodo<E> objNodo = objArbol.getRaiz();
            if (objNodo != null) {
                
                Iterator<Arbol.Nodo<E>> iter = objNodo.getHijos().iterator();
                while(iter.hasNext()) {
                    Arbol.Nodo<E> hijoRaiz = iter.next();
                    this.hijos.insertarAlFinal(hijoRaiz);
                }
            }
        }
    }
}
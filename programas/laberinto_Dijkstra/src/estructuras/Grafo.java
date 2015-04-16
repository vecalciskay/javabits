package estructuras;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Observable;

public class Grafo<E> extends Observable {

    private Hashtable<String, Nodo<E>> nodos;

    public Grafo() {
        nodos = new Hashtable<String, Nodo<E>>();
    }

    public Collection<Grafo.Nodo<E>> getNodos() {
        return nodos.values();
    }

    /**
     * Inserta unnuevo nodo en el grafo. Sin embargo no lo enlaza a nada.
     * @param v
     */
    public void nuevoNodo(E v) {
        nuevoNodo(v.toString(), v);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Crea un nuevo nodo en el grafo.
     * @param id Identificador del nodo
     * @param v El objeto que va dentro del nodo
     */
    public void nuevoNodo(String id, E v) {
        Nodo<E> objNodo = new Nodo<E>(id, v);
        nodos.put(id, objNodo);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Crea una arista entre los dos nodos.
     * @param nodoOrigen El identifcador del nodo origen
     * @param nodoDestino El identificador del nodo destino
     */
    public void arista(String nodoOrigen, String nodoDestino) {
        Nodo<E> objNodoOrigen = this.nodos.get(nodoOrigen);
        Nodo<E> objNodoDestino = this.nodos.get(nodoDestino);

        objNodoOrigen.insertarVecino(objNodoDestino);
        
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Calcula el camino mas corto entre dos nodos. Los nodos deben exisitr 
     * en el grafo
     * @param de El identificador del nodo de origen
     * @param a El identificador del nodo destino
     * @return El numero de pasos o saltos que se necesita para ir de de a a.
     */
    public int caminoMasCorto(String de, String a) {
        // inicializamos las visitas a 0
        inicializarVisitadoa0();

        // creamos la cola y colocamos el nodo DE
        Cola<Nodo<E>> tocaVisitar = new Cola<Nodo<E>>();
        boolean encontro = false;

        Nodo<E> nodoDe = nodos.get(de);
        nodoDe.setVisitaMas1();
        tocaVisitar.push(nodoDe);

        Nodo<E> nodoA = nodos.get(a);

        // Mientras la cola no este vacia o no hayamos encontrado a A
        while (!tocaVisitar.estaVacia() && !encontro) {
            Nodo<E> objNodo = tocaVisitar.pull();
            encontro = visitarNodo(objNodo, tocaVisitar, nodoA);
        }
        return nodoA.getVisitado() - 1;
    }

    /**
     * Inicializa todo para hacer el algoritmod e camino mas corto.
     * Coloca los visitados de todos los nodos a 0.
     */
    private void inicializarVisitadoa0() {
        Iterator<Grafo.Nodo<E>> i = nodos.values().iterator();
        while (i.hasNext()) {
            i.next().resetVisita();
        }
    }

    /**
     * Este metodo es el mas importante en el algoritmo de BFS. SE coloca todos
     * los nodos a visitar en la cola para tal efecto y se van cambiando los 
     * valores del visitado de acuerdo como corresponda
     * @param objVisitado El nodo a visitar.
     * @param tocaVisitar La cola donde se colocaran los vecinos no visitados
     * @param objDestino El nodo que se testea para saber cuando parar.
     * @return Verdadreo o falso para saber si lo encuentra.
     */
    private boolean visitarNodo(Nodo<E> objVisitado, Cola<Nodo<E>> tocaVisitar, 
                                Nodo<E> objDestino) {
        Iterator<Nodo<E>> vecinosVisitado = objVisitado.getVecinos().iterator();
        while(vecinosVisitado.hasNext()) {
            Nodo<E> vecino = vecinosVisitado.next();
            
            // Si es el que estamos buscando devuelve true y le coloca el numero
            // de visitado que corresponda.
            if (vecino == objDestino) {
                vecino.setVisitado(objVisitado.getVisitado() + 1);
                return true;
            }
            
            // Solamente lo coloca si no esta en la cola (si no ha sido visitado)
            if (vecino.getVisitado() == 0)
                tocaVisitar.push(vecino);
            
            // Coloca el visitado de acuerdo a lo que corresponde
            vecino.setVisitado(objVisitado.getVisitado() + 1);
        }
        // Si no lo encuentra entre los vecinos de este nodo
        return false;
    }
    
    /**
     * Encuentra el id del nodo que tiene el valor de distancia mas pequeno. Esto es utilizado
     * por el algoritmo de Dijkstra.
     * @param analizados El conjunto de nodos que se analiza
     * @param distancias Las distancias que tiene cada uno
     * @return El id del nodo con la menor distancia
     */
    private String getNodoDistanciaMasCortaEnAnalizados(Cadena<String> analizados, 
                                                        Hashtable<String, Integer> distancias) {
        String resultado = null;
        int minDistancia = Integer.MAX_VALUE;

        Iterator<String> i = analizados.iterator();
        while (i.hasNext()) {
            String idNodo = i.next();
            int distanciaNodo = distancias.get(idNodo).intValue();
            if (distanciaNodo < minDistancia) {
                minDistancia = distanciaNodo;
                resultado = idNodo;
            }
        }
        return resultado;
    }
    
    /**
     * Forma la respuesta tomando el hastable de los nodos ANTERIOR en el 
     * algoritmo de Dijkstra
     * @param anterior
     * @return
     */
    private Pila<Nodo<E>> formarRespuesta(Hashtable<String, String> anterior,
                                              String destino) {
        Pila<Nodo<E>> resultado = new Pila<Nodo<E>>();
        Nodo<E> recorre = nodos.get(destino);
        resultado.push(recorre);
        
        while(true) {
            String idAnterior = anterior.get(recorre.getId());
            if (idAnterior == null)
                break;
            recorre = nodos.get(idAnterior);
            resultado.push(recorre);
        }
        return resultado;
    }
    
    /**
     * Inicializa todos los valores en el algoritmo de dijkstra. Las distanacias 
     * infinito meons para el origne que es 0. Coloca en analizados TODOS los
     * nodos.
     * @param origen
     * @param analizados
     * @param distancias
     */
    private void inicializarParaDijkstra(String origen, 
                                         Cadena<String> analizados, 
                                         Hashtable<String, Integer> distancias) {
        Iterator<Nodo<E>> i = this.nodos.values().iterator();
        while (i.hasNext()) {
            Nodo<E> objNodo = i.next();
            distancias.put(objNodo.getId(), new Integer(Integer.MAX_VALUE));
            analizados.insertar(objNodo.getId());
        }

        boolean encontro = false;

        // coloca la distancia del origen a 0
        distancias.put(origen, new Integer(0));
    }
    
    /**
     * Calcula el camino mas corto entre dos nodos. Los nodos deben exisitr 
     * en el grafo
     * @param origen El identificador del nodo de origen
     * @param destino El identificador del nodo destino
     * @return El numero de pasos o saltos que se necesita para ir de de a a.
     */
    public Pila<Nodo<E>> caminoMasCortoDijkstra(String origen, String destino) {
        if (nodos.get(origen) == null || nodos.get(destino) == null)
            return null;
        // Coloca las distancias de todos los nodos a infinito
        // Se aqrma una cadena analizados con todos los nodos
        Cadena<String> analizados = new Cadena<String>();
        Hashtable<String, Integer> distancias = 
            new Hashtable<String, Integer>();
        Hashtable<String, String> anterior = new Hashtable<String, String>();

        inicializarParaDijkstra(origen, analizados, distancias);

        // comienza la iteracion
        while (analizados.tamano() > 0) {
            String idNodo = 
                getNodoDistanciaMasCortaEnAnalizados(analizados, distancias);
            // Lo quita de la lista de analizados
            analizados.eliminarContenido(idNodo);
            
            if (idNodo.equals(destino)) {
                Pila<Nodo<E>> resultado = formarRespuesta(anterior, destino);
                return resultado;
            }

            Iterator<Nodo<E>> i = nodos.get(idNodo).getVecinos().iterator();
            while (i.hasNext()) {
                Nodo<E> objNodo = i.next();
                // Solamente los que estan en analizados
                if (!analizados.existe(objNodo.getId())) {
                    continue;
                }

                int calculoDistancia = 
                    distancias.get(idNodo).intValue() + 1;
                int distanciaParaNodoVecino = 
                    distancias.get(objNodo.getId()).intValue();
                if (calculoDistancia < distanciaParaNodoVecino) {
                    distancias.put(objNodo.getId(), new Integer(calculoDistancia));
                    anterior.put(objNodo.getId(), idNodo);
                }
            }
        }

        return null;
    }

    /**
     * Cada nodo tiene un identificador y un valor dentro del nodo
     */
    public static class Nodo<E> {
        /**
         * El identificador es un string que se utiliza para poder encontrar al 
         * nodode manera rapida en la lista de nodos.
         */
        private String id;

        private E valor;

        private Cadena<Nodo<E>> vecinos;

        private int visitado;

        /**
         * El constructos de un nodo de un grafo. Se necesita obligatoriamente un
         * nombre de nodo (identificador) que NO se encuentre en el grafo ya.
         * @param identificador
         * @param v
         */
        public Nodo(String identificador, E v) {
            this.id = identificador;
            this.valor = v;
            this.vecinos = new Cadena<Nodo<E>>();
            this.visitado = 0;
        }


        public String getId() {
            return id;
        }

        public E getValor() {
            return valor;
        }

        public int getVisitado() {
            return visitado;
        }
        
        public void setVisitado(int v) {
            this.visitado = v;
        }

        public void setVecinos(Cadena<Grafo.Nodo<E>> newvecinos) {
            this.vecinos = newvecinos;
        }

        public Cadena<Grafo.Nodo<E>> getVecinos() {
            return vecinos;
        }

        /**
         * Solamente aumenta el valor del campo visitado +1
         */
        public void setVisitaMas1() {
            this.visitado++;
        }

        /**
         * Pone la visita del nodo a 0
         */
        public void resetVisita() {
            this.visitado = 0;
        }

        /**
         * Inserta un vecino en el nodo, pero primero ve si el vecino no se 
         * encuentra ya en la lista de vecinos.
         * @param objNodoDestino El objeto nodo de destino
         */
        private void insertarVecino(Grafo.Nodo<E> objNodoDestino) {
            Iterator<Grafo.Nodo<E>> i = vecinos.iterator();
            while (i.hasNext()) {
                Grafo.Nodo<E> obj = i.next();
                if (obj == objNodoDestino)
                    return;
            }

            vecinos.insertar(objNodoDestino);
        }
    }
}

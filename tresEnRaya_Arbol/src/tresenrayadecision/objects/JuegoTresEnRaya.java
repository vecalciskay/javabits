package tresenrayadecision.objects;

import java.net.URL;

import java.util.Iterator;
import java.util.Observable;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import tresenrayadecision.TresEnRayaException;

public class JuegoTresEnRaya extends Observable {

    private static Logger logger = Logger.getRootLogger();

    public static final int JUGADOR = 1;
    public static final int COMPUTADORA = 2;
    public static final int COMENZADO = 1;
    public static final int TERMINADO = 2;

    private int quienJuega;
    private int quienComienza;
    private int quienGano;
    private int estado;    
    private int numeroNodosUltima;

    private Cadena<Jugada> movimientos;

    public JuegoTresEnRaya() {
        movimientos = new Cadena<Jugada>();
        estado = COMENZADO;
        quienGano = 0;
    }

    /**
     * Este metodo marca un nuevo moviemiento realizado por el jugador pasado en parametro.
     * La fila y la columna indican el lugar del movimiento. El sistema guarda el movimiento
     * en la cola de movimientos del juego.
     * @param fila numero de 0 a 2
     * @param columna numero de 0 a 2
     * @param jugador numero de 1 a 2 puede ser las constantes JUGADOR o COMPUTADORA
     */
    public void nuevoMovimiento(int fila, int columna, int jugador) throws TresEnRayaException {
        
        Jugada nueva = null;
        if (!movimientos.estaVacia()) {
            Jugada ultima = movimientos.ultimo();
            nueva = ultima.clonar();
        } else {
            nueva = new Jugada();
        }
        
        logger.debug("Movimiento del jugador " + jugador + " a filaXcolumna: " + fila + " x " + columna);
        nueva.marcar(fila, columna, jugador);
        if (nueva.haGanado(jugador)) {
            estado = TERMINADO;
            quienGano = jugador;
        }
        
        movimientos.insertarAlFinal(nueva);
        
        quienJuega = (jugador == JUGADOR ? COMPUTADORA : JUGADOR);
        
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Crea un arbol de TODOS los posibles movimientos a partir de una jugada. Este
     * metodo puede ser bastantee pesado ya que puede crear un arbol de varios
     * miles de nodos al menos para la primera jugada.
     * Alternativamente luego de cada jugada cambia de jugador para simular TODAS 
     * las posibles jugadas.
     * @param movida
     * @param jugador
     * @return
     */
    public Arbol<Jugada> crearArbol(Jugada movida, int jugador) {
        Arbol<Jugada> result = new Arbol<Jugada>();
        
        Arbol.Nodo<Jugada> nodoRaiz = result.insertarHijo(null, movida);
        
        Cadena<Jugada> posiblesJugadas = movida.getPosiblesJugadas(jugador);
        int otroJugador = (jugador == JUGADOR ? COMPUTADORA : JUGADOR);
        
        Iterator<Jugada> iter = posiblesJugadas.iterator();
        while(iter.hasNext()) {
            Jugada hijo = iter.next();
            Arbol.Nodo<Jugada> nodoHijo = result.insertarHijo(nodoRaiz, hijo);
            Arbol<Jugada> arbolHijo = crearArbol(hijo, otroJugador);
            
            nodoHijo.colocarHijosDeRaizDeArbol(arbolHijo);
        }
        
        return result;
    }

    /**
     * Elige una jugada a partir del arbol de decision obtenido para el jugador
     * pasado en parametro.
     * Para esto lo que hace es contar la cantidad de victorias que le da cada uno
     * de los hijos de la raiz (las posibles jugadas). Luego, lo unico que se hace
     * es elegir el hijo que da la mayor cantidad de victorias.
     * @param arbolDecision
     * @param jugador
     * @return
     */
    private Jugada elegirJugada(Arbol<Jugada> arbolDecision, int jugador) {
        int maxCantidadVictorias = Integer.MIN_VALUE;
        Jugada elegida = null;
        
        Cadena<Arbol.Nodo<Jugada>> posibles =
            arbolDecision.getRaiz().getHijos();
        
        Iterator<Arbol.Nodo<Jugada>> iter = posibles.iterator();
        while(iter.hasNext()) {                       
            Arbol.Nodo<Jugada> nodo = iter.next();
            logger.debug("Jugada: \n" + nodo.getValor());
            int cantidadVictorias = getCantidadVictoriasAPartirDe(nodo, jugador, 1);
            logger.debug("Tiene " + cantidadVictorias + " victorias posibles");
            if (cantidadVictorias >= maxCantidadVictorias) {
                elegida = nodo.getValor();
                maxCantidadVictorias = cantidadVictorias;
            }
        }
        
        return elegida;
    }
    
    /**
     * El nodo hijo pasado es el ultimo movimiento del jugador pasado en parametro, 
     * los hijos de este nodo son los posibles movimientos del OTRO jugador, donde NO
     * puede ganar este que estamos probando.
     * @param nodo
     * @param jugador
     * @param profundidad
     * @return
     */
    public int getCantidadVictoriasAPartirDe(Arbol.Nodo<Jugada> nodo, int jugador, int profundidad) {
        if (nodo.getHijos().estaVacia()) {
            if (nodo.getValor().haGanado(jugador))
                return (10 - profundidad);
            int otroJugador = (jugador == COMPUTADORA ? JUGADOR : COMPUTADORA);
            if (nodo.getValor().haGanado(otroJugador))
                return (profundidad - 10);
            return 0;
        }
        
        int total = 0;
        Iterator<Arbol.Nodo<Jugada>> iter = nodo.getHijos().iterator();
        while(iter.hasNext()) {
            Arbol.Nodo<Jugada> nodoHijo = iter.next();
            total += getCantidadVictoriasAPartirDe(nodoHijo, jugador, profundidad + 1);
        }
        
        return total;
    }
    
    /**
     * Luego de elegir entre las posibles jugadas, este metodo devuelve las coordenadas
     * que nos llevan a esa jugada elegida. 
     * @param jugador
     * @return
     */
    public int[] elegirMovimiento(int jugador) {
        Jugada ultima = movimientos.ultimo();
        
        Arbol<Jugada> arbolDecision = crearArbol(ultima, jugador);
        logger.debug("El arbol de decision tiene " + arbolDecision.cantidadNodos() + " nodos");
        logger.debug("El arbol de decision tiene profundidad " + arbolDecision.altura() + " nodos");
        logger.debug("El arbol de decision tiene grado " + arbolDecision.grado());
        
        this.numeroNodosUltima = arbolDecision.cantidadNodos();
        
        Jugada decision = elegirJugada(arbolDecision, jugador);
        
        int[] movida = ultima.coordenadaParaLlegarA(decision);
        
        return movida;
    }

    public static void main(String[] args) {
        
        String resource = "/auditoria.properties";
        URL configFileResource = JuegoTresEnRaya.class.getResource(resource);
        PropertyConfigurator.configure(configFileResource);
        
        JuegoTresEnRaya j = new JuegoTresEnRaya();

        j.setQuienComienza(JUGADOR);
        j.setQuienJuega(JUGADOR);

        while (!j.estaTerminado()) {
            if (j.getQuienJuega() == JUGADOR) {
                int fila = -1;
                int columna = -1;
                String filaCol = 
                    JOptionPane.showInputDialog("Coordenadas en la forma fila-col");
                StringTokenizer tokens = new StringTokenizer(filaCol, "-");
                fila = Integer.parseInt(tokens.nextToken());
                columna = Integer.parseInt(tokens.nextToken());

                try {
                    j.nuevoMovimiento(fila, columna, JUGADOR);
                } catch (TresEnRayaException e) {
                    logger.error("Error al crear el nuevo movimiento", e);
                    break;
                }
            } else {

                int[] nuevoMovimiento = j.elegirMovimiento(COMPUTADORA);
                try {
                    j.nuevoMovimiento(nuevoMovimiento[0], nuevoMovimiento[1],
                                  COMPUTADORA);
                } catch (TresEnRayaException e) {
                    logger.error("Error al crear el nuevo movimiento", e);
                    break;
                }
            }
            
            System.out.println(j);
        }

        System.out.println("Ha ganado: " + j.getQuienGano());
    }
    
    /**
     * Imprime el ultimo movimiento que se ha realizado en este juego
     * @return
     */
    public String toString() {
        if (movimientos.ultimo() == null)
            return "=====";
        return movimientos.ultimo().toString();
    }

    /**
     * Cambia el orden de quien juega ahora.
     * @param newquienJuega
     */
    public void setQuienJuega(int newquienJuega) {
        this.quienJuega = newquienJuega;
    }

    /**
     * Obtiene quien juega
     * @return
     */
    public int getQuienJuega() {
        return quienJuega;
    }

    /**
     * Cambia el orden de quien comienza
     * @param newquienComienza
     */
    public void setQuienComienza(int newquienComienza) {
        this.quienComienza = newquienComienza;
    }

    /**
     * Obtiene quien comienza
     * @return
     */
    public int getQuienComienza() {
        return quienComienza;
    }

    public Cadena<Jugada> getMovimientos() {
        return movimientos;
    }

    public void setEstado(int newestado) {
        this.estado = newestado;
    }

    public int getEstado() {
        return estado;
    }

    public boolean estaTerminado() {
        return estado == TERMINADO;
    }

    public void setQuienGano(int newquienGano) {
        this.quienGano = newquienGano;
    }

    public int getQuienGano() {
        return quienGano;
    }

    public void setNumeroNodosUltima(int newnumeroNodosUltima) {
        this.numeroNodosUltima = newnumeroNodosUltima;
    }

    public int getNumeroNodosUltima() {
        return numeroNodosUltima;
    }
}

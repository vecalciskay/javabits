package laberinto.gui;

import estructuras.Cadena;
import estructuras.Grafo;

import estructuras.Pila;

import java.awt.Color;
import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;

import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class DibujoGrafo {

    private int anchoNodo = 20;

    private Grafo<DibujoNodo> grafo;

    private Pila<Grafo.Nodo<DibujoNodo>> ultimoCamino = null;

    private static final Logger logger = Logger.getLogger(DibujoGrafo.class);

    public DibujoGrafo(Grafo<DibujoNodo> elGrafo) {
        grafo = elGrafo;
    }

    public void dibujar(Graphics gc) {

        int maximaFila = getMaximaFila();
        int maximaColumna = getMaximaColumna();

        gc.setColor(Color.lightGray);
        gc.fillRect(0, 0, maximaColumna * anchoNodo, maximaFila * anchoNodo);

        Iterator<Grafo.Nodo<DibujoNodo>> iter = grafo.getNodos().iterator();
        while (iter.hasNext()) {
            Grafo.Nodo<DibujoNodo> nodo = iter.next();

            nodo.getValor().dibujar(gc, this.anchoNodo);
        }
    }

    public int getMaximaFila() {
        int maxima = 0;
        Iterator<Grafo.Nodo<DibujoNodo>> iter = grafo.getNodos().iterator();
        while (iter.hasNext()) {
            Grafo.Nodo<DibujoNodo> nodo = iter.next();

            int filaNodo = nodo.getValor().getFila();

            if (filaNodo > maxima)
                maxima = filaNodo;
        }

        return maxima;
    }

    public int getMaximaColumna() {
        int maxima = 0;
        Iterator<Grafo.Nodo<DibujoNodo>> iter = grafo.getNodos().iterator();
        while (iter.hasNext()) {
            Grafo.Nodo<DibujoNodo> nodo = iter.next();

            int columnaNodo = nodo.getValor().getColumna();

            if (columnaNodo > maxima)
                maxima = columnaNodo;
        }

        return maxima;
    }

    public void setAnchoNodo(int newanchoNodo) {
        this.anchoNodo = newanchoNodo;
    }

    public int getAnchoNodo() {
        return anchoNodo;
    }

    public void leerNodosLaberinto(File aFile) throws Exception {
        BufferedReader theReader = null;
        try {
            theReader = new BufferedReader(new FileReader(aFile));
            String linea = theReader.readLine();
            while (theReader.ready()) {

                DibujoNodo unNodoDibujo = new DibujoNodo(linea);
                logger.debug("Cargando nodo " + unNodoDibujo.getNombre() + 
                             " en el grafo");
                grafo.nuevoNodo(unNodoDibujo.getNombre(), unNodoDibujo);

                linea = theReader.readLine();
            }
        } catch (Exception err) {
            logger.error("Error al leer el archivo", err);
            throw new Exception("Error al leer el archivo de arcos, ver logs");
        } finally {
            if (theReader != null)
                theReader.close();
        }
    }

    public Grafo<DibujoNodo> getGrafo() {
        return grafo;
    }

    public void leerArcosLaberinto(File aFile) throws Exception {
        BufferedReader theReader = null;
        try {
            theReader = new BufferedReader(new FileReader(aFile));
            String linea = theReader.readLine();
            while (theReader.ready()) {

                StringTokenizer tokens = new StringTokenizer(linea);
                String desde = tokens.nextToken();
                String hasta = tokens.nextToken();

                logger.debug("Lee una arista que va de " + desde + " a " + 
                             hasta);
                grafo.arista(desde, hasta);

                linea = theReader.readLine();
            }
        } catch (Exception err) {
            logger.error("Error al leer el archivo de arcos", err);
            throw new Exception("Error al leer el archivo de arcos, ver logs");
        } finally {
            if (theReader != null)
                theReader.close();
        }
    }

    /**
     * Revisa todos los nodos del grafo y dependiendo de sus vecinos va colocando
     * los valores de las paredes en el nodo bidujo de cada nodo
     */
    public void calcularParedes() {
        logger.info("Coloca todas las paredes a 0, quiere decir que hay paredes en todo lado");
        Iterator<Grafo.Nodo<DibujoNodo>> iter = 
            this.grafo.getNodos().iterator();
        while (iter.hasNext()) {
            Grafo.Nodo<DibujoNodo> nodo = iter.next();
            nodo.getValor().hayVecinoEn(0);
        }

        logger.info("Coloca las paredes dependiendo de los vecinos");
        iter = this.grafo.getNodos().iterator();
        while (iter.hasNext()) {
            Grafo.Nodo<DibujoNodo> nodo = iter.next();
            nodo.getValor().hayVecinoEn(0);

            Cadena<Grafo.Nodo<DibujoNodo>> vecinos = nodo.getVecinos();
            Iterator<Grafo.Nodo<DibujoNodo>> iterVecino = vecinos.iterator();
            while (iterVecino.hasNext()) {
                Grafo.Nodo<DibujoNodo> vecino = iterVecino.next();
                calcularParedDeVecino(nodo, vecino);
            }
        }
    }

    public void calcularParedDeVecino(Grafo.Nodo<DibujoNodo> nodo, 
                                      Grafo.Nodo<DibujoNodo> vecino) {
        // Si esta a la derecha
        if (nodo.getValor().getFila() == vecino.getValor().getFila() && 
            nodo.getValor().getColumna() < vecino.getValor().getColumna())
            nodo.getValor().hayVecinoEn(DibujoNodo.RIGHT);

        if (nodo.getValor().getFila() == vecino.getValor().getFila() && 
            nodo.getValor().getColumna() > vecino.getValor().getColumna())
            nodo.getValor().hayVecinoEn(DibujoNodo.LEFT);

        if (nodo.getValor().getColumna() == vecino.getValor().getColumna() && 
            nodo.getValor().getFila() < vecino.getValor().getFila())
            nodo.getValor().hayVecinoEn(DibujoNodo.BOTTOM);

        if (nodo.getValor().getColumna() == vecino.getValor().getColumna() && 
            nodo.getValor().getFila() > vecino.getValor().getFila())
            nodo.getValor().hayVecinoEn(DibujoNodo.TOP);
    }

    /**
     * Marca el ultimo camino calculado con el algoritmo. En cada un ode los nodos
     * dibujo se coloca la direccion que toma cada uno para que se recorra por el 
     * buen camino y se puedan ver las flechas correspondientes.
     * @param newultimoCamino
     */
    public void setUltimoCamino(Pila<Grafo.Nodo<DibujoNodo>> newultimoCamino) {
        this.ultimoCamino = newultimoCamino;

        logger.info("Borrando el valor de cualquier camino anterior que habia");
        Iterator<Grafo.Nodo<DibujoNodo>> iter = 
            this.grafo.getNodos().iterator();
        while (iter.hasNext()) {
            Grafo.Nodo<DibujoNodo> nodo = iter.next();
            nodo.getValor().setDireccion(0);
        }

        if (newultimoCamino.tamano() <= 1) {
            logger.warn("No existen al menos dos nodos (inicio y fin) en el camino pasado en parametro");
            return;
        }

        logger.info("Coloca el camino nuevo en el grafo");
        Grafo.Nodo<DibujoNodo> actual = newultimoCamino.pop();
        Grafo.Nodo<DibujoNodo> siguiente = null;
        while (!newultimoCamino.estaVacia()) {
            siguiente = newultimoCamino.pop();

            logger.debug("Haciendo camino entre: " + 
                         actual.getValor().getNombre() + " y " + 
                         siguiente.getValor().getNombre());

            if (actual.getValor().getFila() == 
                siguiente.getValor().getFila() && 
                actual.getValor().getColumna() < 
                siguiente.getValor().getColumna())
                actual.getValor().setDireccion(DibujoNodo.RIGHT);

            if (actual.getValor().getFila() == 
                siguiente.getValor().getFila() && 
                actual.getValor().getColumna() > 
                siguiente.getValor().getColumna())
                actual.getValor().setDireccion(DibujoNodo.LEFT);

            if (actual.getValor().getColumna() == 
                siguiente.getValor().getColumna() && 
                actual.getValor().getFila() < siguiente.getValor().getFila())
                actual.getValor().setDireccion(DibujoNodo.BOTTOM);

            if (actual.getValor().getColumna() == 
                siguiente.getValor().getColumna() && 
                actual.getValor().getFila() > siguiente.getValor().getFila())
                actual.getValor().setDireccion(DibujoNodo.TOP);

            actual = siguiente;
        }
        
        siguiente.getValor().setDireccion(DibujoNodo.END);
    }

    public Pila<Grafo.Nodo<DibujoNodo>> getUltimoCamino() {
        return ultimoCamino;
    }
}

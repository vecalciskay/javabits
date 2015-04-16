package tp4;

import java.awt.Graphics;

import java.util.LinkedList;
import java.util.Queue;

public class Grafo implements Dibujo {

    private int numeroCeldas;
    private Celda[] celdas;
    private int ultimoId;
    private Queue<Celda> tocaVisitar;
    private int nroPasos;

    private int width;
    private int height;
    private int tamanoCelda;
    private int maxIdCeldaAMostrar;

    public Grafo(int nb, int ancho, int alto, int celdaSize) {
        this.width = ancho;
        this.height = alto;
        this.tamanoCelda = celdaSize;
        this.numeroCeldas = nb;
        this.maxIdCeldaAMostrar = 
                this.maximoNroCeldaMostrar((int)((width / 2) / tamanoCelda) + 
                                           1) - 1;

        celdas = new Celda[this.numeroCeldas];

        for (int i = 0; i < this.numeroCeldas; i++)
            celdas[i] = new Celda(i + 1, this);

        celdas[0].setX(this.width / 2 - (this.tamanoCelda / 2));
        celdas[0].setY(this.height / 2 - (this.tamanoCelda / 2));

        this.enlazarGrafo();
        this.tocaVisitar = new LinkedList<Celda>();
    }

    private void enlazarGrafo() {
        this.ultimoId = 2;
        int idEnlazador = 1;

        // Lo lógico
        celdas[idEnlazador - 1].enlazar(celdas[2 - 1], 0);
        celdas[idEnlazador - 1].enlazar(celdas[3 - 1], 1);
        celdas[idEnlazador - 1].enlazar(celdas[4 - 1], 2);
        celdas[idEnlazador - 1].enlazar(celdas[5 - 1], 3);
        celdas[idEnlazador - 1].enlazar(celdas[6 - 1], 4);
        celdas[idEnlazador - 1].enlazar(celdas[7 - 1], 5);
        // 2 con 3, 3 con 4..... 7 con 2
        celdas[2 - 1].enlazar(celdas[3 - 1], 2);
        celdas[3 - 1].enlazar(celdas[4 - 1], 3);
        celdas[4 - 1].enlazar(celdas[5 - 1], 4);
        celdas[5 - 1].enlazar(celdas[6 - 1], 5);
        celdas[6 - 1].enlazar(celdas[7 - 1], 0);
        //celdas[7 - 1].enlazar(celdas[2 - 1], 1);

        // toca colocar la 8
        this.ultimoId = 7;
        // enlazada en el número 7
        idEnlazador = 2;

        while (this.ultimoId < this.numeroCeldas) {
            idEnlazador = celdas[idEnlazador - 1].enlazarTodoPosible();
        }
    }

    /**
     * @return Returns the numeroCeldas.
     */
    public int getNumeroCeldas() {
        return numeroCeldas;
    }

    public int caminoMasCorto(int de, int a) {
        this.tocaVisitar = new LinkedList<Celda>();
        for (int i = 0; i < this.numeroCeldas; i++) {
            celdas[i].setPaso(0);
        }

        boolean encontro = false;

        celdas[de - 1].setPaso(1);
        this.tocaVisitar.add(celdas[de - 1]);
        // sino, seguimos buscando
        while (!this.tocaVisitar.isEmpty() && !encontro) {
            Celda c = this.tocaVisitar.poll();
            encontro = this.visitarCelda(c.getId(), a);
        }
        return celdas[a - 1].getPaso() - 1;
    }

    /**
     * Visita celda y marca sus vecinos si es que no han sido visitados
     * @param id
     */
    public boolean visitarCelda(int id, int a) {
        Celda c = this.celdas[id - 1];

        for (int i = 0; i < 6; i++) {

            // Si no es nulo y no ha sido visitado
            Celda vecinoDeCenI = c.getVecino(i);
            if (vecinoDeCenI != null) {
                // colocarlo en espera e la pila
                if (vecinoDeCenI.getId() == a) {
                    vecinoDeCenI.setPaso(c.getPaso() + 1);
                    return true;
                }
                if (vecinoDeCenI.getPaso() == 0)
                    this.tocaVisitar.add(vecinoDeCenI);
                vecinoDeCenI.setMinPaso(c.getPaso() + 1);
            }
        }
        return false;
    }

    public void dibujar(Graphics gc) {
        int muestraHasta = 
            (this.maxIdCeldaAMostrar > this.numeroCeldas ? this.numeroCeldas : 
             this.maxIdCeldaAMostrar);

        for (int i = 0; i < muestraHasta; i++) {
            celdas[i].dibujar(gc);
        }
    }

    public int maximoNroCeldaMostrar(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        return (2 * maximoNroCeldaMostrar(n - 1) - 
                maximoNroCeldaMostrar(n - 2) + 6);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    /**
     * @return Returns the celdas.
     */
    public Celda[] getCeldas() {
        return celdas;
    }

    /**
     * @return Returns the nroPasos.
     */
    public int getNroPasos() {
        return nroPasos;
    }

    /**
     * @return Returns the tocaVisitar.
     */
    public Queue<Celda> getTocaVisitar() {
        return tocaVisitar;
    }

    /**
     * @return Returns the ultimoId.
     */
    public int getUltimoId() {
        return ultimoId;
    }

    public void setUltimoId(int resp) {
        this.ultimoId = resp;
    }

    /**
     * @return Returns the tamanoCelda.
     */
    public int getTamanoCelda() {
        return tamanoCelda;
    }
}

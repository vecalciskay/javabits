package tp4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import util.ColorSet;

public class Celda {

    private int id;
    private Celda[] vecinos;
    private int paso;
    private int x = 0;
    private int y = 0;

    private Grafo padre;

    public Celda(int v, Grafo parent) {
        this.id = v;
        this.padre = parent;
        vecinos = new Celda[6];
        paso = 0;
    }

    /**
     * @return Returns the paso.
     */
    public int getPaso() {
        return paso;
    }

    /**
     * @param paso The paso to set.
     */
    public void setPaso(int paso) {
        this.paso = paso;
    }

    /**
     * Dependiendo de donde se acerca la otra celda,
     * Si se acerca por la derecha, entonces indice 0
     * Si se acerca arriba-derecha, entonces indice 1
     * Si se acerca arriba-izquierda, entonces indice 2
     * Si se acerca por la izquierda, entonces indice 3
     * Si se acerca abajo-izquierda, entonces indice 4
     * Si se acerca abajo-derecha, entonces indice 5
     * @param otra
     * @param indice
     */
    public void enlazar(Celda otra, int indice) {
        this.vecinos[indice] = otra;
        otra.vecinos[(indice + 3) % 6] = this;

        if (otra.x != 0 && otra.y != 0)
            return;
        otra.x = this.x;
        otra.y = this.y;
        if (indice == 0)
            otra.x = this.x + padre.getTamanoCelda();
        if (indice == 3)
            otra.x = this.x - padre.getTamanoCelda();
        if (indice == 1 || indice == 5)
            otra.x = this.x + (padre.getTamanoCelda() / 2);
        if (indice == 2 || indice == 4)
            otra.x = this.x - (padre.getTamanoCelda() / 2);
        if (indice == 1 || indice == 2)
            otra.y = this.y - padre.getTamanoCelda() + 2;
        if (indice == 4 || indice == 5)
            otra.y = this.y + padre.getTamanoCelda() - 2;
    }

    public int enlazarTodoPosible() {
        int resp = padre.getUltimoId();
        int desde = 0;

        // Encuentra el primer nulo después de algún lleno,
        // siempre debe haber alguno ya que el grafo cuenta con al menos
        // 7 nodos

        // Encontrar primero el cambio 
        boolean esNulo = false;
        if (this.vecinos[0] == null)
            esNulo = true;
        else
            esNulo = false;

        int cambioEn = 0;
        for (int i = 1; i < 6; i++) {
            if (esNulo && this.vecinos[i] != null) {
                cambioEn = i;
                break;
            }
            if (!esNulo && this.vecinos[i] == null) {
                cambioEn = i;
                break;
            }
        }

        // Si el cambio es NULO, entonces donde
        // debemos colocar en cambioEn
        if (!esNulo) {
            desde = cambioEn;
        } else {
            // Debemos seguir desde el No nulo encontrado hasta encontrar
            // el primero Nulo
            int k = cambioEn;
            for (int i = 0; i < 6; i++) {
                if (this.vecinos[(k + i) % 6] == null) {
                    desde = (k + i) % 6;
                    break;
                }
            }
        }

        // lo anterior
        this.enlazar(padre.getCeldas()[resp - 1], desde);

        for (int i = desde + 1; i < (desde + 6); i++) {
            // si ya esta enlazado nos vamos
            if (this.vecinos[i % 6] != null) {
                break;
            }
            // si estamos por el final, va a nulo
            if (resp >= padre.getNumeroCeldas()) {
                break;
            } else {
                resp++;
                this.enlazar(padre.getCeldas()[resp - 1], i % 6);
                padre.getCeldas()[resp - 
                    1].enlazar(padre.getCeldas()[resp - 2], (i + 3 + 1) % 6);
            }
        }
        padre.setUltimoId(resp);

        // encontramos el que tiene el siguiente id
        return (this.id + 1);
    }

    /**
     * @return Returns the id.
     */
    public int getId() {
        return id;
    }

    public void setMinPaso(int i) {
        if (this.paso == 0 || i < this.paso)
            paso = i;
    }

    public void dibujar(Graphics gc) {
        int tamano = padre.getTamanoCelda();
        int tfuente = 9;
        if (tamano > 30)
            tfuente = tamano / 3;
        int textX = x + 2;
        int textY = y + 2 * tfuente;

        gc.setColor(ColorSet.getColor(this.paso % ColorSet.MAXCOLOR));
        gc.fillArc(x, y, tamano, tamano, 0, 360);
        gc.setColor(Color.BLACK);
        gc.drawArc(x, y, tamano, tamano, 0, 360);
        gc.setFont(new Font("Arial", Font.BOLD, tfuente));
        gc.drawString(this.id + "-" + this.paso, textX, textY);
    }

    /**
     * @return Returns the x.
     */
    public int getX() {
        return x;
    }

    /**
     * @param x The x to set.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return Returns the y.
     */
    public int getY() {
        return y;
    }

    /**
     * @param y The y to set.
     */
    public void setY(int y) {
        this.y = y;
    }

    public Celda getVecino(int i) {
        return this.vecinos[i];
    }


}

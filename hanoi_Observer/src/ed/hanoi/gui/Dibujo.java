package ed.hanoi.gui;

import java.awt.Graphics;

public abstract class Dibujo {

    protected int alto;    
    protected int ancho;

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
    
    /**
     * @param gc
     * @param x
     * @param y
     */
    public abstract void dibujar(Graphics gc, int x, int y);
}

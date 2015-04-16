package ed.hanoi.gui;

import ed.hanoi.objects.Disco;

import java.awt.Graphics;

public class DibujoDisco extends Dibujo {

    private Disco objDisco;
    
    /**
     * @param d
     */
    public DibujoDisco(Disco d) {
        objDisco = d;
        setAncho(d.getTamano() * 10);
        setAlto(10);
    }

    /**
     * @param gc
     * @param x
     * @param baseDisco
     */
    public void dibujar(Graphics gc, int x, int baseDisco) {
        gc.fillRect(x - this.getAncho() / 2, baseDisco, getAncho(), getAlto());
    }
}

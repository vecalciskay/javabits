package ed.hanoi.gui;

import ed.hanoi.objects.Disco;
import ed.hanoi.objects.Torre;

import java.awt.Graphics;

import java.util.Iterator;

public class DibujoTorre extends Dibujo{

    private Torre objTorre;    
    

    /**
     * @param t
     */
    public DibujoTorre(Torre t) {
        setObjTorre(t);
        setAlto(200);
        setAncho(5);
    }

    /**
     * @param gc
     * @param x
     * @param y
     */
    public void dibujar(Graphics gc, int x, int y) {
        gc.fillRect(x- this.getAncho() / 2, y, ancho, alto);
        
        int baseDisco = y + this.getAlto();
        Iterator objIteracion = objTorre.getPila().iterator();
        while(objIteracion.hasNext()) {
        
            Disco obj = (Disco)objIteracion.next();            
            DibujoDisco dibujo = new DibujoDisco(obj);
            
            dibujo.dibujar(gc, x, baseDisco);
            baseDisco -= dibujo.getAlto();
        }
    }

    public Torre getObjTorre() {
        return objTorre;
    }

    public void setObjTorre(Torre objTorre) {
        this.objTorre = objTorre;
    }
}

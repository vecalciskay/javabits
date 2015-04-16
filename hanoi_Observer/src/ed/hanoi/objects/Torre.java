package ed.hanoi.objects;

import java.util.Stack;

public class Torre {

    protected int nroTorre;
    
    protected Stack pila;
    
    public Torre(int n) {
        pila = new Stack();
        nroTorre = n;
    }
    
    public void meter(Disco objDisco) {
        this.getPila().push(objDisco);
    }
    
    public Disco sacar() {
        return (Disco)this.getPila().pop();
    }

    public int getNroTorre() {
        return nroTorre;
    }

    public Stack getPila() {
        return pila;
    }
}

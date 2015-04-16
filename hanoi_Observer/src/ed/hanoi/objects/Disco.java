package ed.hanoi.objects;

public class Disco {

    protected int tamano;
    
    public Disco(int t) {
        tamano = t;
    }
    
    public Disco() {
        this(1);
    }

    public int getTamano() {
        return tamano;
    }
}

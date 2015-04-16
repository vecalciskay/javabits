package ed.hanoi.objects;

import java.util.Observable;

import org.apache.log4j.Logger;

public class Hanoi extends Observable {
    private static Logger logger = Logger.getLogger(Hanoi.class);
    
    protected Torre[] torres;
    protected int numeroDiscos;
    
    public Hanoi(int nbDiscos) {
        torres = new Torre[3];
        
        torres[0] = new Torre(1);
        torres[1] = new Torre(2);
        torres[2] = new Torre(3);
        
        for(int i=0; i<nbDiscos; i++) {
            Disco objDisco = new Disco(nbDiscos - i);
            torres[0].meter(objDisco);
        }
        
        this.numeroDiscos = nbDiscos;
    }
    
    public void hacerHanoi(int de, int a, int intermedio, int n) {
        if (n == 1) {
            torres[a].meter(torres[de].sacar());
            logger.debug("Mueve anillo de: " + (de+1) + " a: " + (a+1));
            this.setChanged();
            this.notifyObservers();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) { ; }
            return;
        } 
        
        hacerHanoi(de, intermedio, a, n - 1);
        hacerHanoi(de, a, intermedio, 1);
        hacerHanoi(intermedio, a, de, n - 1);
    }
    
    public static void main(String[] args) {
        Hanoi h = new Hanoi(3);
        h.hacerHanoi(0,2,1,3);
    }
    
    public Torre getTorre(int n) {
        return torres[n];
    }
    
    public void iniciado() {
        this.setChanged();
        this.notifyObservers();
    }

    public void hacer() {
        this.hacerHanoi(0, 2, 1, this.numeroDiscos);
        logger.info("Termino el Hanoi");
    }
    
    public String toString() {
        return "Hanoi de " + numeroDiscos + " discos";
    }
}

package tp4;

import java.awt.Graphics;

public interface Dibujo {

    public void dibujar(Graphics gc);

    public int getWidth();

    public int getHeight();

    public int caminoMasCorto(int de, int a);
}

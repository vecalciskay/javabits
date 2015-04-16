package arbol.obj;

import java.awt.Graphics;

public interface Drawable {

	public void draw(Graphics g, int x, int y);
	
	public int getAncho(Graphics g);
	
	public int getAlto(Graphics g);
}

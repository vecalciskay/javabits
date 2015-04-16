package tp2;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelDibujo extends JPanel {

	private int ancho = 600;

	private int alto = 400;
	
	private static final long serialVersionUID = 1L;
	
	private Dibujo objeto;
	
	public PanelDibujo() {
		objeto = null;
		setPreferredSize(new Dimension(ancho, alto));
	}

	public void cambiarA(int nro, int profundidad) {
		if (nro == Fractal.SIERPINSKY)
			objeto = new Sierpinsky(profundidad);
		else if (nro == Fractal.VONKOCH) 
			objeto = new Vonkoch(profundidad);
		this.repaint();
	}
	
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		if (objeto != null)
			objeto.dibujar(gc);
	}
}

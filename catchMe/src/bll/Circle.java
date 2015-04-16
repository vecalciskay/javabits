package bll;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Circle extends Figure {

	/**
	 * Crea un circulo en la posicion x y de Color c y de tamano estandar. 
	 * El movimiento tiene una direccion al azar y la velocidad es estandar.
	 * @param x
	 * @param y
	 * @param c
	 */
	public Circle(int x, int y, Color c) {
		this.x = x;
		this.y = y;
		this.color = c;
		this.angleDirection = Math.random() * 2.0 * Math.PI;
		this.size = Figure.BASIC_SIZE;
		this.speed = Figure.BASIC_SPEED;
	}
	
	@Override
	public void draw(Graphics2D gc) {
		gc.setColor(this.color);
		
		Shape theCircle = new Ellipse2D.Double(x - size, y - size, 2.0 * size, 2.0 * size);
		gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
	    gc.fill(theCircle);
	}

	@Override
	public boolean isInside(int x, int y) {
		double tx = (double)(this.x - x);
		double ty = (double)(this.y - y);
		
		if ((tx * tx + ty * ty) < (size * size))
			return true;
		return false;
	}

}

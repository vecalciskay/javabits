package bll;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Square extends Figure {

	/**
	 * Crea un cuadrado en la posicion x y de Color c y de tamano estandar. 
	 * El movimiento tiene una direccion al azar y la velocidad es estandar.
	 * @param x
	 * @param y
	 * @param c
	 */
	public Square(int x, int y, Color c) {
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
		
		gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
	    gc.fillRect(x - size, y - size, size * 2, size * 2);
	}

	@Override
	public boolean isInside(int x, int y) {
		if (x > (this.x - size) &&
				x < (this.x + size) &&
				y > (this.y - size) &&
				y < (this.y + size))
			return true;
		return false;
	}

}

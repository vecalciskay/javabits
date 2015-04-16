package bll;

import java.awt.Color;
import java.awt.Graphics2D;

import org.apache.log4j.Logger;

/**
 * El punto X,Y en lo posible es el centro de la figura geometrica
 * @author vladimir
 *
 */
public abstract class Figure {

	private static final Logger logger = Logger.getRootLogger();
	
	public static final int BASIC_SPEED = 2;
	public static final int BASIC_SIZE = 15;
	
	protected int x;
	protected int y;
	protected Color color;
	protected double angleDirection;
	protected int speed;
	protected int size;
	
	public void moveAlongDirection(GameBoard board) {
		double newX = (double)x + (double)speed * Math.cos(angleDirection);
		double newY = (double)y + (double)speed * Math.sin(angleDirection);
		
		x = (int)Math.round(newX);
		y = (int)Math.round(newY);
		
		if ((x - size) < 0 ||
				(x + size) > board.getWidth() ||
				(y - size) < 0 ||
				(y + size) > board.getHeight()) {
			double bounceAngle = calculateBounceAngle();
			logger.debug("Cambio de direccion de " + Math.toDegrees(angleDirection) + " a " + Math.toDegrees(bounceAngle));
			angleDirection = bounceAngle;
		}
	}
	
	private double calculateBounceAngle() {
		double bounceAngle = angleDirection;
		double moduloAngle = 0.0;
		double divideResult = Math.floor( Math.abs(angleDirection)/(Math.PI / 2.0));
		moduloAngle = angleDirection - divideResult * (Math.PI / 2.0);
		if ((int)divideResult % 2 == 0) 
		{
			moduloAngle = Math.PI / 2.0 - moduloAngle;
			bounceAngle = angleDirection + 2.0 * moduloAngle + Math.PI;
		} 
		else 
		{
			bounceAngle = angleDirection - 2.0 * moduloAngle;
		}
		
		return bounceAngle;
	}

	public abstract void draw(Graphics2D gc);
	
	public abstract boolean isInside(int x, int y);
}

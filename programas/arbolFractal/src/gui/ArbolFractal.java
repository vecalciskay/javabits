package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import org.apache.log4j.Logger;

public class ArbolFractal {
	
	private static final Logger logger = Logger.getRootLogger();
	
	private double factorLengthN;
	private double angle;
	private double widthTrunk;
	
	public static Color[] colors = { 
		new Color(96, 48, 0),
		new Color(142, 71, 0),
		new Color(159, 108, 23),
		new Color(155, 163, 20),
		new Color(65, 157, 26),
		new Color(35, 86, 14)
	};
	
	public ArbolFractal() {

		this.setDefaults();
		
		logger.info("Using default values angle: " + angle + " and factorLength: " + factorLengthN);
	}
	
	public ArbolFractal(double factor, double angleBranch, double widthFactor) {
		factorLengthN = factor;
		angle = angleBranch;
		widthTrunk = widthFactor;
		
		logger.info("Using values angle: " + angle + " and factorLength: " + factorLengthN);
	}

	public void draw(double x1, double y1, double x2, double y2, int n, int maxN,
			Graphics gc) {

		int idxColor = 0;
		// maxN --> 7
		// n: 7 -> 5, 6 -> 4, 5->3, 4->2, 3->1, 2->0, 1->0
		
		// maxN -->3
		// n: 3->5, 2->4, 1->3
		idxColor = 5 - (maxN - n);
		if (idxColor < 0)
			idxColor = 0;
		
		int stroke = (int)(n * widthTrunk);
		gc.setColor(colors[5 - idxColor]);
		Graphics2D g2 = (Graphics2D) gc;
        g2.setStroke(new BasicStroke(stroke));
        g2.draw(new Line2D.Float((int) x1, (int) y1, (int) x2, (int) y2));

		if (n <= 1)
			return;
		
		logger.debug("Calculating points for (" + x1 + ", " + y1 + ") - (" + x2 + ", " + y2 + ")");

		double h = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)) * factorLengthN;
		double angleLine = 0.0;
		if (x2 - x1 == 0.0) {
			if (y2 <= y1)
				angleLine = Math.PI / 2;
			else
				angleLine = -1.0 * Math.PI / 2;
		} else {
			double tangent_y = y1 - y2;
			double tangent_x = x2 - x1;
			double tangent = tangent_y / tangent_x;
			angleLine = Math.atan(tangent);		
			
			if (tangent_x < 0 && tangent_y >= 0)
				angleLine = Math.PI + angleLine;
			if (tangent_x < 0 && tangent_y < 0)
				angleLine = Math.PI + angleLine;
			
			logger.info("Tangent: " + tangent_y + " / " + tangent_x + " and Angle = " + Math.toDegrees(angleLine));
		}
		
		double finalAngle = angleLine + angle;
		double finalY = y2 - h * Math.sin(finalAngle);
		double finalX = x2 + h * Math.cos(finalAngle);
		
		logger.info("Branch left: (" + finalX + ", " + finalY + ")");
		
		draw(x2, y2, finalX, finalY, n-1, maxN, gc);
		
		finalAngle = angleLine - angle;
		finalY = y2 - h * Math.sin(finalAngle);
		finalX = x2 + h * Math.cos(finalAngle);
		
		logger.info("Branch right: (" + finalX + ", " + finalY + ")");
		
		draw(x2, y2, finalX, finalY, n-1, maxN, gc);
	}

	public double getFactorLengthN() {
		return factorLengthN;
	}

	public void setFactorLengthN(double factorLengthN) {
		this.factorLengthN = factorLengthN;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getWidthTrunk() {
		return widthTrunk;
	}

	public void setWidthTrunk(double widthTrunk) {
		this.widthTrunk = widthTrunk;
	}
	
	public void setDefaults() {
		factorLengthN = 0.8;
		angle = Math.PI / 8.0;
		widthTrunk = 3.0;
	}
}

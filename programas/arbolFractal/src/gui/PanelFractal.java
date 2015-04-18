package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

public class PanelFractal extends JPanel {

	private static final Logger logger = Logger.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int w;
	private int h;
	
	
	public PanelFractal(int width, int height) {
		w = width;
		h = height;
	}

	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	public void paintComponent(Graphics gc) {

		logger.info("Dibuja el cielo y la tierra");
		drawSky(gc);
		drawLand(gc);
		
		ArbolFractal treeDrawer = new ArbolFractal();

		// Arbol muy pequeno atras
		treeDrawer.setDefaults();
		treeDrawer.setWidthTrunk(0.8);
		treeDrawer.draw(599, 120, 600, 103, 6, 6, gc);
		
		// Arbol a la izquierda extrema, mediano, largo
		treeDrawer.setDefaults();
		treeDrawer.setWidthTrunk(2.0);
		treeDrawer.setFactorLengthN(0.8);
		treeDrawer.setAngle(Math.PI / 9.0);
		treeDrawer.draw(91, 400, 90, 320, 7, 7, gc);
		
		// Arbol a la derecha extrema, mediano, muy frondoso
		treeDrawer.setDefaults();
		treeDrawer.setWidthTrunk(2.0);
		treeDrawer.setFactorLengthN(0.75);
		treeDrawer.setAngle(Math.PI / 7.0);
		treeDrawer.draw(751, 370, 753, 290, 8, 8, gc);
				
		// Arbol grande del frente
		treeDrawer.setFactorLengthN(0.75);
		treeDrawer.setAngle(Math.PI / 8.0);
		treeDrawer.draw(335, 554, 330, 408, 9, 9, gc);
		
		// arbol adelante, solamente las ramas
		treeDrawer.setDefaults();
		treeDrawer.setWidthTrunk(3.0);
		treeDrawer.setFactorLengthN(0.6);
		treeDrawer.setAngle(Math.PI / 6.0);
		treeDrawer.draw(510, 531, 510, 432, 3, 3, gc);
	}

	private void drawLand(Graphics gc) {
		gc.setColor(new Color(0, 255, 0));
		gc.fillRect(0, 100, w, h - 100);
	}

	private void drawSky(Graphics gc) {
		gc.setColor(Color.cyan);
		gc.fillRect(0, 0, w, 100);
	}

	
}

package parser.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import parser.obj.ArbolBinario;
import parser.obj.Aritmetico;

public class PanelDibujo extends JPanel implements Observer {
	private final static Logger logger = LogManager.getRootLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArbolBinario<Aritmetico> theTree;
	/**
	 * Create the panel.
	 */
	public PanelDibujo() {
		theTree = null;
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		
		this.repaint();
	}

	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		
		if (theTree == null) {
			logger.warn("No chessboard to draw");
			return;
		}
		
		Dibujador treeDrawer = new Dibujador(theTree);
		treeDrawer.dibujar((Graphics2D) gc);
	}
	
	public ArbolBinario<Aritmetico> getTheTree() {
		return theTree;
	}
	public void setTheTree(ArbolBinario<Aritmetico> theTree) {
		this.theTree = theTree;
	}
	public Dimension getPreferredSize() {
		return new Dimension(1000,2000);
	}
}

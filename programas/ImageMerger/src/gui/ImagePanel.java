package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import objects.ImagePixels;

public class ImagePanel extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private ImagePixels imagen;
	public ImagePanel(ImagePixels img) {
		imagen = img;
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600,400);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (imagen != null) {
			imagen.draw(g);
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		repaint();
	}
}

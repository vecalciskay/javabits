package dijkstra.gui.controlador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dijkstra.edd.coord.GrafoCoordenada;

public abstract class Controlador implements MouseListener, MouseMotionListener {
	
	protected GrafoCoordenada modelo;
	
	@Override
	public void mouseDragged(MouseEvent e) { ; }

	@Override
	public void mouseReleased(MouseEvent e) { ; }
	
	@Override
	public void mousePressed(MouseEvent e) { ;}


	@Override
	public void mouseClicked(MouseEvent e) { ; }
	
	@Override
	public void mouseEntered(MouseEvent e) { ; }

	@Override
	public void mouseExited(MouseEvent e) { ; }

	@Override
	public void mouseMoved(MouseEvent e) { ; }


}

package bn.gui;

import java.awt.event.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bn.model.Tablero;

/**
 * Esta es una clase de ayuda que nos permite utilizar el mouse en vez del teclado 
 * para el disparo de la batalla naval.
 * @author Vladimir
 *
 */
public class Disparador implements MouseListener, MouseMotionListener {
	
	private static final Logger logger = LogManager.getRootLogger();

	private Tablero elTablero;
	private String coordenadasAEnviar;
	private int filaEnviar;
	private int colEnviar;
	
	public Disparador() {
		elTablero = null;
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// Not used
	}

	/**
	 * Este utilizamos para saber cuando se está moviendo
	 */
	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (elTablero == null) {
			logger.warn("Tablero todavia no fue asignado");
			return;
		}
		
		int[] filaCol = new int[2];
		coordenadasAEnviar = elTablero.obtenerCoordenadas(arg0.getX(), arg0.getY(), filaCol);
		filaEnviar = filaCol[0];
		colEnviar = filaCol[1];
		
		if (filaEnviar >= 6 && colEnviar >= 8)
			return;
		
		elTablero.setPosibleTiro(filaEnviar, colEnviar);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// Not used
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Not used
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Not used
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// Not used
	}

	/**
	 * Este se utiliza para saber dónde quiere disparar
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// 
		if (elTablero == null) {
			logger.warn("Tablero todavia no fue asignado");
			return;
		}
		
		int[] filaCol = new int[2];
		coordenadasAEnviar = elTablero.obtenerCoordenadas(arg0.getX(), arg0.getY(), filaCol);
		filaEnviar = filaCol[0];
		colEnviar = filaCol[1];
		
		if (filaEnviar >= 6 && colEnviar >= 8)
			return;
		
		if (elTablero.yaDisparoEn(filaEnviar, colEnviar))
			return;
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				elTablero.jugarMiTurno(coordenadasAEnviar, filaEnviar, colEnviar);
			}});
		
		t.start();
	}

	public Tablero getElTablero() {
		return elTablero;
	}

	public void setElTablero(Tablero elTablero) {
		this.elTablero = elTablero;
	}

}

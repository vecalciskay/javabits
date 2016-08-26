package pixelAnimator.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import pixelAnimator.obj.Animator;

/**
 * Panel donde se muestra toda la animación. Este panel tiene un tamano maximo de 800x600, por 
 * ello las imagenes que se cargan no pueden ser mas grandes que eso
 * @author Vladimir
 *
 */
public class JAnimatorPanel extends JPanel implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getRootLogger();
	
	public static final int MAXANCHO = 800;
	public static final int MAXALTO = 600;
	
	private Animator objAnimator;
	
	/**
	 * El constructor realmente esta vacio, se asigna el animaodr cada vez que es
	 * llamado el update y el dibujo solamente se hace cuando se llama a repaint.
	 */
	public JAnimatorPanel() {
		objAnimator = null;
	}
	
	/**
	 * Este metodo es necesario para que cuando arme el panel automaticamente se ajuste al tamano
	 * que le estamos indicando. Este metodo es sobreescrito de la clase padre JPAnel.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(MAXANCHO,MAXALTO);
	}
	
	/**
	 * Al momento de hacer update, cuando es llamado desde el observable, lo unico que hay que hacer
	 * es repintar todo el objeto que está siendo observado
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		logger.info("LLmaado a update con el objeto " + arg0);
		
		if (arg0 instanceof Animator) {
			objAnimator = (Animator)arg0;
		}
		
		repaint();
	}
	
	/**
	 * Esete metodo dibuja todo lo que se tiene que ver en la pantalla. es muy importante la primera linea
	 * que se hace cargo de dibujar todo lo que antes había encima del componente.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (objAnimator == null)
			return;
		
		objAnimator.dibujar(g);
	}

}

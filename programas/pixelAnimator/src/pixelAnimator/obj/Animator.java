package pixelAnimator.obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Esta clase domina todo el concepto de la animación y las imagenes que se deben pintar en el panel.
 * La construcción es la siguiente: El objeto Animator tiene un objeto de tipo Dibujo y un objeto de tipo 
 * AnimacionAparicion. El objeto de tipo AnimacionAparicion es el que determina cual es la imagen 
 * que se debe pintar dependiendo en el paso en el cual se encuentra en el momento en el que se la pide
 * 
 * si el objeto de tipo AnimacionAparicion es nulo entonces l aimagen que se muestra es solamente la imagen
 * del dibujo original.
 *  
 * @author Vladimir
 *
 */
public class Animator extends Observable {
	
	private static final Logger logger = LogManager.getRootLogger();

	/**
	 * Aquí esta cargada la imagen original.
	 */
	private Dibujo original;
	
	/**
	 * Este es el objeto que realiza directametne la animación y sabe cual es la imagen que se debe 
	 * pintar.
	 */
	private AnimacionAparicion animador;
	
	/**
	 * Crea un objeto que maneja el momento en que debe iniciar o no una animacion. Tambien se debe colocar
	 * la animación que se utilziará
	 * @param original Dibujoo original que se utiliza 
	 * @param animacionId El codigo de la animación o nulo. Los codigo aceptados son IZQUIERDADERECHA
	 */
	public Animator(Dibujo original, String animacionId) {
		this.original = original;
		
		setAnimador(animacionId);
	}
	
	/**
	 * Aquí se indica toda la logica para pintar el dibujo. Si nohay aniacion se dibuja el 
	 * original, sino, entonces se dibuja la transición que se saca del objeto AnimacionAparicion
	 * @param g
	 */
	public void dibujar(Graphics g) {
		
		if (animador == null) {
			
			original.dibujar(g);
			return;
		}
		
		animador.getIntermedio().dibujar(g);
	}
	
	public void setAnimador(String id) {
		if (id == null) {
			logger.info("Creando un animador sin animacion");
			animador = null;
			return;
		}
		
		if (id == "IZQUIERDADERECHA") {
			logger.info("Creando un animador de izquierda a derecha");
			animador = new AnimacionIzquierdaDerecha(this, original);			
		}
	}

	/**
	 * Crea una imagen a partir de un archivo
	 * @param imgFile
	 * @throws IOException
	 */
	public void cargarImagen(File imgFile) throws IOException {
		BufferedImage img = null;
        try {
            img = ImageIO.read(imgFile);
        } catch (IOException e) {
        	logger.error("No pudo leer imagen desde archivo " + imgFile.getAbsolutePath());
        	throw e;
        }
        
        if (original == null) {
        	original = new Dibujo(img.getWidth(), img.getHeight());
        }
        original.cargarDibujo(img);
	}
	
	/**
	 * Wrapper para notificar desde cualquier lugar a los observadores de este objeto
	 */
	public void notificar() {
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Este metodo es el que hace realmente el trabajo en un hilo diferente para cambiar 
	 * la imagen que va a ser desplegada cada vez
	 */
	public void initAnimacion() {
		if (animador == null) {
			logger.warn("No hay objeto animacion que ejecutar, sale");
			return;
		}
		
		Thread worker = new Thread(animador);
		worker.start();
	}
}

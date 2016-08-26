package pixelAnimator.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Esta clase representa una matriz de pixeles que se puede pintar.
 * @author Vladimir
 *
 */
public class Dibujo {

	private static final Logger logger = LogManager.getRootLogger();

	private int[][] pixeles;
	private int ancho;
	private int alto;
	private String archivo;
	
	/**
	 * Constructor a partir de otro dibujo, esto es utilizado cuando queremos crear la imagen
	 * intermedia a partir de la imagen original.
	 * @param origen
	 */
	public Dibujo(Dibujo origen) {
		this.ancho = origen.getAncho();
		this.alto = origen.getAlto();
		pixeles = new int[ancho][alto];
		this.archivo = "";
	}

	/**
	 * Constructor con el tamano original de la imagen, se debe inicializar siempre la lista de
	 * pixeles
	 * @param width
	 * @param height
	 */
	public Dibujo(int width, int height) {
		this.ancho = width;
		this.alto = height;
		pixeles = new int[ancho][alto];
		this.archivo = "";
	}

	public int[][] getPixeles() {
		return pixeles;
	}

	public void setPixeles(int[][] pixeles) {
		this.pixeles = pixeles;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	/**
	 * El metodo dibujar lo unico que hace es ir pixel por pixel y dibujar un punto en ese lugar
	 * con las coordenadas correctas.
	 * @param g
	 */
	public void dibujar(Graphics g) {
		for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                g.setColor(new Color(pixeles[i][j]));
                g.drawLine(i, j, i, j);
            }
        }
	}

	/**
	 * Aquí se carga el dibujo a partir de una imagen de BufferedImage, la carga desde el archivo se realiza
	 * en otro lugar.
	 * @param img
	 */
	public void cargarDibujo(BufferedImage img) {

        ancho = img.getWidth();
        alto = img.getHeight();
        pixeles = new int[ancho][alto];

        for (int row = 0; row < ancho; row++) {
            for (int col = 0; col < alto; col++) {
                pixeles[row][col] = img.getRGB(row, col);
            }
        }
        
        logger.info("Se leyo una imagen de " + ancho + "x" + alto + " en el dibujo");
	}

	/**
	 * Borra la imagen pero mantiene el tamano
	 */
	public void reset() {
		pixeles = new int[ancho][alto];
	}
}

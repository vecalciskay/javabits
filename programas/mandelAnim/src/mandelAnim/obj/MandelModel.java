package mandelAnim.obj;

import java.awt.Graphics;
import java.util.Observable;

import javax.swing.JOptionPane;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * @author Vladimir
 *
 */
public class MandelModel extends Observable implements Runnable {
	private static final Logger logger = LogManager.getRootLogger();

	private Complejo zc;
	private double radio;
	private int w;
	private int h;
	private Dibujo imagen;
	private int[][] divergente;
	private Complejo[][] zn;

	private int running;

	/**
	 * 
	 */
	public MandelModel() {
		reset();
	}
	
	public String toString() {
		return "Centro: " + zc.toString() + " radio: " + radio;
	}

	public void comenzarMandel() {
		logger.info("Comienza la animación de Mandelbrot con datos " + toString());
		
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * 
	 * @param g
	 */
	public void dibujar(Graphics g) {
		if (imagen != null)
			imagen.dibujar(g);
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Dibujo getImagen() {
		return imagen;
	}

	public void setImagen(Dibujo imagen) {
		this.imagen = imagen;
	}

	public void zoomEn(int x, int y) {
		logger.info("Hace zoom en la posición x,y: " + x + "x" + y);
		
		zc = z0(x, y);
		radio = radio * 0.25;
	}

	public void reset() {
		logger.info("Mandelbrot vielve a tener los datos x defecto");
		
		zc = new Complejo(0,0);
		radio = 1.5;

		w = 600;
		h = 600;

		resetDibujo();
		
		setZn();

		running = 0;
	}

	public boolean isRunning() {
		return running > 0;
	}

	@Override
	public void run() {
		if (running > 0)
			return;

		animacionMandel();
	}

	private void animacionMandel() {
		running = 1;
		
		int nroNuevosDivergentes = 0;
		
		for (int c = 0; c < 256; c++) {
			nroNuevosDivergentes = 0;
			
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					if (divergente[i][j] > 0)
						continue;
					
					Complejo zn_1 = zn[i][j];
					zn[i][j] = zn_1.mult(zn_1).add(z0(i,j));
					if (zn[i][j].abs() > 2.0) {
						divergente[i][j] = 1;
						nroNuevosDivergentes++;
					}
					imagen.setPixel(i,j,c);
				}	
			}
			
			this.setChanged();
			this.notifyObservers();
			
			logger.info("Se hizo la iteracion z" + c + " hay " + nroNuevosDivergentes + " nuevos divergentes" );
			
			try { Thread.sleep(20); } catch(Exception q) { }
		}
		
		logger.info("Termina el proceso de mandelbrot");
		JOptionPane.showMessageDialog(null, "Ya termino el mandelbrot");
		running = 0;
	}

	private Complejo z0(int i, int j) {
		double xmin = zc.getReal() - radio;
		double xmax = zc.getReal() + radio;
		double ymin = zc.getImg() - radio;
		double ymax = zc.getImg() + radio;
		
		double stepX = (xmax - xmin) / (double)w;
		double stepY = (ymax - ymin) / (double)h;
		
		return new Complejo(xmin +  i * stepX, ymin + j * stepY);
	}

	public void setZn() {
		logger.info("Los numeros complejos de base");
		
		zn = new Complejo[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				zn[i][j] = z0(i,j);
			}
		}
	}

	public void resetDibujo() {
		imagen = new Dibujo(w, h);

		divergente = new int[w][h];
	}
}

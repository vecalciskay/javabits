package pixelAnalyzer.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Observable;

import pixelAnalyzer.obj.tool.Herramienta;
import pixelAnalyzer.obj.tool.Lapiz;

public class Dibujo extends Observable {
	
	private int[][] pixeles;
	private int ancho;
	private int alto;
	
	private Herramienta herramientaActual;
	private Contexto contextoActual;
	private int seleccionado;
	private String nombre;

	public Dibujo(int w, int h) {
		
		pixeles = new int[w][h];
		ancho = w;
		alto = h;
		
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				pixeles[i][j] = 255*256*256+255*256 + 255;
			}
		}
		
		herramientaActual = new Lapiz();
		contextoActual = new Contexto();
		seleccionado = 1;
		
		nombre = "Sin Titulo " + seleccionado;
	}

	public int[][] getPixeles() {
		return pixeles;
	}



	public void setPixeles(int[][] pixeles) {
		this.pixeles = pixeles;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public Herramienta getHerramientaActual() {
		return herramientaActual;
	}

	public void setHerramientaActual(Herramienta herramientaActual) {
		this.herramientaActual = herramientaActual;
	}

	public Contexto getContextoActual() {
		return contextoActual;
	}

	public void setContextoActual(Contexto contextoActual) {
		this.contextoActual = contextoActual;
	}

	public void dibujar(Graphics g) {

		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				g.setColor(new Color(pixeles[i][j]));
				g.drawLine(i, j, i, j);
			}
		}
		
	
		herramientaActual.dibujar(g);
		contextoActual.dibujar(g);
	}
	
	public int getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(int seleccionado) {
		this.seleccionado = seleccionado;
	}

	public void setPixel(int i, int j, int c) {
		pixeles[i][j] = c;
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setPixel(int i, int j, int c, boolean sinNotificar) {
		if (sinNotificar) {
			pixeles[i][j] = c;
			return;
		}
		setPixel(i, j, c);
	}
	
	public void setPixel(int i, int j, Color c) {
		
		pixeles[i][j] = c.getRed() * 256 * 256 + c.getGreen() * 256 + c.getBlue();

		this.setChanged();
		this.notifyObservers();
	}

	public int getPixel(int i, int j) {
		return pixeles[i][j];
	}
	
	public void reset() {
		pixeles = new int[ancho][alto];
		
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				pixeles[i][j] = 255*256*256+255*256 + 255;
			}
		}
		this.setChanged();
		this.notifyObservers();
	}

	public void hacerDrag(int x, int y) {
		herramientaActual.dragHasta(this, x, y);
	}
	
	public void hacerClic(int x, int y) {
		herramientaActual.clicEn(this, x, y);
	}

	public void notificar() {
		this.setChanged();
		this.notifyObservers();
	}

	public void mover(int x, int y) {
		herramientaActual.mueveA(this, x, y);
	}

	public void soltarClic(MouseEvent arg0, int x, int y) {
		herramientaActual.soltar(this, x, y);
	}
}

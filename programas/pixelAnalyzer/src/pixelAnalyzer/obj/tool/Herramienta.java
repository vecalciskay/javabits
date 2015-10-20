package pixelAnalyzer.obj.tool;

import java.awt.Graphics;

import pixelAnalyzer.obj.Dibujo;

public abstract class Herramienta {

	protected String nombre;
	protected int lastX;
	protected int lastY;
	
	public abstract void clicEn(Dibujo obj, int x, int y);
	public abstract void dragHasta(Dibujo obj, int x, int y);
	public abstract void mueveA(Dibujo obj, int x, int y);
	public abstract void dibujar(Graphics g);
	public abstract void soltar(Dibujo dibujo, int x, int y);
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getLastX() {
		return lastX;
	}
	public void setLastX(int lastX) {
		this.lastX = lastX;
	}
	public int getLastY() {
		return lastY;
	}
	public void setLastY(int lastY) {
		this.lastY = lastY;
	}
}

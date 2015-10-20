package pixelAnalyzer.obj;

import java.awt.Color;
import java.awt.Graphics;

public class Contexto {

	private Color elColor;
	private int grosor;
	
	public Contexto() {
		elColor = Color.black;
		grosor = 1;
	}

	public Color getElColor() {
		return elColor;
	}

	public void setElColor(Color elColor) {
		this.elColor = elColor;
	}

	public int getGrosor() {
		return grosor;
	}

	public void setGrosor(int inputGrosor) {
		switch(inputGrosor) {
		case 1:
		case 2:
		case 4:
		case 6:
		case 9:
			grosor = inputGrosor;
			break;
		default:
			grosor = 1;
		}
	}

	public int getElColorInt() {
		return elColor.getRed()*256*256 + elColor.getGreen()*256 + elColor.getBlue();
	}

	public String getColorForDisplay() {
		return elColor.getRed() + "," + elColor.getGreen() + "," + elColor.getBlue();
	}

	public void dibujar(Graphics g) {
		;
	}
	
	
}

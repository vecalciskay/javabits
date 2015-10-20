package pixelAnalyzer.obj.tool;

import java.awt.Graphics;

import pixelAnalyzer.obj.Dibujo;

public class Lapiz extends Herramienta {
	
	public Lapiz() {
		nombre = "Lápiz";
		lastX = 0;
		lastY = 0;
	}

	@Override
	public void clicEn(Dibujo obj, int x, int y) {
		
	}

	@Override
	public void dragHasta(Dibujo obj, int x, int y) {
		switch(obj.getContextoActual().getGrosor()) {
		case 1:
			if (obj.getAncho() > x && obj.getAlto() > y)
				obj.setPixel(x, y, obj.getContextoActual().getElColor());
			break;
		case 2:
			if (obj.getAncho() > x && obj.getAlto() > y)
				obj.setPixel(x, y, obj.getContextoActual().getElColor());
			if (obj.getAncho() > (x+1) && obj.getAlto() > y)
				obj.setPixel(x+1, y, obj.getContextoActual().getElColor());
			break;
		case 4:
			if (obj.getAncho() > x && obj.getAlto() > y)
				obj.setPixel(x, y, obj.getContextoActual().getElColor());
			if (obj.getAncho() > (x+1) && obj.getAlto() > y)
				obj.setPixel(x+1, y, obj.getContextoActual().getElColor());
			if (obj.getAncho() > x && obj.getAlto() > (y+1))
				obj.setPixel(x, y+1, obj.getContextoActual().getElColor());
			if (obj.getAncho() > (x+1) && obj.getAlto() > (y+1))
				obj.setPixel(x+1, y+1, obj.getContextoActual().getElColor());
			break;
		case 6:
			break;
		case 9:
			break;
		default:
			if (obj.getAncho() > x && obj.getAlto() > y)
				obj.setPixel(x, y, obj.getContextoActual().getElColor());
			break;
		}
		
	}

	@Override
	public void mueveA(Dibujo obj, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dibujar(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void soltar(Dibujo dibujo, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}

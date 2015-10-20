package pixelAnalyzer.obj.tool;

import java.awt.Color;
import java.awt.Graphics;

import pixelAnalyzer.obj.Dibujo;

public class Linea extends Herramienta {

	private static final int NOPOINT = 0;
	private static final int STARTPOINT = 1;

	private int estado = NOPOINT;

	private int xDe, xA, yDe, yA;
	
	public Linea() {
		nombre = "Línea";
	}

	@Override
	public void clicEn(Dibujo obj, int x, int y) {
		if (estado == NOPOINT) {

			xDe = x;
			yDe = y;

			xA = -1;
			yA = -1;

			estado = STARTPOINT;
			return;
		}
		if (estado == STARTPOINT) {

			xA = x;
			yA = y;

			// Ahora si se hace la linea
			hacerLaLinea(obj, xDe, yDe, xA, yA);
			estado = NOPOINT;

			obj.notificar();
		}
	}

	private void hacerLaLinea(Dibujo obj, int xDe2, int yDe2, int xA2, int yA2) {
		int difX = xA2 - xDe2;
		int difY = yA2 - yDe2;
		if ((difX * difX + difY * difY) <= 2) {
			switch (obj.getContextoActual().getGrosor()) {
			case 1:
				obj.setPixel(xDe2, yDe2, obj.getContextoActual()
						.getElColorInt(), true);
				break;
			case 2:
				obj.setPixel(xDe2, yDe2, obj.getContextoActual()
						.getElColorInt(), true);
				obj.setPixel(xDe2 + 1 > obj.getAncho() ? xDe2 : xDe2 + 1, yDe2,
						obj.getContextoActual().getElColorInt(), true);
				break;
			case 4:
				obj.setPixel(xDe2, yDe2, obj.getContextoActual()
						.getElColorInt(), true);
				obj.setPixel(xDe2 + 1 > obj.getAncho() ? xDe2 : xDe2 + 1, yDe2,
						obj.getContextoActual().getElColorInt(), true);
				obj.setPixel(xDe2, yDe2 + 1 > obj.getAlto() ? yDe2 : yDe2 + 1,
						obj.getContextoActual().getElColorInt(), true);
				obj.setPixel(xDe2 + 1 > obj.getAncho() ? xDe2 : xDe2 + 1,
						yDe2 + 1 > obj.getAlto() ? yDe2 : yDe2 + 1, obj
								.getContextoActual().getElColorInt(), true);
				break;
			default:
				break;
			}
			return;
		}

		int interX = xDe2 + (xA2 - xDe2) / 2;
		int interY = yDe2 + (yA2 - yDe2) / 2;

		hacerLaLinea(obj, xDe2, yDe2, interX, interY);
		hacerLaLinea(obj, interX, interY, xA2, yA2);
	}

	@Override
	public void dragHasta(Dibujo obj, int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mueveA(Dibujo obj, int x, int y) {
		xA = x;
		yA = y;
		obj.notificar();
	}

	@Override
	public void dibujar(Graphics g) {
		if (estado == STARTPOINT) {
			g.setColor(new Color(0,0,0,127));
			g.drawLine(xDe, yDe, xA, yA);
		}
	}

	@Override
	public void soltar(Dibujo dibujo, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}

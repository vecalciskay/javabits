package tp2;

import java.awt.Graphics;

public class Vonkoch extends Fractal {

	public Vonkoch(int prof) {
		this.profundidad = prof;
		this.nombre = "Von Koch";
	}

	public void dibujar(Graphics g) {
		hacerVonKoch(10, 150, 400, 150, this.profundidad, g);
	}

	public void hacerVonKoch(int x0, int y0, int x3, int y3, int n, Graphics gc) {
		if (n == 1) {
			gc.drawLine(x0, y0, x3, y3);
		} else {
			double angle = Math.atan2(y3 - y0, x3 - x0);

			double dx1 = (double) (x3 - x0) / 3.0;
			double dy1 = (double) (y3 - y0) / 3.0;
			double distance3 = Math.sqrt((y3 - y0) * (y3 - y0) + (x3 - x0)
					* (x3 - x0)) / 3.0;
			int x1 = x0 + (int) dx1;
			int y1 = y0 + (int) dy1;
			int x2 = x0 + (int) (2.0 * dx1);
			int y2 = y0 + (int) (2.0 * dy1);

			int x4 = (int) (x1 + distance3 * Math.cos(angle - Math.PI / 3.0));
			int y4 = (int) (y1 + distance3 * Math.sin(angle - Math.PI / 3.0));

			hacerVonKoch(x0, y0, x1, y1, n - 1, gc);
			hacerVonKoch(x1, y1, x4, y4, n - 1, gc);
			hacerVonKoch(x4, y4, x2, y2, n - 1, gc);
			hacerVonKoch(x2, y2, x3, y3, n - 1, gc);
		}
	}
}

package objects;

public class ImageColor {
	
	private int color;

	public ImageColor(int pixel) {
		color = pixel;
	}
	
	public ImageColor(int r, int g, int b) {
		color = 0;
		setColor(r, g, b);
	}

	public void percentage(double percentageImage) {
		int r = getR();
		int g = getG();
		int b = getB();
		
		double dr = (double)r * percentageImage / 100.0;
		r = (int)dr;
		double dg = (double)g * percentageImage / 100.0;
		g = (int)dg;
		double db = (double)b * percentageImage / 100.0;
		b = (int)db;
		
		setColor(r,g,b);
	}

	private void setColor(int r, int g, int b) {
		color = (r << 16) | (g << 8) | b;
	}

	public ImageColor add(ImageColor c2) {
		int r1 = getR();
		int g1 = getG();
		int b1 = getB();
		
		int r2 = c2.getR();
		int g2 = c2.getG();
		int b2 = c2.getB();
		
		int r = (r1 + r2 > 255) ? 255 : r1 + r2;
		int g = (g1 + g2 > 255) ? 255 : g1 + g2;
		int b = (b1 + b2 > 255) ? 255 : b1 + b2;
		
		return new ImageColor(r,g,b);
	}

	public int getR() {
		return (0x00ff0000 & color) >> 16;
	}
	
	public int getG() {
		return (0x0000ff00 & color) >> 8;
	}
	
	public int getB() {
		return (0x000000ff & color);
	}

	public int getIntColor() {
		return color;
	}
}

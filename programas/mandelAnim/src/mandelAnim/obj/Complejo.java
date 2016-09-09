package mandelAnim.obj;

public class Complejo {
	private double real;
	private double img;

	public Complejo(double r, double i) {
		real = r;
		img = i;
	}
	
	public Complejo add(Complejo z) {
		return new Complejo(z.getReal() + this.real, z.getImg() + this.img);
	}
	
	/**
	 * (x1,y1) x (x2,y2) = x1x2 + y1x2i + y2x1i - y1y2
	 * @param z
	 * @return
	 */
	public Complejo mult(Complejo z) {
		double newReal = z.getReal()*this.real - z.getImg() * this.img;
		double newImg = z.getReal()*this.img + z.getImg() * this.real;
		
		return new Complejo(newReal, newImg);
	}
	
	public double abs() {
		return Math.sqrt(real*real + img*img);
	}

	public double getReal() {
		return real;
	}

	public void setReal(double real) {
		this.real = real;
	}

	public double getImg() {
		return img;
	}

	public void setImg(double img) {
		this.img = img;
	}
}

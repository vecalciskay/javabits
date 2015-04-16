package tableronet.modelo;

/**
 * Esta clase representa un punto y solamente tiene coordenadas x y y
 * @author Vladimir
 *
 */
public class Punto {

	private int x;
	private int y;
	
	public Punto(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * El punto que llega esta en formato x,y
	 * @param p
	 * @throws Exception 
	 */
	public Punto(String p) throws Exception {
		String[] coordenadas = p.split(",");
		if (coordenadas.length != 2)
			throw new Exception("Mal formato de las coordendas " + p + " y debe ser x,y");
		
		this.x = Integer.MIN_VALUE;
		this.y = Integer.MIN_VALUE;
		
		try {
			x = Integer.parseInt(coordenadas[0]);
			y = Integer.parseInt(coordenadas[1]);
		} catch (Exception e) {
			throw new Exception("La coordenada x y/o y deben ser numeros enteros positivos");
		}
	}
	
	public String toString() {
		return x + "," + y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

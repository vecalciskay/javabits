package dijkstra.edd.coord;

public class Coordenada {

	protected int x;
	protected int y;
	
	public Coordenada(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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
	
	public double distancia(Coordenada c2) {
		int dx = c2.getX() - this.x;
		int dy = c2.getY() - this.y;
		
		double result = Math.sqrt((double)(dx*dx + dy*dy));
		
		return result;
	}

	public boolean tienePunto(int x2, int y2) {
		return ((x - GrafoCoordenada.RADIONODO) < x2 &&
				(x + GrafoCoordenada.RADIONODO) > x2 &&
				(y - GrafoCoordenada.RADIONODO) < y2 &&
				(y + GrafoCoordenada.RADIONODO) > y2);
	}
}

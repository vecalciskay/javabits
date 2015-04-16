package tp2;

public abstract class Fractal implements Dibujo {
	
	public static final int VONKOCH = 1;
	public static final int SIERPINSKY = 2;

	protected int profundidad;
	protected String nombre;
	/**
	 * @return Returns the profundidad.
	 */
	public int getProfundidad() {
		return profundidad;
	}
	/**
	 * @param profundidad The profundidad to set.
	 */
	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	/**
	 * @return Returns the nombre.
	 */
	public String getNombre() {
		return nombre;
	}
}

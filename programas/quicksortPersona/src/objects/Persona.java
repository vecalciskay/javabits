package objects;

public class Persona implements Comparable<Persona> {

	private int altura;
	private String nombre;
	
	
	
	public Persona(int altura, String nombre) {
		super();
		this.altura = altura;
		this.nombre = nombre;
	}

	@Override
	public int compareTo(Persona arg0) {
		if (altura > arg0.getAltura())
			return 1;
		if (altura < arg0.getAltura())
			return -1;
		
		return nombre.compareTo(arg0.getNombre());
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

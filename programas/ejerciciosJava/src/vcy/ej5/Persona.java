package vcy.ej5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Persona {

	private String nombre;
	private String apellido;
	private String direccion;
	private String email;

	public Persona(String nom, String ape, String direc) {
		this.nombre = nom;
		this.apellido = ape;
		this.direccion = direc;
		extractEmail();
	}

	private void extractEmail() {
		Pattern patronValidacion = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
		Matcher analizador = patronValidacion.matcher(direccion);

		while (analizador.find()) {
			this.email = analizador.group();
		}
	}

	public String apellidoComaNombre() {
		return apellido + ", " + nombre;
	}

	public String nombreAlReves() {
		StringBuilder result = new StringBuilder();
		for (int i = nombre.length()-1; i >=0 ; i--) {
			result.append(nombre.charAt(i));
		}
		return result.toString();
	}

	public boolean direccionTieneEmail() {
		return email != null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

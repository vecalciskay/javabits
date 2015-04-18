package vcy.ej5;

public class Ej5Main {

	public static void main(String[] args) {
		
		String[] nombres = {"Hugo","Paco","Luis","Daisy","Donald" };
		String[] apellidos = {"Toro","Gonzalez", "Guardia", "Zenzano", "Claros" };
		String[] direcciones = { 
			"Av Beni entre 3er y 4to anillo, nombre@corporacion.com y tambien su fono 3345664",
			"Av Beni entre 3er y 4to anillo, y tambien su fono 3345664",
			"Av Beni entre 3er y 4to anillo, nombres@corporacion.com y tambien su fono 3345664",
			"Av Beni entre 3er y 4to anillo, (gallo.pato@microsoft.com) y tambien su fono 3345664",
			"Av Beni entre 3er y 4to anillo, nombre@@corporacion.com y tambien su fono 3345664"		
		};
		
		for (int i = 0; i < direcciones.length; i++) {
			Persona p = new Persona(nombres[i], apellidos[i], direcciones[i]);
			
			System.out.println("--------- " + i + " ----------");
			System.out.println(p.apellidoComaNombre());
			System.out.println(p.nombreAlReves());
			if (p.direccionTieneEmail()) {
				System.out.println("El email de la persona es " + p.getEmail());
			} else {
				System.out.println("La direccion no tiene ningun email valido");			
			}
		}
		
		
	}
}

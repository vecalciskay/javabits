package vcy.ej6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ej6Main {

	public static void main(String[] args) {

		System.out
				.println("Este ejercicio muestra las posibilidades de regex (Expresiones regulares)");

		String regexEmail = "[a-zA-Z0-9\\._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";

		System.out
				.println("Para revisar si una expresión es un email por ejemplo: "
						+ regexEmail);

		String[] emails = { "vladimir.calderon@gmail.com", "www.yahoo.com",
				"gabriel@hotmail.com" };

		for (String s : emails) {
			System.out.println(s + " ----> "
					+ (s.matches(regexEmail) ? "OK" : "ERROR"));
		}
		System.out.println();

		System.out
				.println("PAra revisar si un texto lleva un correo electrónico:");
		System.out
				.println("-----------------------------------------------------------");
		String texto = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque et magna nec augue malesuada\n"
				+ "posuere vel vitae eros. Ut nec vladimir.calderon@gmail.com est a est semper vestibulum eget  \n"
				+ "Integer iaculis ullamcorper metus, quis blandit dolor commodo a. danielVelasquez@cs.math.edu \n"
				+ "purus venenatis congue. Aenean eu neque lorem. Morbi convallis, mi nec elementum laoreet, \n"
				+ "elit sapien accumsan orci, eu finibus arcu ex eu neque. Nam eu eros quis ante sollicitudin \n"
				+ "semper. Etiam federico56@hotmail.com nulla lectus mattis ac mauris. Morbi elementum molestie\n"
				+ "lorem, eu commodo ex dapibus vel.";
		System.out.println(texto);
		System.out
				.println("-----------------------------------------------------------");

		// Create a Pattern object
		Pattern expresionRegular = Pattern.compile(regexEmail);

		// Now create matcher object.
		Matcher m = expresionRegular.matcher(texto);
		
		int cuantos = 1;
		while (m.find()) {
			System.out.println("Email Nro " + cuantos);
			System.out.println("Desde posicion: " + m.start() + " hasta: "
					+ m.end());
			System.out.println("Mail: " + m.group());
			cuantos++;
		}
	}

}

package pixelAnalyzer.test;

import pixelAnalyzer.ed.ListaDoble;

public class TestLista {

	public static void main(String[] args) {

		ListaDoble<String> s = new ListaDoble<String>();
		s.insertar("Hugo").insertar("Paco").insertar("Luis");
		System.out.println(s);
	}

}

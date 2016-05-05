package arbol;

import arbol.obj.Cadena;

public class TestCadena {
	public static void main(String[] args) {
		Cadena<Integer> c = new Cadena<Integer>();
		

		c.insertar(new Integer(7));
		c.insertar(new Integer(1));
		c.insertar(new Integer(5));
		c.insertar(new Integer(4));
		c.insertar(new Integer(9));
		c.insertar(new Integer(3));
		c.insertar(new Integer(12));
		c.insertar(new Integer(4));
		
		System.out.println(c.toString());
		
		c.sort();
		
		System.out.println(c.toString());
	}
}

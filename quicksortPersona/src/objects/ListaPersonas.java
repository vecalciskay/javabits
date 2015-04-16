package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;

public class ListaPersonas extends Observable {

	private static int NUMEROPERSONAS = 400;
	private static String[] NOMBRES = { "Sergio", "Paco", "Hugo", "Luis",
			"Daniel", "Maria", "Lucia", "Fernanda", "Gabriela", "Manuel" };

	private Persona[] personas;

	public ListaPersonas() {
		super();
		this.personas = new Persona[NUMEROPERSONAS];
	}

	public void reset() {
		for (int i = 0; i < personas.length; i++) {
			personas[i] = ListaPersonas.randomPersona();
		}
	}

	public static Persona randomPersona() {
		int altura = 165 + (int) (Math.random() * 10.0);
		String nombre = NOMBRES[(int) (Math.random() * ListaPersonas.NOMBRES.length)];

		return new Persona(altura, nombre);
	}

	public void draw(Graphics gc) {

		int x0 = 50;
		int y0 = 150;

		for (int i = 0; i < personas.length; i++) {
			gc.setColor(Color.blue);
			gc.drawLine(x0 + i * 2, y0, x0 + i * 2,
					y0 - (personas[i].getAltura() - 164) * 10);
		}
	}

	public void ordenar() {
		quicksort(0, personas.length - 1);
	}

	public void quicksort(int izq, int der) {
		int i = izq;
		int j = der;
		Persona pivote = personas[(izq + der) / 2];
		do {
			while (personas[i].compareTo(pivote) < 0)
				i++;
			while (personas[j].compareTo(pivote) > 0)
				j--;
			if (i <= j) {
				Persona aux = personas[i];
				personas[i] = personas[j];
				personas[j] = aux;
				
				this.setChanged();
				this.notifyObservers();
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {;	}
				
				i++;
				j--;
			}
		} while (i <= j);
		if (izq < j)
			quicksort(izq, j);
		if (der > i)
			quicksort(i, der);
	}
}

package vcy.ej1;

public class Ej1Main {

	public static void main(String[] args) {

		ListaNumeros lista = new ListaNumeros(1000);
		lista.colocarAlAzar_maxNumero(50);
		
		int cuantos47 = lista.contarCuantosHay(47);
		
		System.out.println("El 47 aparece " + cuantos47 + " veces en la lista");
	}

}

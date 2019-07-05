package dijkstra.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dijkstra.edd.CadenaOrdenada;
import dijkstra.edd.Grafo;
import dijkstra.edd.Pila;

public class TestComponentesGrafo {

	@Test
	public void testDistanciaComparable() {
		Grafo.IdDistancia d1 = new Grafo.IdDistancia("A",4);
		Grafo.IdDistancia d2 = new Grafo.IdDistancia("A",6);
		Grafo.IdDistancia d3 = new Grafo.IdDistancia("A",6);
		
		int menorQue0 = d1.compareTo(d2);
		int mayorQue0 = d3.compareTo(d1);
		int igualQue0 = d2.compareTo(d3);
		
		assertTrue(menorQue0 < 0);
		assertTrue(mayorQue0 > 0);
		assertTrue(igualQue0 == 0);
	}
	
	@Test
	public void testCadenaOrdenada() {
		CadenaOrdenada<String> c = new CadenaOrdenada<String>();
		c.insertar("delta");
		c.insertar("beta");
		c.insertar("alfa");
		c.insertar("iota");
		c.insertar("epsilon");
		
		String result = c.toString();
		assertEquals("alfa->beta->delta->epsilon->iota", result);
	}
	
	@Test
	public void testCMCGrafoSimple() throws Exception {
		Grafo<String> g = new Grafo<String>();
		
		g.nodo("M","M");
		g.nodo("S","S");
		g.nodo("X","X");
		g.nodo("P","P");
		
		g.arco("M","X",7.0, false);
		g.arco("M","P",4.0, false);
		g.arco("P","X",1.0, false);
		g.arco("X","P",1.0, false);
		g.arco("X","S",4.0, false);
		g.arco("P","S",6.0, false);
		
		Pila<String> camino = g.caminoMasCorto("M", "S");
		
		String caminoString = camino.toString();
		
		assertEquals("M->P->X->S", caminoString);
	}
}

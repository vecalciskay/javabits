package dijkstra.edd.coord;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dijkstra.edd.Grafo;
import dijkstra.edd.Pila;

public class GrafoCoordenada extends Grafo<Coordenada> {
	
	public static final int RADIONODO = 20;
	private static final int BORDE = 2;
	private static final int BORDESELECCIONADO = 4;
	private static final int LINEABASETEXTO = 10;
	
	protected String origen;
	protected String destino;
	protected String origenHelper;
	protected Pila<String> caminoMasCorto;
	
	protected String nodoSeleccionado;
	protected Coordenada arcoPosible;
	
	public GrafoCoordenada() {
		super();
	}

	public void calcularDistancias()  {
		nodos.values().forEach(n -> {
			n.getSalientes().values().forEach(a -> {
				a.setPeso(a.getDe().getContenido().distancia(a.getA().getContenido()));
			});
		});
		
		this.setChanged();
		this.notifyObservers();
	}

	public void dibujar(Graphics2D g) {
		dibujarArcos(g);
		dibujarCaminoMasCorto(g);
		dibujarNodos(g);
	}

	private void dibujarCaminoMasCorto(Graphics2D g) {

		caminoMasCorto = null;
		
		if (origen != null && destino != null) {
			try {
				caminoMasCorto = this.caminoMasCorto(origen, destino);
			} catch (Exception e) {
				caminoMasCorto = null;
			}
		}
		
		if (caminoMasCorto != null) {
			String de = caminoMasCorto.pop();
			while(!caminoMasCorto.estaVacia()) {
				g.setStroke(new BasicStroke(2));
				g.setColor(Color.green);
				String a = caminoMasCorto.pop();
				
				Coordenada coordDe = nodos.get(de).getContenido();
				Coordenada coordA = nodos.get(a).getContenido();
				g.drawLine(coordDe.getX(), coordDe.getY(), coordA.getX(), coordA.getY());
				
				de = a;
			}
		}
		
	}

	private void dibujarArcos(Graphics g) {
		nodos.values().forEach(n -> {
			n.getSalientes().values().forEach(a -> {
				dibujarUnArco(g, a);
			});
			
		});
		
		if (arcoPosible != null) {
			Coordenada xy0 = nodos.get(nodoSeleccionado).getContenido();
			g.drawLine(xy0.getX(), xy0.getY(), arcoPosible.getX(), arcoPosible.getY());
		}
	}

	private void dibujarUnArco(Graphics g, Arco<Coordenada> a) {
		Coordenada c1 = a.getDe().getContenido();
		Coordenada c2 = a.getA().getContenido();
		g.drawLine(c1.getX(), c1.getY(), c2.getX(), c2.getY());
	}

	private void dibujarNodos(Graphics g) {
		
		nodos.values().forEach(n -> {
			dibujarUnNodo(g, n);
		});
	}

	private void dibujarUnNodo(Graphics g, Nodo<Coordenada> n) {
		Coordenada coord = n.getContenido();
		String id = n.getId();
		
		int borde = BORDE;
		g.setColor(Color.black);

		if (nodoSeleccionado != null && id.equals(nodoSeleccionado)) {
			borde = BORDESELECCIONADO;
			g.setColor(Color.orange);
		}
		
		if (origen != null && id.equals(origen)) {
			borde = BORDESELECCIONADO;
			g.setColor(Color.green);
		}
		
		if (destino != null && id.equals(destino)) {
			borde = BORDESELECCIONADO;
			g.setColor(Color.red);
		}
		
		g.fillOval(coord.getX() - RADIONODO - borde, coord.getY() - RADIONODO - borde, 
				2 * (RADIONODO + borde), 2 * (RADIONODO + borde));
		g.setColor(Color.white);
		g.fillOval(coord.getX() - RADIONODO, coord.getY() - RADIONODO, 
				2 * RADIONODO, 2 * RADIONODO);
		
		g.setColor(Color.black);
		g.drawString(id, coord.getX(), coord.getY() + LINEABASETEXTO);
	}

	public String getNodoEnPunto(int x, int y) {
		for(Nodo<Coordenada> n : nodos.values()) {
			if (n.getContenido().tienePunto(x,y))
				return n.getId();
		}
		
		return null;
	}

	public void setNodoSeleccionado(String seleccionado) {
		this.nodoSeleccionado = seleccionado;
		this.setChanged();
		this.notifyObservers();
	}
	
	public String getNodoSeleccionado() {
		return this.nodoSeleccionado;
	}

	public Coordenada getArcoPosible() {
		return arcoPosible;
	}

	public void setArcoPosible(Coordenada arcoPosible) {
		this.arcoPosible = arcoPosible;
		this.setChanged();
		this.notifyObservers();
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
		this.setChanged();
		this.notifyObservers();
	}

	public String getOrigenHelper() {
		return origenHelper;
	}

	public void setOrigenHelper(String origenHelper) {
		this.origenHelper = origenHelper;
	}

	public void cambiarContenidoSeleccionado(Coordenada coordenada) {
		if (nodoSeleccionado == null)
			return;
		
		nodos.get(nodoSeleccionado).setContenido(coordenada);
		this.calcularDistancias();
	}
}

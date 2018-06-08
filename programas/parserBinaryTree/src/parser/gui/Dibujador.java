package parser.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import parser.obj.ArbolBinario;
import parser.obj.Aritmetico;

public class Dibujador {

	private ArbolBinario<Aritmetico> theTree;

	public Dibujador(ArbolBinario<Aritmetico> t) {
		theTree = t;
	}

	public void dibujar(Graphics2D g) {
		if (theTree.getRaiz() != null) {
			dibujarNodo(theTree.getRaiz(), 10, 10, g);
		}
			
	}

	public void dibujarNodo(ArbolBinario.Nodo<Aritmetico> n, int x, int y,
			Graphics2D g) {

		int ancho = getAnchoNodo(n);
		
		int xNodo = x + ancho / 2;
		int yNodo = y;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		
		int xNodoLeft = x;
		int yNodoHijo = y + 40;
		int xNodoRight = x;
		if (n.getIzquierda() != null) {
			xNodoRight = xNodoLeft + getAnchoNodo(n.getIzquierda());		
		}
		
		// Dibujar aristas
		g.setColor(new Color(30,30,30));
		if (n.getIzquierda() != null)
			g.drawLine(xNodo - n.getContenido().toString().length() * 3, yNodo + 18,
					xNodoLeft + ( xNodoRight - xNodoLeft)/2, yNodoHijo + 28/2);
		if (n.getDerecha() != null)
			g.drawLine(xNodo - n.getContenido().toString().length() * 3, yNodo + 18,
					xNodoRight + getAnchoNodo(n.getDerecha()) / 2, yNodoHijo + 28/2);		
		
		if (n.getIzquierda() != null) {
			dibujarNodo(n.getIzquierda(), xNodoLeft, yNodoHijo, g );
		}
		
		if (n.getDerecha() != null) {
			dibujarNodo(n.getDerecha(), xNodoRight, yNodoHijo, g);
		}
		
		Ellipse2D.Double hole = new Ellipse2D.Double();
		hole.width = n.getContenido().toString().length() * 12 + 16;
		hole.height = 28;
		hole.x = xNodo - Math.round(hole.width / 2.0);
		hole.y = yNodo;
		
			
		// Dibujar nodo
		g.setColor(new Color(83,169,210));
		g.fill(hole);
		g.setColor(new Color(30,30,30));
		g.drawString(n.getContenido().toString(), xNodo - n.getContenido().toString().length() * 3, yNodo + 18);

	}

	public int getAnchoNodo(ArbolBinario.Nodo<Aritmetico> n) {
		if (n.getIzquierda() == null && n.getDerecha() == null) {
			return n.getContenido().toString().length() * 12 + 16;
		}

		int anchoTotal = 0;
		if (n.getIzquierda() != null)
			anchoTotal += this.getAnchoNodo(n.getIzquierda());
		if (n.getDerecha() != null)
			anchoTotal += this.getAnchoNodo(n.getDerecha());
		
		return anchoTotal;
	}

}

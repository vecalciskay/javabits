package arbol.obj;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Observable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Arbol<T> extends Observable {
	
	private static final Logger logger = LogManager.getRootLogger();
	
	public static final int MARGEN_NODO = 5;
	public static final int ESPACIO_HORIZONTAL_NODO = 20;
	public static final int ESPACIO_VERTICAL_NODO = 70;
	
	public static final String ELIMINAR = "ELIMINAR";
	public static final String NUEVOHIJO = "NUEVOHIJO";
	public static final String MOVER = "MOVER";
	public static final String EDITAR = "EDITAR";

	private Nodo<T> raiz;
	private Nodo<T> seleccionadoNodo;
	private int seleccionadoNodoX;
	private int seleccionadoNodoY;
	private int seleccionadoMovidaX;
	private int seleccionadoMovidaY;
	private String seleccionadoComando;
	
	public Arbol() {
		raiz = null;
		seleccionadoComando = "";
		seleccionadoComando = null;
	}
	
	public String toString() {
		if (raiz == null)
			return "[]";
		return raiz.toString();
	}
	
	public Nodo<T> getSeleccionadoNodo() {
		return seleccionadoNodo;
	}

	public void setSeleccionadoNodo(Arbol<T>.Nodo<T> nodo) {
		this.seleccionadoNodo = nodo;
		
		if (nodo == null) 
		{
			this.setChanged();
			this.notifyObservers();
		}
	}

	public String getSeleccionadoComando() {
		return seleccionadoComando;
	}

	public void setSeleccionadoComando(String seleccionadoComando) {
		this.seleccionadoComando = seleccionadoComando;
	}

	public String getSeleccionadoId() {
		if (seleccionadoNodo == null)
			return null;
		
		return seleccionadoNodo.getId();
	}

	public void setSeleccionadoNodoXY(int x, int y) {
		this.seleccionadoNodoX = x;
		this.seleccionadoNodoY = y;
	}
	
	public void setSeleccionadoMovidaXY(int x, int y) {
		this.seleccionadoMovidaX = x;
		this.seleccionadoMovidaY = y;
		
		this.setChanged();
		this.notifyObservers();
	}

	public void insertar(T obj, String idPadre) throws Exception {
		if (idPadre == null) {
			logger.info("En la raiz " + obj.toString());
			raiz = new Nodo<T>(obj);
			
			this.setChanged();
			this.notifyObservers();
			
			return;
		}
		
		raiz.insertarRecursivo(obj, idPadre);
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void draw(Graphics g, int x, int y) {
		if (raiz == null)
		{
			g.drawString("[Vacia]", x, y + 10);
			return;
		}
		
		raiz.draw(g, x, y, false);
		
		if (seleccionadoNodo != null && seleccionadoComando.equals(MOVER)) {
			
			Drawable d = (Drawable)(seleccionadoNodo.getContenido());
			int anchoNodo = d.getAncho(g) + MARGEN_NODO * 2;
			
			int realX = (anchoNodo / 2 + seleccionadoNodo.getLastX()) - seleccionadoNodo.getAncho(g) / 2;
			
			int offset = (seleccionadoNodoX - realX);
			int xNodo = (seleccionadoMovidaX - offset);
			offset = (seleccionadoNodoY - seleccionadoNodo.getLastY());
			int yNodo = (seleccionadoMovidaY - offset);
			seleccionadoNodo.draw(g, xNodo, yNodo, true);
		}
	}

	public void clicEn(Graphics g, int x, int y) {
		seleccionadoComando = "";
		seleccionadoNodo = null;
		
		if (raiz == null)
			return;
		
		raiz.clicEn(g, x, y, this);
	}
	
	public void mueveNodoSeleccionadoA(Graphics g, int x, int y) {
		if (raiz == null)
			return;
		
		raiz.mueveNodoSeleccionadoA(g, x, y, seleccionadoNodo);
		
		this.setChanged();
		this.notifyObservers();
	}

	public void eliminarSeleccionado() {
		Nodo<T> padreNodo = seleccionadoNodo.getPadre();
		
		if (padreNodo == null) {
			logger.info("Eliminando la raiz, se perderá todo");
			raiz = null;
		} else {
			logger.info("Elimina el hijo " + seleccionadoNodo.getId() + " de " + padreNodo.getId());
			padreNodo.eliminarHijo(seleccionadoNodo.getId());
		}
		this.setChanged();
		this.notifyObservers();
	}
	

	public T getContenidoSeleccionado() {
		if (seleccionadoNodo != null)
			return seleccionadoNodo.getContenido();
		return null;
	}
	
	class Nodo<E> {
		private E contenido;
		private HashMap<String, Nodo<E>> hijos;
		private Nodo<E> padre;
		
		private int lastX;
		private int lastY;
		
		public Nodo(E o) throws Exception {
			contenido = o;
			hijos = new HashMap<String, Nodo<E>>();
			padre = null;
			if (!(o instanceof IdentityAble)) {
				throw new Exception("Tiene que poder sacar un id");
			}
		}
		
		/**
		 * Retorna verdadero cuando la accion ya se ha llevado a cabo y falso
		 * cuando debe seguir buscando en los hijos
		 * @param g
		 * @param x
		 * @param y
		 * @param seleccionadoNodo
		 * @return
		 */
		public boolean mueveNodoSeleccionadoA(Graphics g, int x, int y,
				Arbol<T>.Nodo<E> seleccionadoNodo) {
			Drawable d = (Drawable)contenido;
			int anchoNodo = d.getAncho(g) + MARGEN_NODO * 2;
			int altoNodo = d.getAlto(g) + MARGEN_NODO * 2;
			if (x > lastX && x < (lastX + anchoNodo) &&
					y > lastY && y < (lastY + altoNodo)) {
				
				if (this.tienePorPadreEnlaJerarquiaA(seleccionadoNodo)) {
					return true;
				} else {
					this.moverNodoComoHijo(seleccionadoNodo);
					return true;
				}
			}
			
			for(String key : hijos.keySet()) {
				Nodo<E> hijo = hijos.get(key);
				boolean resultado = hijo.mueveNodoSeleccionadoA(g, x, y, seleccionadoNodo);
				if (resultado)
					return true;
			}
			return false;
		}

		private void moverNodoComoHijo(Arbol<T>.Nodo<E> seleccionadoNodo) {
			if (hijos.containsKey(seleccionadoNodo.getId())) {
				logger.info(seleccionadoNodo.getId() + " ya es hijo de " + this.getId());
				return;
			}
			
			Arbol<T>.Nodo<E> padreAntiguo = seleccionadoNodo.getPadre();
			padreAntiguo.eliminarHijo(seleccionadoNodo.getId());
			seleccionadoNodo.setPadre(this);
			hijos.put(seleccionadoNodo.getId(), seleccionadoNodo);
		}

		private boolean tienePorPadreEnlaJerarquiaA(
				Arbol<T>.Nodo<E> seleccionadoNodo) {
			Arbol<T>.Nodo<E> padreActual = this;
			
			while(padreActual != null) {
				if (padreActual == seleccionadoNodo)
					return true;
				padreActual = padreActual.getPadre();
			}
			
			return false;
		}

		public void eliminarHijo(String id) {
			if (hijos.containsKey(id)) {
				hijos.remove(id);
			}
		}

		public void clicEn(Graphics g, int x, int y, Arbol<E> arbol) {
			Drawable d = (Drawable)contenido;
			int anchoNodo = d.getAncho(g) + MARGEN_NODO * 2;
			int altoNodo = d.getAlto(g) + MARGEN_NODO * 2;
			
			if (x > lastX && x < (lastX + anchoNodo) &&
					y > lastY && y < (lastY + altoNodo)) {
			
				logger.info("Selecciono nodo " + contenido.toString());
				arbol.setSeleccionadoNodo((Arbol<E>.Nodo<E>)this);
			
				int dosMargenes = 2 * MARGEN_NODO;
				
				if (x > (lastX + anchoNodo - dosMargenes) && x < (lastX + anchoNodo) &&
					y > lastY && y < (lastY + dosMargenes)) {
					logger.info("Selecciona comando ELIMINAR nodo " + this.getId());
					arbol.setSeleccionadoComando(Arbol.ELIMINAR);
					
					return;
				}
				
				if (x > (lastX + anchoNodo - 2 * dosMargenes) && x < (lastX + anchoNodo - dosMargenes) &&
						y > lastY && y < (lastY + dosMargenes)) {
					logger.info("Selecciona comando NUEVOHIJO en el nodo " + this.getId());
					arbol.setSeleccionadoComando(Arbol.NUEVOHIJO);
					
					return;
				}
				
				if (this.padre != null &&
						x > (lastX + anchoNodo - 3 * dosMargenes) && x < (lastX + anchoNodo - 2 * dosMargenes) &&
						y > lastY && y < (lastY + dosMargenes)) {
					logger.info("Selecciona comando MOVER el nodo " + this.getId());
					arbol.setSeleccionadoComando(Arbol.MOVER);
					
					return;
				}
				
				arbol.setSeleccionadoComando(Arbol.EDITAR);
			}
			
			if (arbol.getSeleccionadoNodo() != null)
				return;
			
			for(String key : hijos.keySet()) {
				Nodo<E> hijo = hijos.get(key);
				hijo.clicEn(g, x, y, arbol);
				if (arbol.getSeleccionadoNodo() != null)
					return;
			}
		}

		public void draw(Graphics g, int x, int y, boolean transparent) {

			if (!(contenido instanceof Drawable))
			{
				logger.error("Contenido del nodo " + contenido.toString() + " no es drawable");
				return;
			}
			
			if (hijos.isEmpty()) {
				Drawable d = (Drawable)contenido;
				int anchoNodo = d.getAncho(g) + MARGEN_NODO * 2;
				int altoNodo = d.getAlto(g) + MARGEN_NODO * 2;
				
				boolean update = !transparent;
				drawNodeOnly(g, x, y, anchoNodo, altoNodo, update);
				return;
			}
			
			int anchoTotal = getAncho(g);
			Drawable d = (Drawable)contenido;
			int anchoNodo = d.getAncho(g) + MARGEN_NODO * 2;
			int altoNodo = d.getAlto(g) + MARGEN_NODO * 2;
			int xNodoPadre = x + anchoTotal / 2 - anchoNodo / 2;
			int xNodo = xNodoPadre;
			
			
			int yNodo = y + ESPACIO_VERTICAL_NODO;
			xNodo = x;
			for(String key : hijos.keySet()) {
				Nodo<E> hijo = hijos.get(key);
				
				int anchoHijo = hijo.getAncho(g);
				g.setColor(Color.black);
				g.drawLine(x + anchoTotal / 2, y + altoNodo / 2, xNodo + anchoHijo / 2, yNodo + altoNodo / 2);
				hijo.draw(g, xNodo, yNodo, transparent);
				
				xNodo += hijo.getAncho(g) + ESPACIO_HORIZONTAL_NODO;
			}
			
			boolean update = !transparent;
			drawNodeOnly(g, xNodoPadre, y, anchoNodo, altoNodo, update);
		}
		
		private void drawNodeOnly(Graphics g, int x, int y, int anchoNodo, int altoNodo, boolean update) {
			
			Drawable d = (Drawable)contenido;
			int opacity = 155;
			
			if (update) 
			{
				lastX = x;
				lastY = y;
				opacity = 255;
			} 
			
			g.setColor(new Color(255,255,255,opacity));
			g.fillRect(x, y, anchoNodo, altoNodo);
			
			g.setColor(new Color(0,0,0,opacity));
			g.drawRect(x, y, anchoNodo, altoNodo);
			
			drawCommands(g, x, y, anchoNodo);
			
			d.draw(g, x + MARGEN_NODO, y + MARGEN_NODO);
		}

		private void drawCommands(Graphics g, int x, int y, int anchoNodo) {
			
			int dosMargenes = 2 * MARGEN_NODO;
			
			g.drawRect(x + anchoNodo - dosMargenes, y, dosMargenes, dosMargenes);
			g.drawRect(x + anchoNodo - 2 * dosMargenes, y, dosMargenes, dosMargenes);
			
			
			g.drawLine(x + anchoNodo - 2, y + 2, 
					x + anchoNodo - dosMargenes + 2, y + dosMargenes - 2);
			g.drawLine(x + anchoNodo - dosMargenes + 2, y + 2, 
					x + anchoNodo - 2, y + dosMargenes - 2);
			
			g.drawLine(x + anchoNodo - dosMargenes - MARGEN_NODO, y + 2, 
					x + anchoNodo - dosMargenes - MARGEN_NODO, y + dosMargenes - 2);
			g.drawLine(x + anchoNodo - 2 * dosMargenes + 2, y + MARGEN_NODO, 
					x + anchoNodo - dosMargenes - 2, y + MARGEN_NODO);
			
			if (this.padre != null) {
				g.drawRect(x + anchoNodo - 3 * dosMargenes, y, dosMargenes, dosMargenes);
				
				g.drawLine(x + anchoNodo - 2 * dosMargenes - MARGEN_NODO, y + 2, 
						x + anchoNodo - 2 * dosMargenes - MARGEN_NODO, y + dosMargenes - 2);
				g.drawLine(x + anchoNodo - 3 * dosMargenes + 2, y + MARGEN_NODO, 
						x + anchoNodo - 2 * dosMargenes - 2, y + MARGEN_NODO);
			}
		}

		public int getAncho(Graphics g) {
			Drawable d = (Drawable)contenido;
			int anchoNodo = d.getAncho(g) + MARGEN_NODO * 2;
			
			if (hijos.isEmpty())
				return anchoNodo;
			
			int anchoHijos = 0;
			for(String key: hijos.keySet()) {
				Nodo<E> hijo = hijos.get(key);
				anchoHijos += hijo.getAncho(g) + ESPACIO_HORIZONTAL_NODO;
			}
			anchoHijos -= ESPACIO_HORIZONTAL_NODO;
			
			return Math.max(anchoNodo, anchoHijos);
		}

		public void insertarRecursivo(E obj, String idPadre) throws Exception {
			if (idPadre.equals(getId()))
			{
				logger.info("Inserta debajo de " + idPadre + ": " + obj.toString());
				insertarHijo(obj);
				return;
			}
			
			for(String key : hijos.keySet()) {
				Nodo<E> hijo = hijos.get(key);
				hijo.insertarRecursivo(obj, idPadre);
			}
		}

		public String getId() {
			return ((IdentityAble)contenido).getId();
		}
		
		public E getContenido() {
			return contenido;
		}

		public void setContenido(E contenido) {
			this.contenido = contenido;
		}

		public Nodo<E> getPadre() {
			return padre;
		}

		public void setPadre(Nodo<E> padre) {
			this.padre = padre;
		}
		
		public int getLastX() {
			return lastX;
		}
		public int getLastY() {
			return lastY;
		}

		public void insertarHijo(E obj) throws Exception {
			
			Nodo<E> nuevo = new Nodo<E>(obj);
			
			if (!(obj instanceof IdentityAble))
			{
				throw new Exception("El obj " + obj.toString() + " en el nodo no es IdentityAble");
			}
			
			IdentityAble objId = (IdentityAble)obj;
			
			if (hijos.containsKey(objId.getId()))
				throw new Exception("El hijo con id " + objId.getId() + " ya se encuentra colocado");
			
			nuevo.setPadre(this);
			hijos.put(objId.getId(), nuevo);
		}
		
		public String toString() {
			
			StringBuilder result = new StringBuilder();
			result.append(contenido.toString());
			
			if (hijos.isEmpty())
				return result.toString();
			
			result.append("(");
			for(String key : hijos.keySet()) {
				result.append(hijos.get(key).toString() + ",");
			}
			result.deleteCharAt(result.length() - 1);
			result.append(")");
			
			return result.toString();
		}
	}

}

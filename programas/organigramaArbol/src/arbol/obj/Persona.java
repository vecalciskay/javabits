package arbol.obj;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Persona implements IdentityAble, Drawable {
	
	private static final Logger logger = LogManager.getRootLogger();

	public static final int ESPACIO_MARGEN = 5;
	public static final int ESPACIO_ANCHO_NODO = 10;
	public static final int ESPACIO_ALTO_NODO = 20;
	public static final int ALTO_NODO = 20;
	
	private String nombre;
	private int indiceCargo;
	
	public Persona(String n, int c) {
		nombre = n;
		indiceCargo = c;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCargo() {
		return getCargosPosibles().get(indiceCargo);
	}
	
	public int getIndiceCargo() {
		return indiceCargo;
	}

	public void setIndiceCargo(int indiceCargo) {
		this.indiceCargo = indiceCargo;
	}

	public String toString() {
		return nombre + "(" + getCargo() + ")";
	}
	
	/**
	 * El punto x, y es la esqina superior izquierda del cuadrado que encierra a todo el 
	 * nombre completo y el cargo. Estos se ecnuentran alineados a la izquierda
	 * @param g
	 * @param x
	 * @param y
	 */
	public void draw(Graphics g, int x, int y) {
		
		logger.debug("Dibuja la persona " + toString());
		
		Font fontCargo = new Font("Garamond", Font.BOLD, 16);
		FontMetrics mesurer = g.getFontMetrics(fontCargo);
		int altoLetra = mesurer.getHeight();
		
		g.setFont(fontCargo);
		g.drawString(getCargo(), x, y + altoLetra);
		
		Font fontNombre = new Font("Tahoma", Font.PLAIN, 14);
		mesurer = g.getFontMetrics(fontNombre);
		altoLetra += mesurer.getHeight();
		
		g.setFont(fontNombre);
		g.drawString(nombre, x, y + altoLetra);
	}
	
	/**
	 * Obtiene el ancho de toda la cadena en el font correspondiente
	 * @return
	 */
	@Override
	public int getAncho(Graphics g) {
		Font fontCargo = new Font("Garamond", Font.BOLD, 16);
		FontMetrics mesurer = g.getFontMetrics(fontCargo);
		int anchoCargo = mesurer.stringWidth(getCargo());
		
		Font fontNombre = new Font("Tahoma", Font.PLAIN, 14);
		mesurer = g.getFontMetrics(fontNombre);
		int anchoNombre = mesurer.stringWidth(nombre);
		
		return Math.max(anchoCargo, anchoNombre);
	}
	
	@Override
	public int getAlto(Graphics g) {
		Font fontCargo = new Font("Garamond", Font.BOLD, 16);
		FontMetrics mesurer = g.getFontMetrics(fontCargo);
		int altoCargo = mesurer.getHeight();
		
		Font fontNombre = new Font("Tahoma", Font.PLAIN, 14);
		mesurer = g.getFontMetrics(fontNombre);
		int altoNombre = mesurer.getHeight();
		
		return altoCargo + altoNombre;
	}

	@Override
	public String getId() {
		return nombre + String.valueOf(indiceCargo);
	}
	
	private static Cadena<String> cargos;
	
	public static Cadena<String> getCargosPosibles() {
		
		if (cargos == null) {
			cargos = new Cadena<String>();
			cargos.insertar("Gerente General");
			cargos.insertar("Secretaria de Gerencia");
			cargos.insertar("Gerente Administrativo Financiero");
			cargos.insertar("Gerente de Sistemas");
			cargos.insertar("Gerente de Recursos Humanos");
			cargos.insertar("Ejecutivo de Administración de Personal");
			cargos.insertar("Programador");
			cargos.insertar("Contador");
		}
		
		return cargos;
	}
}

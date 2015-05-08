package bn.model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import bn.net.ConexionBatallaNaval;

public class Tablero extends Observable {

	private static final Logger logger = LogManager.getRootLogger();

	public static final int NROFILAS = 6;
	public static final int NROCOLUMNAS = 8;

	public static final String FALLA = "FALLA";
	public static final String ACERTO = "ACERTO";
	public static final String DESTRUIDO = "DESTRUIDO";
	public static final String FIN = "FIN";

	private int[][] suTablero;
	private int[][] miTablero;

	private Cadena<Barco> barcos;
	private ConexionBatallaNaval conexion;
	private boolean miTurno;
	private boolean terminado;

	private int anchoCelda;
	private int altoCelda;

	private int posibleFila;

	private int posibleCol;

	public Tablero() {
		suTablero = new int[NROFILAS][NROCOLUMNAS];
		miTablero = new int[NROFILAS][NROCOLUMNAS];

		barcos = new Cadena<Barco>();
		
		posibleFila = -1;
		posibleCol = -1;

		logger.info("Se ha creado un tablero completo vacio");
	}

	public void inicializarMiTablero() {
		logger.info("Se van a crear 3 lanchas y 2 buques");

		crearUnaLancha();
		crearUnaLancha();
		crearUnaLancha();

		crearUnBuque();
		crearUnBuque();
	}

	private void crearUnBuque() {
		int v = (int) (Math.round(Math.random()));
		Barco nuevo = new Buque(0, 0, v == 1);

		colocarBarco(nuevo);
	}

	private void crearUnaLancha() {
		int v = (int) (Math.round(Math.random()));
		Barco nuevo = new Lancha(0, 0, v == 1);

		colocarBarco(nuevo);
	}

	private void colocarBarco(Barco nuevo) {

		int f = 0;
		int c = 0;

		if (nuevo.isVertical()) {
			f = (int) (Math.random() * (double) (NROFILAS - (nuevo.getTamano() - 1)));
			c = (int) (Math.random() * (double) (NROCOLUMNAS));
		} else {
			f = (int) (Math.random() * (double) (NROFILAS));
			c = (int) (Math.random() * (double) (NROCOLUMNAS - (nuevo
					.getTamano() - 1)));
		}
		nuevo.setFila(f);
		nuevo.setCol(c);

		while (solapaBarco(nuevo)) {
			if (nuevo.isVertical()) {
				f = (int) (Math.random() * (double) (NROFILAS - (nuevo
						.getTamano() - 1)));
				c = (int) (Math.random() * (double) (NROCOLUMNAS));
			} else {
				f = (int) (Math.random() * (double) (NROFILAS));
				c = (int) (Math.random() * (double) (NROCOLUMNAS - (nuevo
						.getTamano() - 1)));
			}

			nuevo.setFila(f);
			nuevo.setCol(c);
		}

		barcos.insertar(nuevo);
		logger.info("Se creo un nuevo " + nuevo.toString());

		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Sirve para saber si el barco nuevo que se pasa en parámetro pisa a algún
	 * barco que ya se encuentra registrado
	 * 
	 * @param nuevo
	 * @return
	 */
	private boolean solapaBarco(Barco nuevo) {

		Iterator<Barco> i = barcos.iterator();
		while (i.hasNext()) {

			Set<String> posicionesNuevo = new HashSet<String>(
					Arrays.asList(nuevo.getPosiciones()));

			Barco objBarco = i.next();
			Set<String> posicionesBarco = new HashSet<String>(
					Arrays.asList(objBarco.getPosiciones()));

			posicionesNuevo.retainAll(posicionesBarco);
			if (!posicionesNuevo.isEmpty()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Obtiene la respuesta de un tiro en MI tablero, este es el metodo que
	 * llama la clase que recibe los disparos que dispara el oponente.
	 * 
	 * @param f
	 * @param c
	 * @return
	 */
	public String getRespuestaParaTiroEn(int f, int c) {
		
		int faltanNTirosParaTerminar = getNumeroTirosParaTerminar();
		
		Iterator<Barco> i = barcos.iterator();
		while (i.hasNext()) {
			Barco embarcacion = i.next();
			String respuestaBarco = embarcacion.getRespuestaParaTiroEn(f, c);
			if (!respuestaBarco.equals(FALLA)) {
				if (faltanNTirosParaTerminar == 1)
					return FIN;
				return respuestaBarco;
			}
		}

		return FALLA;
	}

	/**
	 * Devuelve el numero de aciertos que falta para terminar con nuestros barcos.
	 * @return
	 */
	private int getNumeroTirosParaTerminar() {
		int respuesta = 0;
		Iterator<Barco> i = barcos.iterator();
		while (i.hasNext()) {
			Barco embarcacion = i.next();
			
			respuesta += (embarcacion.getTamano() - embarcacion.getNroAciertos());
		}
		
		return respuesta;
	}

	public int[][] getSuTablero() {
		return suTablero;
	}

	public void setSuTablero(int[][] suTablero) {
		this.suTablero = suTablero;
	}

	public int[][] getMiTablero() {
		return miTablero;
	}

	public void setMiTablero(int[][] miTablero) {
		this.miTablero = miTablero;
	}

	public Cadena<Barco> getBarcos() {
		return barcos;
	}

	public void setBarcos(Cadena<Barco> barcos) {
		this.barcos = barcos;
	}

	public boolean isMiTurno() {
		return miTurno;
	}

	public void setMiTurno(boolean miTurno, String respuesta) {
		this.miTurno = miTurno;

		this.setChanged();
		
		if (respuesta == null)
			this.notifyObservers();
		else
			this.notifyObservers(respuesta);
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	public ConexionBatallaNaval getConexion() {
		return conexion;
	}

	public void setConexion(ConexionBatallaNaval conexion) {
		this.conexion = conexion;
	}

	@Override
	public String toString() {

		String[] letras = { "A", "B", "C", "D", "E", "F" };

		StringBuilder tablero1 = new StringBuilder();
		StringBuilder tablero2 = new StringBuilder();

		tablero1.append("   1  2  3  4  5  6  7  8\n");
		tablero1.append("--------------------------\n");

		tablero2.append("   1  2  3  4  5  6  7  8\n");
		tablero2.append("--------------------------\n");

		for (int i = 0; i < NROFILAS; i++) {
			tablero1.append(letras[i]).append(" ");
			tablero2.append(letras[i]).append(" ");
			for (int j = 0; j < NROCOLUMNAS; j++) {
				tablero1.append(" ").append(suTablero[i][j]).append(" ");
				tablero2.append(" ").append(miTablero[i][j]).append(" ");
			}
			tablero1.append("\n");
			tablero2.append("\n");
		}

		return tablero1.append("\n").append(tablero2).toString();
	}

	public void drawMiTablero(Graphics g, int anchoCelda, int altoCelda) {

		this.anchoCelda = anchoCelda;
		this.altoCelda = altoCelda;

		String[] letras = { "A", "B", "C", "D", "E", "F" };

		g.setColor(Color.black);

		for (int i = 1; i < 9; i++) {
			g.drawString(String.valueOf(i), anchoCelda * i + 20, 20);
		}

		for (int i = 0; i < 6; i++) {
			g.setColor(Color.black);
			g.drawString(letras[i], 20, altoCelda * (i + 1) + 30);
			for (int j = 0; j < 8; j++) {
				g.setColor(Color.white);
				g.fillRect(anchoCelda * (j + 1), altoCelda * (i + 1),
						anchoCelda, altoCelda);

				g.setColor(Color.gray);
				g.drawLine(anchoCelda * (j + 1), altoCelda * (i + 1),
						anchoCelda * (j + 2), altoCelda * (i + 1));
				g.drawLine(anchoCelda * (j + 1), altoCelda * (i + 1),
						anchoCelda * (j + 1), altoCelda * (i + 2));
			}
		}

		Iterator<Barco> iter = barcos.iterator();
		while (iter.hasNext()) {
			Barco b = iter.next();

			b.draw(g, anchoCelda, altoCelda);
		}

		g.setColor(new Color(127, 0, 0, 127));
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (miTablero[i][j] > 0) {
					g.fillRect(anchoCelda * (j + 1), altoCelda * (i + 1),
							anchoCelda, altoCelda);
				}
			}
		}
	}

	public void drawSuTablero(Graphics g, int anchoCelda, int altoCelda) {

		this.anchoCelda = anchoCelda;
		this.altoCelda = altoCelda;

		String[] letras = { "A", "B", "C", "D", "E", "F" };

		g.setColor(Color.black);

		for (int i = 1; i < 9; i++) {
			g.drawString(String.valueOf(i), anchoCelda * i + 20, 20);
		}

		for (int i = 0; i < 6; i++) {
			g.setColor(Color.black);
			g.drawString(letras[i], 20, altoCelda * (i + 1) + 30);
			for (int j = 0; j < 8; j++) {
				g.setColor(Color.white);
				g.fillRect(anchoCelda * (j + 1), altoCelda * (i + 1),
						anchoCelda, altoCelda);

				g.setColor(Color.gray);
				g.drawLine(anchoCelda * (j + 1), altoCelda * (i + 1),
						anchoCelda * (j + 2), altoCelda * (i + 1));
				g.drawLine(anchoCelda * (j + 1), altoCelda * (i + 1),
						anchoCelda * (j + 1), altoCelda * (i + 2));
			}
		}

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				if (suTablero[i][j] == 1) {
					g.setColor(new Color(127, 127, 127, 127));
					g.fillRect(anchoCelda * (j + 1), altoCelda * (i + 1),
							anchoCelda, altoCelda);
				}
				if (suTablero[i][j] > 1) {
					g.setColor(new Color(127, 0, 0, 127));
					g.fillRect(anchoCelda * (j + 1), altoCelda * (i + 1),
							anchoCelda, altoCelda);
				}
			}
		}
		
		if (posibleFila >= 0 && posibleCol >= 0) {
			try {
				if (suTablero[posibleFila][posibleCol] == 0) {
					
					g.setColor(new Color(0,0,127,127));
					g.fillOval(anchoCelda * (posibleCol + 1) + anchoCelda / 2 - 10, 
							altoCelda * (posibleFila + 1) + altoCelda / 2 -10, 
							20, 20);
					
					g.setColor(Color.blue);
					g.drawLine(anchoCelda * (posibleCol + 1) + anchoCelda / 2, altoCelda * (posibleFila + 1) + 5,
							anchoCelda * (posibleCol + 1) + anchoCelda / 2, altoCelda * (posibleFila + 2) - 5);
					
					g.drawLine(anchoCelda * (posibleCol + 1) + 10, altoCelda * (posibleFila + 1) + altoCelda / 2,
							anchoCelda * (posibleCol + 2) - 10, altoCelda * (posibleFila + 1) + altoCelda / 2);
				}
				
				posibleFila = -1;
				posibleCol = -1;
			} catch(Exception q) {
				logger.error("No puede ir a " + posibleFila + "," + posibleCol, q);
			}
		}
	}

	/**
	 * Obtiene las coordenadas en el tablero de la forma int,int
	 * 
	 * @param x
	 * @param y
	 * @param filaCol 
	 * @return
	 */
	public String obtenerCoordenadas(int x, int y, int[] filaCol) {
		if (anchoCelda <= 0 || altoCelda <= 0)
			return "-1,-1";

		int xf = x - anchoCelda;
		int yf = y - altoCelda;

		if (xf < 0 || yf < 0)
			return "-1,-1";

		int c = xf / anchoCelda;
		int f = yf / altoCelda;
		
		filaCol[0] = f;
		filaCol[1] = c;

		String[] letras = { "A", "B", "C", "D", "E", "F" };

		return letras[f] + (c + 1);
	}

	public void jugarMiTurno(String mensajeEnviar, int fila, int col) {
		
		if (conexion == null) {
			logger.error("No hay conexión ni tampoco hay juego, no sirve el tiro");
			return;
		}
		
		enviarMensaje(mensajeEnviar, fila, col);
		
		recibirMensaje();
	}
	

	public void jugarSuTurno() {
		if (conexion == null) {
			logger.error("No hay conexión ni tampoco hay juego, no sirve el tiro");
			return;
		}
		
		recibirMensaje();
	}

	private void recibirMensaje() {
		logger.info("Esperando el tiro que viene del oponente");

		String mensaje = "";
		try {
			mensaje = conexion.getIn().readLine();
		} catch (IOException e) {
			logger.error("No pudo recibir ningun mensaje, se acaba el juego", e);
			setTerminado(true);
			return;
		}

		int[] filaColumna = new int[2];
		filaColumna = convertirMensajeACoordenadas(mensaje);

		if (filaColumna[0] < 0 || filaColumna[1] < 0) {
			logger.error("Se rompe el protocolo ya que no se recibio un mensaje correcto: "
					+ mensaje);
			setTerminado(true);
		}

		String respuesta = getRespuestaParaTiroEn(filaColumna[0],
				filaColumna[1]);
		
		miTablero[filaColumna[0]][filaColumna[1]] = 1;

		logger.info("[Recibido] " + mensaje);
		logger.info("[Enviado]" + respuesta);

		conexion.getOut().println(respuesta);
		conexion.getOut().flush();

		if (respuesta.equals(FIN)) {
			logger.info("Hemos perdido, el ultimo golpe destruyo el ultimo barco");
			setTerminado(true);
			setMiTurno(true, "3Usted ha perdido!!");
		} else {
			setMiTurno(true, "2Me Toca");
		}
	}

	private void enviarMensaje(String mensajeEnviar, int fila, int col) {
		conexion.getOut().println(mensajeEnviar);
		conexion.getOut().flush();
		
		logger.info("Esperando la respuesta del oponente");
		String respuestaMensaje = "";
		try {
			respuestaMensaje = conexion.getIn().readLine();
		} catch (IOException e) {
			logger.error("No pudo recibir ningun mensaje, se acaba el juego", e);
			setTerminado(true);
			return;
		}
		
		logger.info("[Enviado] " + mensajeEnviar);
		logger.info("[Recibido]" + respuestaMensaje);
		
		logger.info("Se marca el disparo en " + mensajeEnviar + " (" + fila + "," + col + ")");
		suTablero[fila][col] = 1;
		if (respuestaMensaje.equals(ACERTO) || 
				respuestaMensaje.equals(DESTRUIDO) ||
				respuestaMensaje.equals(FIN)) {
			logger.info("Se logro golpear (" + respuestaMensaje + ") al adversario");
			suTablero[fila][col]++;
		}
		
		if (respuestaMensaje.equals(FIN)) {
		
			logger.info("El juego ha terminado, hemos ganado");
			setTerminado(true);
			setMiTurno(false, "0Ganamos");
		
		} else {
		
			setMiTurno(false, "1" + respuestaMensaje);
		}
	}

	public static int[] convertirMensajeACoordenadas(String mensaje) {
		int[] result = new int[2];
		result[0] = -1;
		result[1] = -1;

		String[] letras = { "A", "B", "C", "D", "E", "F" };

		if (mensaje == null || mensaje.length() != 2)
			return result;

		String primera = mensaje.substring(0, 1);
		for (int i = 0; i < letras.length; i++) {
			if (primera.equals(letras[i])) {
				result[0] = i;
				break;
			}
		}

		String segunda = mensaje.substring(1, 2);
		try {
			int col = Integer.parseInt(segunda);
			result[1] = col - 1;
		} catch (Exception q) {
			logger.error("El mensaje en la segunda letra no era un numero, era "
					+ segunda);
		}

		return result;
	}

	public void setPosibleTiro(int filaEnviar, int colEnviar) {
		posibleFila = filaEnviar;
		posibleCol = colEnviar;
		
		this.setChanged();
		this.notifyObservers();
	}

	public boolean yaDisparoEn(int filaEnviar, int colEnviar) {
		return suTablero[filaEnviar][colEnviar] > 0;
	}

}

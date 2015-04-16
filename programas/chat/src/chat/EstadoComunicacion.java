package chat;

import java.awt.*;

import javax.swing.*;

public class EstadoComunicacion extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int FLECHA_ARRIBA = 1;

	public static final int FLECHA_ABAJO = 2;

	public static final int EN_ESPERA = 4;

	private int estado = EstadoComunicacion.EN_ESPERA;

	private Thread proceso;

	private boolean please_stop;

	private int width = 100;

	private int height = 200;

	private int lastY;

	public EstadoComunicacion() {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.WHITE);
		please_stop = false;
		proceso = new Thread(this);
		proceso.start();
	}

	public void shutdown() {
		this.please_stop = true;
	}

	public void run() {
		int ultimoEstado = estado;
		lastY = 0;
		while (!please_stop) {
			// Si ha cambiado de estado, entonces hay que comenzar de 0
			if (estado != ultimoEstado) {
				lastY = 0;
				ultimoEstado = estado;
			}
			repaint();

			lastY += 10;
			if (lastY > height) {
				if (ultimoEstado != EstadoComunicacion.EN_ESPERA)
					estado = EstadoComunicacion.EN_ESPERA;
				lastY = 0;
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				;
			}
		}
	}

	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		switch (estado) {
		case EstadoComunicacion.FLECHA_ARRIBA:
			dibujarFlechaArriba(gc, lastY);
			break;
		case EstadoComunicacion.FLECHA_ABAJO:
			dibujarFlechaAbajo(gc, lastY);
			break;
		case EstadoComunicacion.EN_ESPERA:
			dibujarEnEspera(gc, lastY);
			break;
		default:
			break;
		}
	}

	private void dibujarEnEspera(Graphics gc, int y) {
		if (gc == null)
			return;
		// Primero borra la anterior si es que había
		int altura = 0;
		gc.setColor(Color.WHITE);
		altura = y - 10;
		gc.drawRect(10, height / 2 - altura / 4, width - 10 * 2, altura / 2);
		gc.setColor(Color.BLACK);
		altura = y;
		gc.drawRect(10, height / 2 - altura / 4, width - 10 * 2, altura / 2);
	}

	private void dibujarFlechaAbajo(Graphics gc, int y) {
		if (gc == null)
			return;
		// Primero borra la anterior si es que había
		int altura = 0;
		gc.setColor(Color.WHITE);
		altura = y - 10;
		gc.drawLine(10, altura, width / 2, altura + 10);
		gc.drawLine(width / 2, altura + 10, width - 10, altura);
		gc.setColor(Color.BLACK);
		altura = y;
		gc.drawLine(10, altura, width / 2, altura + 10);
		gc.drawLine(width / 2, altura + 10, width - 10, altura);
	}

	/**
	 * El estaod en el que va a estar mostrando el dibujo. HAy que sacarlo de
	 * las variables de clase de la clase.
	 * 
	 * @param e
	 */
	public void setEstado(int e) {
		this.estado = e;
	}

	/**
	 * Dibuja una flecha hacia arriba indicando un tipo de comunicacion.
	 * 
	 * @param gc
	 * @param y
	 */
	public void dibujarFlechaArriba(Graphics gc, int y) {
		if (gc == null)
			return;
		// Primero borra la anterior si es que había
		int altura = 0;
		gc.setColor(Color.WHITE);
		altura = y - 10;
		gc.drawLine(10, height - altura, width / 2, height - altura - 10);
		gc.drawLine(width / 2, height - altura - 10, width - 10, height
				- altura);
		gc.setColor(Color.BLACK);
		altura = y;
		gc.drawLine(10, height - altura, width / 2, height - altura - 10);
		gc.drawLine(width / 2, height - altura - 10, width - 10, height
				- altura);
	}

	public static void main(String[] args) {
		JFrame win = new JFrame("test de chat");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		EstadoComunicacion obj = new EstadoComunicacion();
		win.getContentPane().add(obj);

		win.pack();
		win.setVisible(true);
	}
}

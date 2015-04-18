package tableronetChat.modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;

public class Mensaje {

	private String texto;
	private boolean usuarioRemoto;
	private boolean enviado;
	private Date fecha;
	
	public static final int ANCHO_MENSAJE = 275;
	
	public Mensaje(String texto, boolean usuarioRemoto) {
		this.texto = texto;
		this.usuarioRemoto = usuarioRemoto;
		fecha = new Date();
		enviado = false;
	}
	
	public String toString() { 
		return texto;
	}
	
	/**
	 * El 23 sale de dividir 275 entre 12
	 * @return
	 */
	public String[] cortarMensaje() {
		
		int numMsg = texto.length() / 23;
		String[] res = new String[numMsg + 1];
		
		for(int i=0; i<=numMsg; i++) {
			if (i == numMsg) {
				res[i] = texto.substring(i*23);
				continue;
			}
			res[i] = texto.substring(i*23, i*23 + 23);
		}
		
		return res;
	}
	
	public int getAlto() {
		String[] msgs = cortarMensaje();
		return msgs.length * 15;
	}

	public void draw(Graphics g, int h) {
		String[] msgs = cortarMensaje();
		int alto = msgs.length * 15;
		if (usuarioRemoto) {
			g.setColor(Color.yellow);
			g.fillRoundRect(10, h, 275, alto, 20, 20);

			drawTextos(g, msgs, 10, h + 10);
		} else {
			g.setColor(Color.green);
			g.fillRoundRect(315, h, 275, alto, 20, 20);
			
			drawTextos(g, msgs, 315, h + 10);
		}
	}
	
	private void drawTextos(Graphics g, String[] msgs, int x, int y) {
		g.setColor(Color.black);
		for(int i=0; i<msgs.length; i++) {
			g.drawString(msgs[i], x, y + i*15);
		}
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isUsuarioRemoto() {
		return usuarioRemoto;
	}

	public void setUsuarioRemoto(boolean usuarioRemoto) {
		this.usuarioRemoto = usuarioRemoto;
	}

	public boolean isEnviado() {
		return enviado;
	}

	public void setEnviado(boolean enviado) {
		this.enviado = enviado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}

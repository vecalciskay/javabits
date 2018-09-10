package server.stat;

public class MensajeTipo {

	public enum Tipo {
		ENTRADA, SALIDA
	};
	
	private String mensaje;
	private int mensajeLength;
	private Tipo tipo;
	
	public MensajeTipo(String m, Tipo t) {
		this.mensaje = m;
		this.tipo = t;
	}

	public MensajeTipo(byte[] data, Tipo t) {
		this.mensajeLength = data.length;
		this.tipo = t;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public int getMensajeLength() {
		return mensajeLength;
	}

	public void setMensajeLength(int mensajeLength) {
		this.mensajeLength = mensajeLength;
	}
	
	
}

package server.conf;

public class Configuracion {

	private static final int PUERTO_SRV = 7676;
	private static final int PUERTO_CMD = 7677;
	private static final String SERVICIO = "Ordenar";
	private static final String SHUTDOWN_KEYWORD = "FINALIZAR";
	private static Configuracion singleton = null;
	
	private Configuracion() {
		puerto = PUERTO_SRV;
		puertoComando = PUERTO_CMD;
		palabraClaveTerminar = SHUTDOWN_KEYWORD;
		servicio = SERVICIO;
	}
	
	public static Configuracion getOrCreate() {
		if (singleton == null)
			singleton = new Configuracion();
		return singleton;
	}
	
	private int puerto;
	private String servicio;
	private int puertoComando;
	private String palabraClaveTerminar;
	
	public int getPuerto() {
		return puerto;
	}
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public int getPuertoComando() {
		return puertoComando;
	}

	public void setPuertoComando(int puertoComando) {
		this.puertoComando = puertoComando;
	}

	public String getPalabraClaveTerminar() {
		return palabraClaveTerminar;
	}

	public void setPalabraClaveTerminar(String palabraClaveTerminar) {
		this.palabraClaveTerminar = palabraClaveTerminar;
	}
	
	
}

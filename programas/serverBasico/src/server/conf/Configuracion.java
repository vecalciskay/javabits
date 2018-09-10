package server.conf;

public class Configuracion {

	private static final int PUERTO_SRV = 7676;
	private static final int PUERTO_CMD = 7677;
	private static final String SERVICIO = "Web";
	private static final String SHUTDOWN_KEYWORD = "FINALIZAR";
	private static final String STATS_CMD = "STATS";
	private static final String SERVICIOWEB_CARPETAWEB = "D:\\Temp\\CarpetaWeb";
	private static Configuracion singleton = null;
	
	private Configuracion() {
		puerto = PUERTO_SRV;
		puertoComando = PUERTO_CMD;
		comandoFinalizar = SHUTDOWN_KEYWORD;
		comandoEstadisticas = STATS_CMD;
		servicio = SERVICIO;
		carpetaWeb = SERVICIOWEB_CARPETAWEB;
	}
	
	public static Configuracion getOrCreate() {
		if (singleton == null)
			singleton = new Configuracion();
		return singleton;
	}
	
	private int puerto;
	private String servicio;
	private int puertoComando;
	private String comandoFinalizar;
	private String comandoEstadisticas;
	private String carpetaWeb;
	
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

	public String getComandoFinalizar() {
		return comandoFinalizar;
	}

	public void setComandoFinalizar(String cmd) {
		this.comandoFinalizar = cmd;
	}

	public String getComandoEstadisticas() {
		return comandoEstadisticas;
	}

	public void setComandoEstadisticas(String comandoEstadisticas) {
		this.comandoEstadisticas = comandoEstadisticas;
	}

	public String getCarpetaWeb() {
		return carpetaWeb;
	}

	public void setCarpetaWeb(String carpetaWeb) {
		this.carpetaWeb = carpetaWeb;
	}
	
}

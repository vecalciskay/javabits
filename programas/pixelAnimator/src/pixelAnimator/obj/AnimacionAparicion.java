package pixelAnimator.obj;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Esta clase abstracta es la clase genérica para cualqeuir animación. Se puede cambiar la cadenacia con la cual
 * se realiza la animación y el tiempo total. 
 * 
 * En las subclases basicamente se debe implementar el metodo hacersiguientePaso que solamente lidia
 * con realizar una transición. Todo el manejo de hilos se hace en esta clase.
 * @author Vladimir
 *
 */
public abstract class AnimacionAparicion implements Runnable {

	private static final Logger logger = LogManager.getRootLogger();

	protected static final int DEFAULT_PASO_MS = 200;
	protected static final int DEFAULT_TOTAL_MS = 8000;
	
	protected Dibujo original;
	protected Dibujo intermedio;
	protected int tiempoMs;
	protected int pasoMs;
	protected int ultimoPaso;
	protected Animator padre;
	
	/**
	 * Primero vemos si es el inicio, en cuyo caso devolvemos la figura en blanco (o negro).
	 * Luego se ve si la figura ya terminó la animación, en cuyo caso se devuelve el original.
	 * Y finalmente se ve el caso general cuando la animación está sucediendo.
	 * 
	 * Mientras la animación tiene un siguiente paso devuelve 1, sino devuelve 0
	 */
	public abstract int hacerSiguientePaso();
	
	public void reset() {
		logger.info("Vuelve la animación al estado 0");
		
		intermedio = new Dibujo(original);
		ultimoPaso = 0;
	}
	
	public void run() {
		logger.info("Comienza la animación");
		reset();
		int resultado = 1;
		
		while(resultado > 0) {
			
			resultado = hacerSiguientePaso();
			padre.notificar();
			try {
				Thread.sleep(pasoMs);
			} catch(Exception q) {
				logger.error("Esto no debeería pasar nunca, salta exception en el thread de espera");
			}
		}
	}
	
	public void copiarOriginal() {
		
		int[][] pxOriginal = original.getPixeles();
		int[][] pxTarget = intermedio.getPixeles();
		
		for (int i = 0; i < intermedio.getAncho(); i++) {
			for (int j = 0; j < intermedio.getAlto(); j++) {
				pxTarget[i][j] = pxOriginal[i][j];
			}
		}
	}

	public Dibujo getOriginal() {
		return original;
	}

	public void setOriginal(Dibujo original) {
		this.original = original;
	}

	public Dibujo getIntermedio() {
		return intermedio;
	}

	public void setIntermedio(Dibujo intermedio) {
		this.intermedio = intermedio;
	}

	public int getTiempoMs() {
		return tiempoMs;
	}

	public void setTiempoMs(int tiempoMs) {
		this.tiempoMs = tiempoMs;
	}
	
	public int getPasoMs() {
		return pasoMs;
	}

	public void setPasoMs(int pasoMs) {
		this.pasoMs = pasoMs;
	}

	public int getUltimoPaso() {
		return ultimoPaso;
	}

	public void setUltimoPaso(int ultimoPaso) {
		this.ultimoPaso = ultimoPaso;
	}
}

package pixelAnimator.obj;

/**
 * Subclase de animación que realiza la animación de izquierda a derecha de una imagen.
 * @author Vladimir
 *
 */
public class AnimacionIzquierdaDerecha extends AnimacionAparicion {

	public AnimacionIzquierdaDerecha(Animator padre, Dibujo original) {
		this.padre = padre;
		this.original = original;
		this.pasoMs = DEFAULT_PASO_MS;
		this.tiempoMs = DEFAULT_TOTAL_MS;
		this.ultimoPaso = 0;
	}

	
	@Override
	public int hacerSiguientePaso() {
		
		this.intermedio.reset();
		
		if (ultimoPaso == 0) {
			ultimoPaso++;
			return 1;
		}
		
		int totalPasos = tiempoMs / pasoMs;
		
		if (ultimoPaso >= totalPasos) {
			copiarOriginal();
			return 0;
		}
		
		int anchoACopiar = ultimoPaso * this.original.getAncho() / totalPasos;
		
		int[][] pxIntermedio = intermedio.getPixeles();
		int[][] pxOriginal = original.getPixeles();
		
		for (int i = 0; i < anchoACopiar; i++) {
			for (int j = 0; j < original.getAlto(); j++) {
				pxIntermedio[anchoACopiar - i - 1][j] = pxOriginal[this.original.getAncho() - i - 1][j];
			}
		}
		
		ultimoPaso++;
		return 1;
	}

}

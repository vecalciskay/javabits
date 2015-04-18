package vcy.ej4;

public class AutoCarrera {
	
	private int velocidad;
	private MejoraMotor mejora;

	public AutoCarrera(MejoraMotor mejora) {
		velocidad = 250;
		this.mejora = mejora;
	}

	public AutoCarrera() {
		velocidad = 250;
		this.mejora = null;
	}

	public int getMaxVelocidad() {
		int mejoraEnVelocidad = 0;
		if (mejora != null) {
			mejoraEnVelocidad = (int)((double)velocidad * mejora.getPorcentajeMejora());
		}
		return velocidad + mejoraEnVelocidad;
	}
}

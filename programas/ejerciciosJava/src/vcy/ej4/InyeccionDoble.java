package vcy.ej4;

public class InyeccionDoble extends MejoraMotor {

	public InyeccionDoble(MejoraMotor mejora) {
		porcentajeMejora = 0.008;
		proximaMejora = mejora;
	}

	public InyeccionDoble() {
		porcentajeMejora = 0.008;
		proximaMejora = null;
	}

}

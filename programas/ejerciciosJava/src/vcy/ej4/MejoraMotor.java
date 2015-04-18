package vcy.ej4;

public abstract class MejoraMotor {
	
	protected double porcentajeMejora;
	
	protected MejoraMotor proximaMejora;

	public double getPorcentajeMejora() {
		if (proximaMejora == null)
			return porcentajeMejora;
		
		return porcentajeMejora + proximaMejora.getPorcentajeMejora();
	}

}

package vcy.ej4;

public class Ej4Main {

	public static void main(String[] args) {
		
		MejoraMotor mejora1 = new InyeccionDoble();
		MejoraMotor mejora2 = new AumentoCompresion();
		MejoraMotor mejora3 = new InyeccionDoble(mejora2);
		
		AutoCarrera objAuto1 = new AutoCarrera();
		System.out.println("1. Vel max: " + objAuto1.getMaxVelocidad());
		
		AutoCarrera objAuto2 = new AutoCarrera(mejora1);
		System.out.println("2. Vel max: " + objAuto2.getMaxVelocidad());
		
		AutoCarrera objAuto3 = new AutoCarrera(mejora2);
		System.out.println("3. Vel max: " + objAuto3.getMaxVelocidad());
		
		AutoCarrera objAuto4 = new AutoCarrera(mejora3);
		System.out.println("4. Vel max: " + objAuto4.getMaxVelocidad());
	}
}

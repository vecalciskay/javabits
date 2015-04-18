package vcy.ej2;

public class Ej2Main {

	public static void main(String[] args) {
		
		CuentaBanco objCuenta = CuentaBanco.crearCuentaConInicial(100.0);
		
		objCuenta.depositar(1500.0);
		System.out.println("La cuenta tiene: " + objCuenta.getSaldo());
		
		objCuenta.retirar(200.0);
		System.out.println("La cuenta tiene: " + objCuenta.getSaldo());
		
		objCuenta.depositar(400.0);
		System.out.println("La cuenta tiene: " + objCuenta.getSaldo());
		
		System.out.println("------------- Con Premio ---------------");
		
		// Este premio es depositar en la cuenta el equivalente del 1% del saldo
		// cada vez que se depositan al menos 500
		PremioDeposito500 premio = new PremioDeposito500();
		
		objCuenta.addObserver(premio);
		
		objCuenta.depositar(800.0);
		System.out.println("La cuenta tiene: " + objCuenta.getSaldo());
		
		objCuenta.depositar(300.0);
		System.out.println("La cuenta tiene: " + objCuenta.getSaldo());
	}
}

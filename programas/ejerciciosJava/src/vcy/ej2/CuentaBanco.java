package vcy.ej2;

import java.util.Observable;

public class CuentaBanco extends Observable {

	private double saldo;

	private CuentaBanco(double inicial) {
		this.saldo = inicial;
	}

	public static CuentaBanco crearCuentaConInicial(double inicial) {
		return new CuentaBanco(inicial);
	}

	public void depositar(double deposito) {
		saldo += deposito;
		System.out.println("Se han depositado " + deposito);
		
		this.setChanged();
		this.notifyObservers(new Double(deposito));
	}

	public void retirar(double retiro) {
		saldo -= retiro;
		System.out.println("Se han retirado " + retiro);
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

}

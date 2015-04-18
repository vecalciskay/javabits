package vcy.ej2;

import java.util.Observable;
import java.util.Observer;

public class PremioDeposito500 implements Observer {

	@Override
	public void update(Observable arg0, Object arg1) {

		CuentaBanco obj = (CuentaBanco)arg0;
		Double elDeposito = (Double)arg1;
		
		if (elDeposito.doubleValue() > 500.0) {
			double saldo = obj.getSaldo();
			double premio = saldo * 0.01;

			saldo = saldo + premio;
			obj.setSaldo(saldo);
			
			System.out.println("Se ha dado a la cuenta un premio de " + premio + " por el deposito de " + elDeposito.doubleValue());
		} else {
			
			System.out.println("No hay premio por un deposito pequeno");
		}
	}

}

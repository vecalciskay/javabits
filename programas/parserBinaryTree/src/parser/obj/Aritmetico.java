package parser.obj;

/**
 * Esta clase representa ya sea un numero o un operador aritmetico
 * @author vladimir
 *
 */
public abstract class Aritmetico {
	protected int numero;
	protected OperadorAritmetico operador;
	
	enum OperadorAritmetico { MAS, MENOS, MULTIPLICACION, DIVISION };
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public OperadorAritmetico getOperador() {
		return operador;
	}
	public void setOperador(OperadorAritmetico operador) {
		this.operador = operador;
	}
	public double getValue() {
		return (double)numero;
	}
}

package parser.obj;

public class Operador extends Aritmetico {
	public Operador(OperadorAritmetico op) {
		this.operador = op;
	}
	
	public Operador(String op) throws Exception {
		switch(op) {
		case "+":
			operador = OperadorAritmetico.MAS;
			break;
		case "-":
			operador = OperadorAritmetico.MENOS;
			break;
		case "*":
			operador = OperadorAritmetico.MULTIPLICACION;
			break;
		case "/":
			operador = OperadorAritmetico.DIVISION;
			break;
		default:
			throw new Exception("Error de lectura, operador desconocido: " + op);
		}
			
	}
	
	public String toString() {
		if (operador == OperadorAritmetico.MAS)
			return "+";
		if (operador == OperadorAritmetico.MENOS)
			return "-";
		if (operador == OperadorAritmetico.MULTIPLICACION)
			return "*";
		if (operador == OperadorAritmetico.DIVISION)
			return "/";
		
		return "ERROR";
	}
}

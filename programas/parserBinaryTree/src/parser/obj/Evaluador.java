package parser.obj;

import parser.obj.ArbolBinario.Nodo;
import parser.obj.Aritmetico.OperadorAritmetico;

public class Evaluador {

	private ArbolBinario<Aritmetico> theTree;

	public Evaluador(ArbolBinario<Aritmetico> obj) {
		theTree = obj;
	}

	public void leerExpresion(String expr) throws Exception {
		if (expr.trim().length() > 0) {
			// Crear el primer nodo con valor 0, ya se acomodara con la lectura
			Aritmetico obj = new Numero(0);
			theTree.setRaiz(new ArbolBinario.Nodo<Aritmetico>(obj));
		}
		leerYAplicarReglas(expr.trim(), theTree.getRaiz());
	}

	/**
	 * 1. If the current token is a '(', add a new node as the left child of the
	 * current node, and descend to the left child. 2. If the current token is
	 * in the list ['+','-','/','*'], set the root value of the current node to
	 * the operator represented by the current token. Add a new node as the
	 * right child of the current node and descend to the right child. 3. If the
	 * current token is a number, set the root value of the current node to the
	 * number and return to the parent. 4. If the current token is a ')', go to
	 * the parent of the current node.
	 */
	private void leerYAplicarReglas(String expr, Nodo<Aritmetico> raiz)
			throws Exception {
		String token = "__START__";
		int idx = 0;
		int[] resultNextToken = new int[1];
		Nodo<Aritmetico> actual = raiz;
		// read token
		while (idx < expr.length() && actual != null) {
			token = leerToken(idx, expr, resultNextToken);
			idx = resultNextToken[0];

			// 1.
			if (token.equals("(")) {
				Nodo<Aritmetico> nleft = new Nodo<Aritmetico>(new Numero(0));
				actual.setIzquierda(nleft);
				actual = actual.getIzquierda();
				this.theTree.setChangedAndNotify();
				continue;
			}

			// 2.
			if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
				
				if (token.equals("+"))
					actual.setContenido(new Operador(OperadorAritmetico.MAS));
				if (token.equals("-"))
					actual.setContenido(new Operador(OperadorAritmetico.MENOS));
				if (token.equals("*"))
					actual.setContenido(new Operador(OperadorAritmetico.MULTIPLICACION));
				if (token.equals("/"))
					actual.setContenido(new Operador(OperadorAritmetico.DIVISION));
					
				Nodo<Aritmetico> nRight = new Nodo<Aritmetico>(new Numero(0));
				actual.setDerecha(nRight);
				actual = actual.getDerecha();
				this.theTree.setChangedAndNotify();
				continue;
			}

			// 3.
			int tokenAsNumber = 0;
			boolean isNumber = false;
			try {
				tokenAsNumber = Integer.parseInt(token);
				isNumber = true;
			} catch (Exception e) {
				;
			}

			if (isNumber) {
				actual.setContenido(new Numero(tokenAsNumber));
				actual = actual.getPadre();
				this.theTree.setChangedAndNotify();
				continue;
			}

			// 4.
			if (token.equals(")")) {
				actual = actual.getPadre();
				continue;
			}
		}
		
		if (idx < expr.length()) {
			throw new Exception("Expresion mal formada, no se puede leer como arbol");
		}
		
		if (actual != null) {
			throw new Exception("Hay parentesis abiertos que no cierran o al reves");
		}
	}

	public String leerToken(int idxNextToken, String expr, int[] resultNextToken)
			throws Exception {
		StringBuffer token = new StringBuffer();

		char c = ' ';
		boolean endToken = false;
		boolean tokenIsNumber = false;
		int idx = idxNextToken;

		while (!endToken && idx < expr.length()) {

			c = expr.charAt(idx);

			if (c == ' ' || c == '\t') {
				if (token.length() > 0) {
					resultNextToken[0] = idx;
					return token.toString();
				}

				idx++;
				continue;
			}

			if (c == '(' || c == '+' || c == '-' || c == '*' || c == '/'
					|| c == ')') {

				if (token.length() > 0) {
					resultNextToken[0] = idx;
					return token.toString();
				}

				token.append(c);
				resultNextToken[0] = idx + 1;
				return token.toString();
			}

			if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4'
					|| c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
				if (token.length() == 0) {
					tokenIsNumber = true;
				}

				if (tokenIsNumber) {
					token.append(c);
					idx++;
					continue;
				}
			}

			endToken = true;
		}

		if (tokenIsNumber && token.length() > 0) {
			resultNextToken[0] = idx;
			return token.toString();
		}

		throw new Exception("Caracter no valido en: " + idxNextToken);
	}

	public double evaluar() {
		return evaluarNodo(theTree.getRaiz());
	}

	private double evaluarNodo(ArbolBinario.Nodo<Aritmetico> nodo) {

		if (nodo.getIzquierda() == null && nodo.getDerecha() == null)
			return nodo.getContenido().getValue();

		double result = 0.0;

		// contenido es un operador
		switch (nodo.getContenido().toString()) {
		case "+":
			result = evaluarNodo(nodo.getIzquierda())
					+ evaluarNodo(nodo.getDerecha());
			break;
		case "-":
			result = evaluarNodo(nodo.getIzquierda())
					- evaluarNodo(nodo.getDerecha());
			break;
		case "*":
			result = evaluarNodo(nodo.getIzquierda())
					* evaluarNodo(nodo.getDerecha());
			break;
		case "/":
			result = evaluarNodo(nodo.getIzquierda())
					/ evaluarNodo(nodo.getDerecha());
			break;
		default:
			result = Double.NaN;
			break;
		}

		return result;
	}

	public static void main(String[] args) throws Exception {

		// testLeerExpresion("345");
		// testLeerExpresion("  (");
		// testLeerExpresion("  ( 2516");
		// testLeerExpresion(" 2+  8");
		// testLeerExpresion("((323+ 44) -( 21 *18 ) ) - 21654");
		//
		ArbolBinario<Aritmetico> a = new ArbolBinario<Aritmetico>();
		Evaluador e = new Evaluador(a);
		e.leerExpresion("(2+4)");
		System.out.println(a + " = " + e.evaluar());
		e.leerExpresion("(((323+ 44) -( 21 *18 ) ) - 21654)");
		System.out.println(a + " = " + e.evaluar());
	}

	public static void testLeerExpresion(String expresion) {
		Evaluador e = new Evaluador(new ArbolBinario<Aritmetico>());
		System.out.println("TEST: " + expresion);
		String token = "__START__";
		int idxNextToken = 0;
		int[] resultNextToken = new int[1];
		while (idxNextToken < expresion.length()) {

			try {
				token = e.leerToken(idxNextToken, expresion, resultNextToken);
			} catch (Exception e1) {
				e1.printStackTrace();
				break;
			}
			idxNextToken = resultNextToken[0];

			System.out.println("(" + idxNextToken + ") " + token);
		}
		System.out.println("FIN");
	}
}

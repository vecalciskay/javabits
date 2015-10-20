package pixelAnalyzer.obj;

import pixelAnalyzer.ed.ListaDoble;
import pixelAnalyzer.gui.JPaintPanel;

public class ListaDibujos {
	private ListaDoble<Dibujo> dibujos;
	
	public ListaDibujos() {
		dibujos = new ListaDoble<Dibujo>();
	}
	
	public void insertarDibujo(Dibujo d) {
		int id = getIdSeleccionado();
		d.setSeleccionado(id + 1);
		dibujos.insertar(d);
	}
	
	public int getIdSeleccionado() {
		int maxId = 0;
		for(Dibujo obj:dibujos)  {
			if (obj.getSeleccionado() > maxId)
				maxId = obj.getSeleccionado();
		}
		
		return maxId;
	}
	
	public Dibujo getSeleccionado() {
		int maxId = 0;
		Dibujo dibujoSeleccionado = null;
		for(Dibujo obj:dibujos)  {
			if (obj.getSeleccionado() > maxId) {
				maxId = obj.getSeleccionado();
				dibujoSeleccionado = obj;
			}
		}
		
		return dibujoSeleccionado;
	}
	
	public void cerrarDibujo(int seleccionado) {
		for(Dibujo obj:dibujos)  {
			if (obj.getSeleccionado() == seleccionado) {
				int pos = dibujos.buscar(obj);
				
				dibujos.eliminar(pos);
			}
		}
	}
	
	public ListaDoble<Dibujo> getLista() {
		return dibujos;
	}

	/**
	 * Este metodo le quita el observador a todos los dibujos y se lo aumenta al dibujo que esté seleccionado
	 * en ese momento
	 * @param panel
	 */
	public void setPanelObservador(JPaintPanel panel) {
		for(Dibujo d:dibujos) {
			d.deleteObservers();
		}
		Dibujo obj = getSeleccionado();
		obj.addObserver(panel);
	}
}

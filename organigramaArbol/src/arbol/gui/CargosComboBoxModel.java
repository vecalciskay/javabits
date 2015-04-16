package arbol.gui;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import arbol.obj.Cadena;
import arbol.obj.Persona;

public class CargosComboBoxModel implements ComboBoxModel<String> {

	private String selectedItem;
	
	public CargosComboBoxModel() {
		selectedItem = null;
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getElementAt(int index) {
		Cadena<String> cargos = Persona.getCargosPosibles();
		return cargos.get(index);
	}

	@Override
	public int getSize() {
		return Persona.getCargosPosibles().tamano();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = (String)anItem;
	}

}

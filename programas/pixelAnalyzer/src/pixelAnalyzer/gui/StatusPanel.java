package pixelAnalyzer.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pixelAnalyzer.obj.Dibujo;

public class StatusPanel extends JPanel implements Observer {

	
	private JLabel theLabel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatusPanel() {
		init();
	}
	
	public void init() {
		
		this.setLayout(new BorderLayout());
		theLabel = new JLabel("Comenzado");
		
		this.add(theLabel, BorderLayout.CENTER);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Dibujo obj = (Dibujo)arg0;
		
		theLabel.setText(obj.getHerramientaActual().getNombre() + " Color: " + obj.getContextoActual().getColorForDisplay() + " Grosor: " + obj.getContextoActual().getGrosor());
	}
}

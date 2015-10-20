package pixelAnalyzer.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pixelAnalyzer.obj.Dibujo;
import pixelAnalyzer.obj.ListaDibujos;

public class PanelDibujos extends JPanel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ListaDibujos dibujos;

	public PanelDibujos(ListaDibujos d) {
		dibujos = d;
		
		init();
	}

	private void init() {
		JTabbedPane tabs = new JTabbedPane();
		
		for(Dibujo obj : dibujos.getLista()) {
			JPaintPanel panelDibujo = new JPaintPanel(obj);
			obj.addObserver(panelDibujo);
			
			tabs.add(obj.getNombre(), panelDibujo);
		}
		
		this.setLayout(new BorderLayout());
		this.add(tabs, BorderLayout.CENTER);
	}
	
	
}

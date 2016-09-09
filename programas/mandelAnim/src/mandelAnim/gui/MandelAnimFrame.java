package mandelAnim.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mandelAnim.obj.MandelModel;

public class MandelAnimFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MandelModel modelo;

	private MandelAnimPanel panel;

	public MandelAnimFrame() {
		
		init();
		this.setVisible(true);
		this.pack();
	}

	private void init() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		modelo = new MandelModel();
		
		panel = new MandelAnimPanel(modelo); 
		
		modelo.addObserver(panel);
		
		this.add(panel);

		JMenuBar bar = new JMenuBar();
		
		JMenu m = new JMenu("Archivo");
		
		JMenuItem mi = new JMenuItem("Reset");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				archivo_reset();
			}
		});
		
		m.add(mi);
		
		mi = new JMenuItem("Comenzar");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				archivo_comenzar();
			}
		});
		
		m.add(mi);
		
		bar.add(m);
		
		this.setJMenuBar(bar);
	}
	
	protected void archivo_comenzar() {
		if (!modelo.isRunning())
			modelo.comenzarMandel();
	}

	public void archivo_reset() {
		modelo.reset();
	}
}

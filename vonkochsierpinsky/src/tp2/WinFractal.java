package tp2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class WinFractal extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public PanelDibujo panel;

	public static void main(String[] args) { // string para palabras letras
		WinFractal win = new WinFractal();
		win.setVisible(true);
	}

	public WinFractal() {
		super("Fractales");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.init();
		pack();
	}
	
	private void init() {
		// Crear barra menu
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		// Crear menu Fractales
		JMenu m1 = new JMenu("Fractales");
		mb.add(m1);
		JMenuItem mi = new JMenuItem("Von Koch");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmd_vonkoch();
			}
		});
		m1.add(mi);
		mi = new JMenuItem("Sierpinsky");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmd_sierpinsky();
			}
		});
		m1.add(mi);

		m1.addSeparator();

		mi = new JMenuItem("Salir");
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salir();
			}
		});
		m1.add(mi);
		// PanelDibujo
		panel = new PanelDibujo();
		getContentPane().add(panel);
	}

	public void cmd_vonkoch() {
		System.out.println("Escogio Von Koch");
		String pr = JOptionPane.showInputDialog("Que profundidad?");
		panel.cambiarA(Fractal.VONKOCH, Integer.parseInt(pr));
	}

	public void cmd_sierpinsky() {
		System.out.println("Escogio Sierpinsky");
		String pr = JOptionPane.showInputDialog("Que profundidad?");
		panel.cambiarA(Fractal.SIERPINSKY, Integer.parseInt(pr));
	}

	public void salir() {
		dispose();
		System.exit(0);
	}
}
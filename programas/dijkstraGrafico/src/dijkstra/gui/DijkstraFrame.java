package dijkstra.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dijkstra.edd.coord.GrafoCoordenada;

public class DijkstraFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getRootLogger();
	
	private GrafoCoordenada modelo;
	
	private DijkstraPanel panel;
	
	private DijkstraControlador controlador;
	
	public DijkstraFrame() {
		initComponents();
	}
	
	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		modelo = new GrafoCoordenada();
		controlador = new DijkstraControlador(modelo);
		panel = new DijkstraPanel(modelo);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panel, BorderLayout.CENTER);
		
		crearMenu();
		
		this.pack();
	}

	private void crearMenu() {
		JMenuBar myMenuBar = new JMenuBar();

		JMenu menuFile = new JMenu("Grafo");

		JMenuItem menuFile_New = new JMenuItem("Nuevo nodo");
		menuFile_New.setMnemonic(KeyEvent.VK_N);
		menuFile_New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.ALT_MASK));
		menuFile_New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFile_New_Clicked(e);
			}
		});
		menuFile.add(menuFile_New);
		
		JMenuItem menuFile_Arco = new JMenuItem("Nuevo arco");
		menuFile_Arco.setMnemonic(KeyEvent.VK_A);
		menuFile_Arco.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.ALT_MASK));
		menuFile_Arco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFile_Arco_Clicked(e);
			}
		});
		menuFile.add(menuFile_Arco);

		menuFile.addSeparator();

		JMenuItem menuFile_Exit = new JMenuItem("Quit");
		menuFile_Exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				menuFile_Exit_Clicked(e);
			}

		});
		menuFile.add(menuFile_Exit);

		myMenuBar.add(menuFile);
		
		// Menu Grafo
		JMenu menuOperaciones = new JMenu("Operaciones");

		JMenuItem menuFile_Mover = new JMenuItem("Mover nodos");
		menuFile_Mover.setMnemonic(KeyEvent.VK_M);
		menuFile_Mover.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.ALT_MASK));
		menuFile_Mover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFile_Mover_Clicked(e);
			}
		});
		menuOperaciones.add(menuFile_Mover);
		
		JMenuItem menuFile_Dijkstra = new JMenuItem("Camino más corto");
		menuFile_Dijkstra.setMnemonic(KeyEvent.VK_D);
		menuFile_Dijkstra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				ActionEvent.ALT_MASK));
		menuFile_Dijkstra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuFile_Dijkstra_Clicked(e);
			}
		});
		menuOperaciones.add(menuFile_Dijkstra);
		
		myMenuBar.add(menuOperaciones);

		this.setJMenuBar(myMenuBar);
	}

	protected void menuFile_Dijkstra_Clicked(ActionEvent e) {
		panel.removeMouseMotionListener(controlador);
		panel.removeMouseListener(controlador);
		
		modelo.setNodoSeleccionado(null);
		controlador.setModoDijkstra();
		panel.addMouseListener(controlador);
	}

	protected void menuFile_Mover_Clicked(ActionEvent e) {
		panel.removeMouseMotionListener(controlador);
		panel.removeMouseListener(controlador);
		
		modelo.setNodoSeleccionado(null);
		controlador.setModoMover();
		panel.addMouseListener(controlador);
		panel.addMouseMotionListener(controlador);
	}

	protected void menuFile_Arco_Clicked(ActionEvent e) {
		panel.removeMouseMotionListener(controlador);
		panel.removeMouseListener(controlador);
		
		modelo.setNodoSeleccionado(null);
		controlador.setModoArco();
		panel.addMouseListener(controlador);
		panel.addMouseMotionListener(controlador);
	}

	protected void menuFile_Exit_Clicked(ActionEvent e) {
		logger.info("Clicked on the exit option, exiting now...");

		this.dispose();
		System.exit(0);
	}

	protected void menuFile_New_Clicked(ActionEvent e) {
		panel.removeMouseMotionListener(controlador);
		panel.removeMouseListener(controlador);
		
		controlador.setModoNodo();
		panel.addMouseListener(controlador);
	}
}
